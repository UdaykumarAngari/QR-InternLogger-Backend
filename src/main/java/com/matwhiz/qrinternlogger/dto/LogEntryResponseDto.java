package com.matwhiz.qrinternlogger.dto;

public class LogEntryResponseDto {
    private String status;
    private String internId;
    private String name;
    private String email;
    private String timestamp;
    private String message;
    
    // Constructors
    public LogEntryResponseDto() {}
    
    public LogEntryResponseDto(String status, String internId, String name, 
                             String email, String timestamp) {
        this.status = status;
        this.internId = internId;
        this.name = name;
        this.email = email;
        this.timestamp = timestamp;
    }
    
    public LogEntryResponseDto(String status, String message) {
        this.status = status;
        this.message = message;
    }
    
    // Getters and Setters
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getInternId() {
        return internId;
    }
    
    public void setInternId(String internId) {
        this.internId = internId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
