package com.matwhiz.qrinternlogger.controller;

import com.matwhiz.qrinternlogger.dto.InternRegistrationDto;
import com.matwhiz.qrinternlogger.dto.LogEntryResponseDto;
import com.matwhiz.qrinternlogger.entity.Intern;
import com.matwhiz.qrinternlogger.entity.EntryLog;
import com.matwhiz.qrinternlogger.service.InternService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class InternController {
    
    @Autowired
    private InternService internService;
    
    @PostMapping("/interns/register")
    public ResponseEntity<?> registerIntern(@Valid @RequestBody InternRegistrationDto registrationDto) {
        try {
            Intern intern = internService.registerIntern(registrationDto);
            return ResponseEntity.ok(intern);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/log-entry")
    public ResponseEntity<LogEntryResponseDto> logEntry(@RequestParam String auth_code) {
        LogEntryResponseDto response = internService.logEntry(auth_code);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/interns")
    public ResponseEntity<List<Intern>> getAllInterns() {
        List<Intern> interns = internService.getAllInterns();
        return ResponseEntity.ok(interns);
    }
    
    @GetMapping("/interns/{internId}")
    public ResponseEntity<?> getInternById(@PathVariable String internId) {
        return internService.getInternById(internId)
                .map(intern -> ResponseEntity.ok(intern))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/entry-logs")
    public ResponseEntity<List<EntryLog>> getEntryLogs() {
        List<EntryLog> entryLogs = internService.getEntryLogs();
        return ResponseEntity.ok(entryLogs);
    }
    
    @GetMapping("/entry-logs/{internId}")
    public ResponseEntity<List<EntryLog>> getEntryLogsByInternId(@PathVariable String internId) {
        List<EntryLog> entryLogs = internService.getEntryLogsByInternId(internId);
        return ResponseEntity.ok(entryLogs);
    }
    
    @GetMapping("/interns/{internId}/qr-code")
    public ResponseEntity<byte[]> getQRCodeImage(@PathVariable String internId) {
        try {
            byte[] qrCodeImage = internService.getQRCodeImage(internId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentDispositionFormData("attachment", internId + "_qr_code.png");
            return new ResponseEntity<>(qrCodeImage, headers, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
