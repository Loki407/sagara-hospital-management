package com.hospital;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String patientName;
    private String patientEmail;
    private String doctorName;
    private String doctorUsername;
    private String medication;
    private String dosage;
    private String frequency;
    private String duration;
    private String instructions;
    private LocalDateTime prescribedDate;
    private String status = "Active";
    
    public Prescription() {
        this.prescribedDate = LocalDateTime.now();
    }
    
    public Prescription(String patientName, String patientEmail, String doctorName, String doctorUsername,
                      String medication, String dosage, String frequency, String duration, String instructions) {
        this.patientName = patientName;
        this.patientEmail = patientEmail;
        this.doctorName = doctorName;
        this.doctorUsername = doctorUsername;
        this.medication = medication;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
        this.instructions = instructions;
        this.prescribedDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    
    public String getPatientEmail() { return patientEmail; }
    public void setPatientEmail(String patientEmail) { this.patientEmail = patientEmail; }
    
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    
    public String getDoctorUsername() { return doctorUsername; }
    public void setDoctorUsername(String doctorUsername) { this.doctorUsername = doctorUsername; }
    
    public String getMedication() { return medication; }
    public void setMedication(String medication) { this.medication = medication; }
    
    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    
    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    
    public LocalDateTime getPrescribedDate() { return prescribedDate; }
    public void setPrescribedDate(LocalDateTime prescribedDate) { this.prescribedDate = prescribedDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}