package com.matwhiz.qrinternlogger.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "entry_logs")
public class EntryLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;
    
    @Column(name = "intern_id", length = 20)
    @NotBlank(message = "Intern ID is required")
    private String internId;
    
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    
    @Column(name = "status", length = 20)
    @Size(max = 20, message = "Status must not exceed 20 characters")
    private String status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intern_id", referencedColumnName = "intern_id", insertable = false, updatable = false)
    @JsonIgnore
    private Intern intern;
    
    // Constructors
    public EntryLog() {
        this.timestamp = LocalDateTime.now();
        this.status = "entered";
    }
    
    public EntryLog(String internId, String status) {
        this();
        this.internId = internId;
        this.status = status;
    }
    
    // Getters and Setters
    public Long getLogId() {
        return logId;
    }
    
    public void setLogId(Long logId) {
        this.logId = logId;
    }
    
    public String getInternId() {
        return internId;
    }
    
    public void setInternId(String internId) {
        this.internId = internId;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Intern getIntern() {
        return intern;
    }
    
    public void setIntern(Intern intern) {
        this.intern = intern;
    }
    
    @Override
    public String toString() {
        return "EntryLog{" +
                "logId=" + logId +
                ", internId='" + internId + '\'' +
                ", timestamp=" + timestamp +
                ", status='" + status + '\'' +
                '}';
    }
}
