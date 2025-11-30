# QR Intern Logger - Backend (Spring Boot)

A Spring Boot backend for a QR code-based intern attendance logging system.  
This service handles intern and visitor management, QR code generation, entry logging, and email notifications.

## Features

- Intern registration with auth code storage
- QR code generation for each intern
- Entry logging using QR-authenticated links
- Visitor registration and logging
- Email notifications to CSO and interns
- CSV-ready data via API for exports
- Secure REST API with validation and error handling

## Architecture

### Tech Stack

- **Framework**: Spring Boot 3.2.x
- **Language**: Java 17+
- **Database**: Relational DB (schema below; tested with MySQL / PostgreSQL)
- **ORM**: JPA / Hibernate
- **QR Code**: ZXing library
- **Email**: SMTP (e.g., Gmail)
- **Build Tool**: Maven

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- MySQL or PostgreSQL (adjust URL/driver accordingly)

## Database Setup

Example schema (MySQL-style):

```sql
CREATE DATABASE IF NOT EXISTS intern_db;
USE intern_db;

CREATE TABLE interns (
    intern_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    aadhaar_number VARCHAR(12) UNIQUE,
    mobile_number VARCHAR(15),
    auth_code VARCHAR(64)
);

CREATE TABLE entry_logs (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    intern_id VARCHAR(20),
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'entered',
    FOREIGN KEY (intern_id) REFERENCES interns(intern_id)
);

CREATE TABLE new_comers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    visitor_id VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(15),
    aadhaar VARCHAR(20),
    purpose TEXT,
    entry_time DATETIME DEFAULT CURRENT_TIMESTAMP
);
