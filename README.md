# Sagara Hospital Management System

A comprehensive hospital management system built with Spring Boot, featuring separate portals for administrators, doctors, and patients.

## Features

### Admin Portal
- Doctor management (add, edit, delete)
- Appointment oversight
- Patient schedule management
- System reports

### Doctor Portal  
- Patient records management
- Medical records (create, edit, delete)
- Prescription management
- Appointment scheduling

### Patient Portal
- Personal profile management
- Appointment booking
- Medical history viewing
- Schedule management

## Technology Stack
- **Backend**: Spring Boot 3.1.0, Java 17
- **Database**: MySQL 8.0
- **Frontend**: HTML5, CSS3, JavaScript
- **Template Engine**: Thymeleaf

## Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/loki407/sagara-hospital-management.git
   cd sagara-hospital-management
   ```

2. **Configure MySQL Database**
   - Create database: `hospital_db`
   - Update `src/main/resources/application.properties` with your MySQL credentials

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Local: `http://localhost:8080`
   - Mobile: `http://YOUR_IP:8080`

## Default Login Credentials

- **Admin**: `admin` / `admin123`
- **Doctor**: `dr.smith` / `doc123`
- **Patient**: Register new account or use generated credentials

## Key Features
- ✅ Comprehensive error handling
- ✅ Mobile-responsive design
- ✅ Role-based authentication
- ✅ Real-time data management
- ✅ Patient scheduling system
- ✅ Medical records management

## Project Structure
```
src/main/
├── java/com/hospital/
│   ├── HospitalApp.java
│   ├── HospitalController.java
│   ├── DataInitializer.java
│   └── entities/
└── resources/
    ├── templates/
    ├── static/
    └── application.properties
```