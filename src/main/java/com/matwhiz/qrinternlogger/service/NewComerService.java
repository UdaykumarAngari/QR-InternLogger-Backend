package com.matwhiz.qrinternlogger.service;

import com.matwhiz.qrinternlogger.dto.NewComerDto;
import com.matwhiz.qrinternlogger.entity.NewComer;
import com.matwhiz.qrinternlogger.repository.NewComerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NewComerService {
    
    @Autowired
    private NewComerRepository newComerRepository;
    
    @Autowired
    private EmailService emailService;
    
    public NewComer registerNewComer(NewComerDto newComerDto) {
        // Check if visitor ID already exists
        if (newComerRepository.existsByVisitorId(newComerDto.getVisitorId())) {
            throw new RuntimeException("Visitor with ID " + newComerDto.getVisitorId() + " already exists");
        }
        
        // Create new comer entity
        NewComer newComer = new NewComer();
        newComer.setName(newComerDto.getName());
        newComer.setVisitorId(newComerDto.getVisitorId());
        newComer.setEmail(newComerDto.getEmail());
        newComer.setPhone(newComerDto.getPhone());
        newComer.setAadhaar(newComerDto.getAadhaar());
        newComer.setPurpose(newComerDto.getPurpose());
        
        // Save new comer
        NewComer savedNewComer = newComerRepository.save(newComer);
        
        // Send notification to CSO
        emailService.sendNewComerNotification(savedNewComer.getName(), 
                                            savedNewComer.getVisitorId(), 
                                            savedNewComer.getPurpose());
        
        return savedNewComer;
    }
    
    public List<NewComer> getAllNewComers() {
        return newComerRepository.findAllByOrderByEntryTimeDesc();
    }
    
    public Optional<NewComer> getNewComerById(Long id) {
        return newComerRepository.findById(id);
    }
    
    public Optional<NewComer> getNewComerByVisitorId(String visitorId) {
        return newComerRepository.findByVisitorId(visitorId);
    }
}
