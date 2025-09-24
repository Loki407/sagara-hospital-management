package com.hospital;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PatientSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String patientEmail;
    private String patientName;
    private String doctorName;
    private String appointmentType;
    private LocalDateTime appointmentDateTime;
    private String status = "Scheduled";
    private String notes;
    private String location;
    private boolean reminderSent = false;
    
    public PatientSchedule() {}
    
    public PatientSchedule(String patientEmail, String patientName, String doctorName, 
                          String appointmentType, LocalDateTime appointmentDateTime, 
                          String location, String notes) {
        this.patientEmail = patientEmail;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.appointmentType = appointmentType;
        this.appointmentDateTime = appointmentDateTime;
        this.location = location;
        this.notes = notes;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getPatientEmail() { return patientEmail; }
    public void setPatientEmail(String patientEmail) { this.patientEmail = patientEmail; }
    
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    
    public String getAppointmentType() { return appointmentType; }
    public void setAppointmentType(String appointmentType) { this.appointmentType = appointmentType; }
    
    public LocalDateTime getAppointmentDateTime() { return appointmentDateTime; }
    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) { this.appointmentDateTime = appointmentDateTime; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public boolean isReminderSent() { return reminderSent; }
    public void setReminderSent(boolean reminderSent) { this.reminderSent = reminderSent; }
}