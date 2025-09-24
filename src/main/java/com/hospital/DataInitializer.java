package com.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    
    @Autowired
    private DoctorRepo doctorRepo;
    
    @Autowired
    private MedicalRecordRepo medicalRecordRepo;
    
    @Autowired
    private PrescriptionRepo prescriptionRepo;
    
    @Autowired
    private PatientScheduleRepo patientScheduleRepo;
    
    @Override
    public void run(String... args) throws Exception {
        try {
            logger.info("Starting data initialization process...");
            
            // Clear existing data with error handling
            try {
                logger.info("Clearing existing medical records...");
                medicalRecordRepo.deleteAll();
                logger.info("Medical records cleared successfully");
            } catch (Exception e) {
                logger.error("Error clearing medical records: {}", e.getMessage(), e);
                throw new RuntimeException("Failed to clear medical records", e);
            }
            
            try {
                logger.info("Clearing existing doctors...");
                doctorRepo.deleteAll();
                logger.info("Doctors cleared successfully");
            } catch (Exception e) {
                logger.error("Error clearing doctors: {}", e.getMessage(), e);
                throw new RuntimeException("Failed to clear doctors", e);
            }
            
            // Add fresh mock doctors with login credentials
            try {
                logger.info("Adding fresh mock doctors with secure credentials...");
                doctorRepo.save(new Doctor("John", "Smith", "Cardiology", "john.smith@hospital.com", "dr.smith", "doc123"));
                doctorRepo.save(new Doctor("Sarah", "Johnson", "Pediatrics", "sarah.johnson@hospital.com", "dr.johnson", "doc123"));
                doctorRepo.save(new Doctor("Michael", "Brown", "Orthopedics", "michael.brown@hospital.com", "dr.brown", "doc123"));
                doctorRepo.save(new Doctor("Emily", "Davis", "Dermatology", "emily.davis@hospital.com", "dr.davis", "doc123"));
                doctorRepo.save(new Doctor("David", "Wilson", "Neurology", "david.wilson@hospital.com", "dr.wilson", "doc123"));
                doctorRepo.save(new Doctor("Lisa", "Garcia", "Gynecology", "lisa.garcia@hospital.com", "dr.garcia", "doc123"));
                logger.info("Fresh mock doctors added successfully!");
            } catch (Exception e) {
                logger.error("Error adding mock doctors: {}", e.getMessage(), e);
                throw new RuntimeException("Failed to add mock doctors", e);
            }
        
            // Add fresh mock medical records
            try {
                logger.info("Adding fresh mock medical records...");
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
                logger.info("Fresh mock medical records added successfully!");
            } catch (Exception e) {
                logger.error("Error adding mock medical records: {}", e.getMessage(), e);
                throw new RuntimeException("Failed to add mock medical records", e);
            }
            
            // Add fresh mock prescriptions
            try {
                logger.info("Adding fresh mock prescriptions...");
                prescriptionRepo.save(new Prescription("John Doe", "patient@demo.com", "Dr. John Smith", "dr.smith", 
                    "Lisinopril", "10mg", "Once daily", "30 days", "Take with food, monitor blood pressure"));
                prescriptionRepo.save(new Prescription("Jane Smith", "jane.smith@email.com", "Dr. Sarah Johnson", "dr.johnson", 
                    "Amoxicillin", "500mg", "Three times daily", "7 days", "Complete full course even if feeling better"));
                prescriptionRepo.save(new Prescription("Mike Johnson", "mike.johnson@email.com", "Dr. Michael Brown", "dr.brown", 
                    "Ibuprofen", "400mg", "As needed", "14 days", "Take with food, maximum 3 times per day"));
                logger.info("Fresh mock prescriptions added successfully!");
            } catch (Exception e) {
                logger.error("Error adding mock prescriptions: {}", e.getMessage(), e);
                throw new RuntimeException("Failed to add mock prescriptions", e);
            }
            
            // Clear existing patient schedules
            try {
                logger.info("Clearing existing patient schedules...");
                patientScheduleRepo.deleteAll();
                logger.info("Patient schedules cleared successfully");
            } catch (Exception e) {
                logger.error("Error clearing patient schedules: {}", e.getMessage(), e);
                throw new RuntimeException("Failed to clear patient schedules", e);
            }
            
            // Add fresh mock patient schedules with varied statuses
            try {
                logger.info("Adding fresh mock patient schedules...");
                
                // Patient 1 (patient@demo.com) - John Doe schedules
                PatientSchedule schedule1 = new PatientSchedule("patient@demo.com", "John Doe", "Dr. John Smith", 
                    "Consultation", java.time.LocalDateTime.now().minusDays(5).withHour(9).withMinute(0), 
                    "Room 101", "Regular checkup");
                schedule1.setStatus("Completed");
                patientScheduleRepo.save(schedule1);
                
                PatientSchedule schedule2 = new PatientSchedule("patient@demo.com", "John Doe", "Dr. Sarah Johnson", 
                    "Follow-up", java.time.LocalDateTime.now().minusDays(2).withHour(14).withMinute(30), 
                    "Room 205", "Blood pressure monitoring");
                schedule2.setStatus("Completed");
                patientScheduleRepo.save(schedule2);
                
                PatientSchedule schedule3 = new PatientSchedule("patient@demo.com", "John Doe", "Dr. Michael Brown", 
                    "Consultation", java.time.LocalDateTime.now().plusDays(1).withHour(10).withMinute(0), 
                    "Room 103", "Orthopedic consultation");
                schedule3.setStatus("Scheduled");
                patientScheduleRepo.save(schedule3);
                
                PatientSchedule schedule4 = new PatientSchedule("patient@demo.com", "John Doe", "Dr. Emily Davis", 
                    "Follow-up", java.time.LocalDateTime.now().plusDays(4).withHour(15).withMinute(0), 
                    "Room 302", "Skin condition review");
                schedule4.setStatus("Scheduled");
                patientScheduleRepo.save(schedule4);
                
                // Patient 2 (jane.smith@email.com) - Jane Smith schedules
                PatientSchedule schedule5 = new PatientSchedule("jane.smith@email.com", "Jane Smith", "Dr. David Wilson", 
                    "Consultation", java.time.LocalDateTime.now().minusDays(7).withHour(11).withMinute(0), 
                    "Room 201", "Neurological assessment");
                schedule5.setStatus("Completed");
                patientScheduleRepo.save(schedule5);
                
                PatientSchedule schedule6 = new PatientSchedule("jane.smith@email.com", "Jane Smith", "Dr. Lisa Garcia", 
                    "Follow-up", java.time.LocalDateTime.now().minusDays(3).withHour(16).withMinute(30), 
                    "Room 304", "Gynecological checkup");
                schedule6.setStatus("Completed");
                patientScheduleRepo.save(schedule6);
                
                PatientSchedule schedule7 = new PatientSchedule("jane.smith@email.com", "Jane Smith", "Dr. John Smith", 
                    "Emergency", java.time.LocalDateTime.now().plusHours(6), 
                    "Emergency Room", "Chest pain evaluation");
                schedule7.setStatus("Scheduled");
                patientScheduleRepo.save(schedule7);
                
                PatientSchedule schedule8 = new PatientSchedule("jane.smith@email.com", "Jane Smith", "Dr. Sarah Johnson", 
                    "Consultation", java.time.LocalDateTime.now().plusDays(3).withHour(9).withMinute(30), 
                    "Room 106", "Pediatric consultation");
                schedule8.setStatus("Scheduled");
                patientScheduleRepo.save(schedule8);
                
                // Patient 3 (mike.johnson@email.com) - Mike Johnson schedules
                PatientSchedule schedule9 = new PatientSchedule("mike.johnson@email.com", "Mike Johnson", "Dr. Emily Davis", 
                    "Consultation", java.time.LocalDateTime.now().minusDays(10).withHour(13).withMinute(0), 
                    "Room 203", "Dermatology consultation");
                schedule9.setStatus("Completed");
                patientScheduleRepo.save(schedule9);
                
                PatientSchedule schedule10 = new PatientSchedule("mike.johnson@email.com", "Mike Johnson", "Dr. Michael Brown", 
                    "Follow-up", java.time.LocalDateTime.now().minusDays(1).withHour(8).withMinute(30), 
                    "Room 105", "Orthopedic follow-up");
                schedule10.setStatus("Completed");
                patientScheduleRepo.save(schedule10);
                
                PatientSchedule schedule11 = new PatientSchedule("mike.johnson@email.com", "Mike Johnson", "Dr. David Wilson", 
                    "Emergency", java.time.LocalDateTime.now().plusHours(2), 
                    "Emergency Room", "Severe headache");
                schedule11.setStatus("Scheduled");
                patientScheduleRepo.save(schedule11);
                
                PatientSchedule schedule12 = new PatientSchedule("mike.johnson@email.com", "Mike Johnson", "Dr. Lisa Garcia", 
                    "Consultation", java.time.LocalDateTime.now().plusDays(7).withHour(11).withMinute(30), 
                    "Room 201", "General health checkup");
                schedule12.setStatus("Scheduled");
                patientScheduleRepo.save(schedule12);
                
                PatientSchedule schedule13 = new PatientSchedule("mike.johnson@email.com", "Mike Johnson", "Dr. John Smith", 
                    "Follow-up", java.time.LocalDateTime.now().plusDays(14).withHour(14).withMinute(0), 
                    "Room 102", "Cardiology follow-up");
                schedule13.setStatus("Scheduled");
                patientScheduleRepo.save(schedule13);
                
                logger.info("Fresh mock patient schedules added successfully!");
            } catch (Exception e) {
                logger.error("Error adding mock patient schedules: {}", e.getMessage(), e);
                throw new RuntimeException("Failed to add mock patient schedules", e);
            }
            
            logger.info("Data initialization completed successfully!");
        } catch (Exception e) {
            logger.error("Critical error during data initialization: {}", e.getMessage(), e);
            throw new RuntimeException("Data initialization failed", e);
        }
    }
}