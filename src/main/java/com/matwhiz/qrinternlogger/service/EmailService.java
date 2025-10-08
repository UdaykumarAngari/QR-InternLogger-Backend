package com.matwhiz.qrinternlogger.service;

import com.matwhiz.qrinternlogger.entity.Intern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ByteArrayResource;
import com.google.zxing.WriterException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private QRCodeService qrCodeService;
    
    private static final String FROM_EMAIL = "angariudaykumar@gmail.com";
    private static final String CSO_EMAIL = "angariuday@gmail.com"; // CSO email for alerts
    
    public void sendQRCodeToIntern(Intern intern) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setFrom(FROM_EMAIL, "MatWhiz Internship Team");
            helper.setTo(intern.getEmail());
            helper.setSubject("Your Intern QR Code - MatWhiz");
            
            String htmlContent = String.format("""
                <html>
                <body>
                    <h2>Welcome to MatWhiz!</h2>
                    <p>Dear %s,</p>
                    <p>Congratulations on being selected as an intern at <strong>MatWhiz</strong>!</p>
                    <p>Attached is your unique QR code, which you'll need to present at the gate for entry. 
                    Please keep this QR code safe and do not share it with anyone.</p>
                    <p>We're excited to have you on board and look forward to your contributions.</p>
                    <p>Best regards,<br>MatWhiz Team</p>
                </body>
                </html>
                """, intern.getName());
            
            helper.setText(htmlContent, true);
            
            // Use QR image stored in DB; fallback to generate if null
            byte[] qrCodeImage = (intern.getQrImage() != null && intern.getQrImage().length > 0)
                    ? intern.getQrImage()
                    : qrCodeService.getQRCodeImage(intern.getAuthCode());
            helper.addAttachment("Intern_QR_Code.png", new ByteArrayResource(qrCodeImage));
            
            
            mailSender.send(message);
        } catch (MessagingException | IOException | WriterException e) {
            throw new RuntimeException("Failed to send email to intern", e);
        }
    }
    
    public void sendCSOAlert(Intern intern, String status) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(FROM_EMAIL);
            message.setTo(CSO_EMAIL);
            
            if ("success".equals(status)) {
                message.setSubject("Intern Entry Alert - " + intern.getName());
                message.setText(String.format("""
                    Intern Entry Alert
                    
                    Intern ID: %s
                    Name: %s
                    Email: %s
                    Entry Time: %s
                    Status: Successfully entered
                    
                    This is an automated notification from the QR Intern Logger system.
                    """, intern.getInternId(), intern.getName(), intern.getEmail(), 
                    java.time.LocalDateTime.now()));
            } else {
                message.setSubject("Failed Entry Attempt Alert");
                message.setText(String.format("""
                    Failed Entry Attempt Alert
                    
                    Intern ID: %s
                    Name: %s
                    Email: %s
                    Attempt Time: %s
                    Status: Failed entry attempt
                    
                    This is an automated notification from the QR Intern Logger system.
                    """, intern.getInternId(), intern.getName(), intern.getEmail(), 
                    java.time.LocalDateTime.now()));
            }
            
            mailSender.send(message);
        } catch (Exception e) {
            // Log error but don't throw exception to avoid breaking the main flow
            System.err.println("Failed to send CSO alert: " + e.getMessage());
        }
    }
    
    public void sendNewComerNotification(String name, String visitorId, String purpose) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(FROM_EMAIL);
            message.setTo(CSO_EMAIL);
            message.setSubject("New Visitor Entry - " + name);
            message.setText(String.format("""
                New Visitor Entry Alert
                
                Visitor Name: %s
                Visitor ID: %s
                Purpose: %s
                Entry Time: %s
                
                This is an automated notification from the QR Intern Logger system.
                """, name, visitorId, purpose, java.time.LocalDateTime.now()));
            
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send new comer notification: " + e.getMessage());
        }
    }
}
