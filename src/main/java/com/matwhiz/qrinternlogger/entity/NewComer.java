package com.matwhiz.qrinternlogger.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "new_comers")
public class NewComer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name", length = 100, nullable = false)
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;
    
    @Column(name = "visitor_id", length = 50, nullable = false)
    @NotBlank(message = "Visitor ID is required")
    @Size(max = 50, message = "Visitor ID must not exceed 50 characters")
    private String visitorId;
    
    @Column(name = "email", length = 100)
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;
    
    @Column(name = "phone", length = 15)
    @Pattern(regexp = "\\d{10,15}", message = "Phone number must be 10-15 digits")
    private String phone;
    
    @Column(name = "aadhaar", length = 20)
    @Pattern(regexp = "\\d{12}", message = "Aadhaar number must be 12 digits")
    private String aadhaar;
    
    @Column(name = "purpose", columnDefinition = "TEXT")
    @NotBlank(message = "Purpose is required")
    private String purpose;
    
    @Column(name = "entry_time")
    private LocalDateTime entryTime;
    
    // Constructors
    public NewComer() {
        this.entryTime = LocalDateTime.now();
    }
    
    public NewComer(String name, String visitorId, String email, String phone, 
                   String aadhaar, String purpose) {
        this();
        this.name = name;
        this.visitorId = visitorId;
        this.email = email;
        this.phone = phone;
        this.aadhaar = aadhaar;
        this.purpose = purpose;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public LocalDateTime getEntryTime() {
        return entryTime;
    }
    
    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }
    
    @Override
    public String toString() {
        return "NewComer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", visitorId='" + visitorId + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", aadhaar='" + aadhaar + '\'' +
                ", purpose='" + purpose + '\'' +
                ", entryTime=" + entryTime +
                '}';
    }
}
