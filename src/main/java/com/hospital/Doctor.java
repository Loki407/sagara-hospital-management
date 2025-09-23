package com.hospital;

import jakarta.persistence.*;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String specialty;
    private String email;
    private String username;
    private String password;
    private String role = "DOCTOR";
    private boolean isActive = true;
    
    public Doctor() {}
    
    public Doctor(String firstName, String lastName, String specialty, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialty = specialty;
        this.email = email;
    }
    
    public Doctor(String firstName, String lastName, String specialty, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialty = specialty;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}