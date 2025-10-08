package com.matwhiz.qrinternlogger.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "interns")
public class Intern {
    
    @Id
    @Column(name = "intern_id", length = 20)
    @NotBlank(message = "Intern ID is required")
    private String internId;
    
    @Column(name = "name", length = 100)
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;
    
    @Column(name = "email", length = 100)
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;
    
    @Column(name = "aadhaar_number", length = 12, unique = true)
    @Pattern(regexp = "\\d{12}", message = "Aadhaar number must be 12 digits")
    private String aadhaarNumber;
    
    @Column(name = "mobile_number", length = 15)
    @Pattern(regexp = "\\d{10,15}", message = "Mobile number must be 10-15 digits")
    private String mobileNumber;
    
    @Column(name = "auth_code", length = 64)
    @NotBlank(message = "Auth code is required")
    private String authCode;
    
    @Lob
    @JdbcTypeCode(SqlTypes.VARBINARY) // ensure BYTEA in PostgreSQL, not OID
    @Column(name = "qr_image")
    private byte[] qrImage;
    
    // Constructors
    public Intern() {}
    
    public Intern(String internId, String name, String email, String aadhaarNumber, 
                 String mobileNumber, String authCode) {
        this.internId = internId;
        this.name = name;
        this.email = email;
        this.aadhaarNumber = aadhaarNumber;
        this.mobileNumber = mobileNumber;
        this.authCode = authCode;
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
    
    public String getAuthCode() {
        return authCode;
    }
    
    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
    
    public byte[] getQrImage() {
        return qrImage;
    }
    
    public void setQrImage(byte[] qrImage) {
        this.qrImage = qrImage;
    }
    
    @Override
    public String toString() {
        return "Intern{" +
                "internId='" + internId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", aadhaarNumber='" + aadhaarNumber + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", authCode='" + authCode + '\'' +
                '}';
    }
}
