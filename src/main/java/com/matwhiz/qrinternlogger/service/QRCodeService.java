package com.matwhiz.qrinternlogger.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class QRCodeService {
    
    private static final int QR_CODE_WIDTH = 300;
    private static final int QR_CODE_HEIGHT = 300;
    private static final String QR_CODE_DIRECTORY = "qr-codes";
    
    public String generateAuthCode() {
        try {
            String randomString = UUID.randomUUID().toString();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(randomString.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating auth code", e);
        }
    }
    
    public byte[] generateQRCodeImage(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, QR_CODE_WIDTH, QR_CODE_HEIGHT);
        
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }
    
    public String saveQRCodeToFile(String internId, String authCode) throws WriterException, IOException {
        // Ensure directory exists
        Path qrCodeDir = Paths.get(QR_CODE_DIRECTORY);
        if (!Files.exists(qrCodeDir)) {
            Files.createDirectories(qrCodeDir);
        }
        
        String fileName = internId + ".png";
        Path filePath = qrCodeDir.resolve(fileName);
        
        byte[] qrCodeImage = generateQRCodeImage(authCode);
        Files.write(filePath, qrCodeImage);
        
        return filePath.toString();
    }
    
    public byte[] getQRCodeImage(String authCode) throws WriterException, IOException {
        return generateQRCodeImage(authCode);
    }
}
