package com.matwhiz.qrinternlogger.service;

import com.matwhiz.qrinternlogger.dto.InternRegistrationDto;
import com.matwhiz.qrinternlogger.dto.LogEntryResponseDto;
import com.matwhiz.qrinternlogger.entity.Intern;
import com.matwhiz.qrinternlogger.repository.InternRepository;
import com.matwhiz.qrinternlogger.repository.EntryLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InternService {
    
    @Autowired
    private InternRepository internRepository;
    
    @Autowired
    private EntryLogRepository entryLogRepository;
    
    @Autowired
    private QRCodeService qrCodeService;
    
    @Autowired
    private EmailService emailService;
    
    public Intern registerIntern(InternRegistrationDto registrationDto) {
        // Check if intern already exists
        if (internRepository.existsById(registrationDto.getInternId())) {
            throw new RuntimeException("Intern with ID " + registrationDto.getInternId() + " already exists");
        }
        
        if (internRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("Intern with email " + registrationDto.getEmail() + " already exists");
        }
        
        if (internRepository.existsByAadhaarNumber(registrationDto.getAadhaarNumber())) {
            throw new RuntimeException("Intern with Aadhaar number " + registrationDto.getAadhaarNumber() + " already exists");
        }
        
        // Generate auth code
        String authCode = qrCodeService.generateAuthCode();
        
        // Create intern entity
        Intern intern = new Intern();
        intern.setInternId(registrationDto.getInternId());
        intern.setName(registrationDto.getName());
        intern.setEmail(registrationDto.getEmail());
        intern.setAadhaarNumber(registrationDto.getAadhaarNumber());
        intern.setMobileNumber(registrationDto.getMobileNumber());
        intern.setAuthCode(authCode);
        
        // Save intern
        Intern savedIntern = internRepository.save(intern);
        
        // Generate QR code bytes and store in DB
        try {
            byte[] qrBytes = qrCodeService.getQRCodeImage(savedIntern.getAuthCode());
            savedIntern.setQrImage(qrBytes);
            savedIntern = internRepository.save(savedIntern);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate QR code", e);
        }
        
        // Send QR code to intern via email (from DB)
        emailService.sendQRCodeToIntern(savedIntern);
        
        return savedIntern;
    }
    
    public LogEntryResponseDto logEntry(String authCode) {
        Optional<Intern> internOpt = internRepository.findByAuthCode(authCode);
        
        if (internOpt.isPresent()) {
            Intern intern = internOpt.get();
            
            // Check if already logged today
            LocalDateTime startOfDay = java.time.LocalDate.now().atStartOfDay();
            LocalDateTime endOfDay = startOfDay.plusDays(1);
            long todayEntries = entryLogRepository.countEntriesByInternIdBetween(
                intern.getInternId(), startOfDay, endOfDay);
            if (todayEntries > 0) {
                return new LogEntryResponseDto("warning", "Entry already logged for today");
            }
            
            // Create entry log
            com.matwhiz.qrinternlogger.entity.EntryLog entryLog = new com.matwhiz.qrinternlogger.entity.EntryLog();
            entryLog.setInternId(intern.getInternId());
            entryLog.setStatus("entered");
            entryLogRepository.save(entryLog);
            
            // Send CSO alert
            emailService.sendCSOAlert(intern, "success");
            
            // Return success response
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return new LogEntryResponseDto("success", intern.getInternId(), 
                                        intern.getName(), intern.getEmail(), timestamp);
        } else {
            // Send failure alert for unknown auth code
            Intern fakeIntern = new Intern();
            fakeIntern.setInternId("UNKNOWN");
            fakeIntern.setName("Unknown");
            fakeIntern.setEmail("N/A");
            emailService.sendCSOAlert(fakeIntern, "failure");
            
            return new LogEntryResponseDto("error", "Invalid QR Code");
        }
    }
    
    public List<Intern> getAllInterns() {
        return internRepository.findAll();
    }
    
    public Optional<Intern> getInternById(String internId) {
        return internRepository.findById(internId);
    }
    
    public List<com.matwhiz.qrinternlogger.entity.EntryLog> getEntryLogs() {
        return entryLogRepository.findAllByOrderByTimestampDesc();
    }
    
    public List<com.matwhiz.qrinternlogger.entity.EntryLog> getEntryLogsByInternId(String internId) {
        return entryLogRepository.findByInternIdOrderByTimestampDesc(internId);
    }
    
    public byte[] getQRCodeImage(String internId) {
        Optional<Intern> internOpt = internRepository.findById(internId);
        if (internOpt.isPresent()) {
            Intern intern = internOpt.get();
            if (intern.getQrImage() != null && intern.getQrImage().length > 0) {
                return intern.getQrImage();
            }
            try {
                return qrCodeService.getQRCodeImage(intern.getAuthCode());
            } catch (Exception e) {
                throw new RuntimeException("Failed to generate QR code image", e);
            }
        }
        throw new RuntimeException("Intern not found");
    }
}
