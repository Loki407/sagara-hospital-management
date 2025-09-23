package com.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private DoctorRepo doctorRepo;
    
    @Autowired
    private MedicalRecordRepo medicalRecordRepo;
    
    @Autowired
    private PrescriptionRepo prescriptionRepo;
    
    @Override
    public void run(String... args) throws Exception {
        try {
            System.out.println("Clearing existing data and adding fresh mock data...");
            
            // Clear existing data
            System.out.println("Clearing existing medical records...");
            medicalRecordRepo.deleteAll();
            
            System.out.println("Clearing existing doctors...");
            doctorRepo.deleteAll();
            
            // Add fresh mock doctors with login credentials
            System.out.println("Adding fresh mock doctors with secure credentials...");
            doctorRepo.save(new Doctor("John", "Smith", "Cardiology", "john.smith@hospital.com", "dr.smith", "doc123"));
            doctorRepo.save(new Doctor("Sarah", "Johnson", "Pediatrics", "sarah.johnson@hospital.com", "dr.johnson", "doc123"));
            doctorRepo.save(new Doctor("Michael", "Brown", "Orthopedics", "michael.brown@hospital.com", "dr.brown", "doc123"));
            doctorRepo.save(new Doctor("Emily", "Davis", "Dermatology", "emily.davis@hospital.com", "dr.davis", "doc123"));
            doctorRepo.save(new Doctor("David", "Wilson", "Neurology", "david.wilson@hospital.com", "dr.wilson", "doc123"));
            doctorRepo.save(new Doctor("Lisa", "Garcia", "Gynecology", "lisa.garcia@hospital.com", "dr.garcia", "doc123"));
            System.out.println("Fresh mock doctors added successfully!");
        
            // Add fresh mock medical records
            System.out.println("Adding fresh mock medical records...");
            // Patient 1 records
            MedicalRecord record1 = new MedicalRecord();
            record1.setPatientName("John Doe");
            record1.setPatientEmail("patient@demo.com");
            record1.setDoctorName("Dr. John Smith");
            record1.setVisitDate("2024-01-15");
            record1.setDiagnosis("Hypertension");
            record1.setTreatment("Lisinopril 10mg daily");
            record1.setNotes("Patient advised to reduce salt intake and exercise regularly");
            medicalRecordRepo.save(record1);
            
            MedicalRecord record2 = new MedicalRecord();
            record2.setPatientName("John Doe");
            record2.setPatientEmail("patient@demo.com");
            record2.setDoctorName("Dr. Sarah Johnson");
            record2.setVisitDate("2024-02-10");
            record2.setDiagnosis("Common Cold");
            record2.setTreatment("Rest and fluids, Acetaminophen as needed");
            record2.setNotes("Symptoms should resolve in 7-10 days");
            medicalRecordRepo.save(record2);
            
            // Patient 2 records
            MedicalRecord record3 = new MedicalRecord();
            record3.setPatientName("Jane Smith");
            record3.setPatientEmail("jane.smith@email.com");
            record3.setDoctorName("Dr. Michael Brown");
            record3.setVisitDate("2024-01-20");
            record3.setDiagnosis("Sprained Ankle");
            record3.setTreatment("RICE protocol, Ibuprofen 400mg");
            record3.setNotes("Follow up in 2 weeks if pain persists");
            medicalRecordRepo.save(record3);
            
            MedicalRecord record4 = new MedicalRecord();
            record4.setPatientName("Jane Smith");
            record4.setPatientEmail("jane.smith@email.com");
            record4.setDoctorName("Dr. Emily Davis");
            record4.setVisitDate("2024-02-05");
            record4.setDiagnosis("Eczema");
            record4.setTreatment("Hydrocortisone cream 1%");
            record4.setNotes("Apply twice daily, avoid harsh soaps");
            medicalRecordRepo.save(record4);
            
            // Patient 3 records
            MedicalRecord record5 = new MedicalRecord();
            record5.setPatientName("Mike Johnson");
            record5.setPatientEmail("mike.johnson@email.com");
            record5.setDoctorName("Dr. David Wilson");
            record5.setVisitDate("2024-01-25");
            record5.setDiagnosis("Migraine");
            record5.setTreatment("Sumatriptan 50mg as needed");
            record5.setNotes("Identify and avoid triggers, maintain sleep schedule");
            medicalRecordRepo.save(record5);
            System.out.println("Fresh mock medical records added successfully!");
            
            // Add fresh mock prescriptions
            System.out.println("Adding fresh mock prescriptions...");
            prescriptionRepo.save(new Prescription("John Doe", "patient@demo.com", "Dr. John Smith", "dr.smith", 
                "Lisinopril", "10mg", "Once daily", "30 days", "Take with food, monitor blood pressure"));
            prescriptionRepo.save(new Prescription("Jane Smith", "jane.smith@email.com", "Dr. Sarah Johnson", "dr.johnson", 
                "Amoxicillin", "500mg", "Three times daily", "7 days", "Complete full course even if feeling better"));
            prescriptionRepo.save(new Prescription("Mike Johnson", "mike.johnson@email.com", "Dr. Michael Brown", "dr.brown", 
                "Ibuprofen", "400mg", "As needed", "14 days", "Take with food, maximum 3 times per day"));
            System.out.println("Fresh mock prescriptions added successfully!");
            
            System.out.println("Fresh data initialization completed!");
        } catch (Exception e) {
            System.err.println("Error during data initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }
}