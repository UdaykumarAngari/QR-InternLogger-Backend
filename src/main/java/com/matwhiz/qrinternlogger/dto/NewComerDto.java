package com.matwhiz.qrinternlogger.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class NewComerDto {
    
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;
    
    @NotBlank(message = "Visitor ID is required")
    @Size(max = 50, message = "Visitor ID must not exceed 50 characters")
    private String visitorId;
    
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;
    
    @Pattern(regexp = "\\d{10,15}", message = "Phone number must be 10-15 digits")
    private String phone;
    
    @Pattern(regexp = "\\d{12}", message = "Aadhaar number must be 12 digits")
    private String aadhaar;
    
    @NotBlank(message = "Purpose is required")
    private String purpose;
    
    // Constructors
    public NewComerDto() {}
    
    public NewComerDto(String name, String visitorId, String email, 
                      String phone, String aadhaar, String purpose) {
        this.name = name;
        this.visitorId = visitorId;
        this.email = email;
        this.phone = phone;
        this.aadhaar = aadhaar;
        this.purpose = purpose;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getVisitorId() {
        return visitorId;
    }
    
    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAadhaar() {
        return aadhaar;
    }
    
    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }
    
    public String getPurpose() {
        return purpose;
    }
    
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
