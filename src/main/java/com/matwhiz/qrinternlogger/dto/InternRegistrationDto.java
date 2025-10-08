package com.matwhiz.qrinternlogger.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class InternRegistrationDto {
    
    @NotBlank(message = "Intern ID is required")
    @Size(max = 20, message = "Intern ID must not exceed 20 characters")
    private String internId;
    
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;
    
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;
    
    @Pattern(regexp = "\\d{12}", message = "Aadhaar number must be 12 digits")
    private String aadhaarNumber;
    
    @Pattern(regexp = "\\d{10,15}", message = "Mobile number must be 10-15 digits")
    private String mobileNumber;
    
    // Constructors
    public InternRegistrationDto() {}
    
    public InternRegistrationDto(String internId, String name, String email, 
                               String aadhaarNumber, String mobileNumber) {
        this.internId = internId;
        this.name = name;
        this.email = email;
        this.aadhaarNumber = aadhaarNumber;
        this.mobileNumber = mobileNumber;
    }
    
    // Getters and Setters
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
    
    public String getAadhaarNumber() {
        return aadhaarNumber;
    }
    
    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }
    
    public String getMobileNumber() {
        return mobileNumber;
    }
    
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
