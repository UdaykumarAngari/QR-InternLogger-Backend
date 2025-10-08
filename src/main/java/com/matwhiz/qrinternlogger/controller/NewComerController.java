package com.matwhiz.qrinternlogger.controller;

import com.matwhiz.qrinternlogger.dto.NewComerDto;
import com.matwhiz.qrinternlogger.entity.NewComer;
import com.matwhiz.qrinternlogger.service.NewComerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class NewComerController {
    
    @Autowired
    private NewComerService newComerService;
    
    @PostMapping("/new-comers")
    public ResponseEntity<?> registerNewComer(@Valid @RequestBody NewComerDto newComerDto) {
        try {
            NewComer newComer = newComerService.registerNewComer(newComerDto);
            return ResponseEntity.ok(newComer);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/new-comers")
    public ResponseEntity<List<NewComer>> getAllNewComers() {
        List<NewComer> newComers = newComerService.getAllNewComers();
        return ResponseEntity.ok(newComers);
    }
    
    @GetMapping("/new-comers/{id}")
    public ResponseEntity<?> getNewComerById(@PathVariable Long id) {
        return newComerService.getNewComerById(id)
                .map(newComer -> ResponseEntity.ok(newComer))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/new-comers/visitor/{visitorId}")
    public ResponseEntity<?> getNewComerByVisitorId(@PathVariable String visitorId) {
        return newComerService.getNewComerByVisitorId(visitorId)
                .map(newComer -> ResponseEntity.ok(newComer))
                .orElse(ResponseEntity.notFound().build());
    }
}
