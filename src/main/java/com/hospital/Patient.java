package com.hospital;

import jakarta.persistence.*;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String dateOfBirth;
    private String address;
    private String bloodGroup;
    private String allergies;
    private String emergencyContact;
    private String emergencyPhone;
    private String guardianName;
    private String guardianPhone;
    private String guardianRelation;
    private String medicalHistory;
    private String username;
    private String password;
    private String role = "PATIENT";
    private boolean active = true;
    
    public Patient() {}
    
    public Patient(String firstName, String lastName, String email, String phone, String dateOfBirth, String address, String bloodGroup, String allergies, String emergencyContact, String emergencyPhone, String guardianName, String guardianPhone, String guardianRelation, String medicalHistory, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.bloodGroup = bloodGroup;
        this.allergies = allergies;
        this.emergencyContact = emergencyContact;
        this.emergencyPhone = emergencyPhone;
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
        this.guardianRelation = guardianRelation;
        this.medicalHistory = medicalHistory;
        this.username = username;
        this.password = password;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    
    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }
    
    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }
    
    public String getEmergencyPhone() { return emergencyPhone; }
    public void setEmergencyPhone(String emergencyPhone) { this.emergencyPhone = emergencyPhone; }
    
    public String getGuardianName() { return guardianName; }
    public void setGuardianName(String guardianName) { this.guardianName = guardianName; }
    
    public String getGuardianPhone() { return guardianPhone; }
    public void setGuardianPhone(String guardianPhone) { this.guardianPhone = guardianPhone; }
    
    public String getGuardianRelation() { return guardianRelation; }
    public void setGuardianRelation(String guardianRelation) { this.guardianRelation = guardianRelation; }
    
    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }
}