package com.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@Repository
interface DoctorRepo extends JpaRepository<Doctor, Long> {
    long count();
    Doctor findByUsernameAndPassword(String username, String password);
    Doctor findByUsername(String username);
}

@Repository  
interface AppointmentRepo extends JpaRepository<Appointment, Long> {}

@Repository
interface MedicalRecordRepo extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPatientEmail(String patientEmail);
    long count();
}

@Repository
interface PrescriptionRepo extends JpaRepository<Prescription, Long> {
    List<Prescription> findByDoctorUsername(String doctorUsername);
    List<Prescription> findByPatientEmail(String patientEmail);
    long count();
}

@Repository
interface PatientRepo extends JpaRepository<Patient, Long> {
    Patient findByUsernameAndPassword(String username, String password);
    Patient findByUsername(String username);
    Patient findByEmail(String email);
    long count();
}

@Repository
interface PatientScheduleRepo extends JpaRepository<PatientSchedule, Long> {
    List<PatientSchedule> findByPatientEmail(String patientEmail);
    List<PatientSchedule> findByPatientEmailOrderByAppointmentDateTimeAsc(String patientEmail);
    long count();
}

@Controller
public class HospitalController {
    
    private static final Logger logger = LoggerFactory.getLogger(HospitalController.class);
    
    @Autowired DoctorRepo doctorRepo;
    @Autowired AppointmentRepo appointmentRepo;
    @Autowired MedicalRecordRepo medicalRecordRepo;
    @Autowired PrescriptionRepo prescriptionRepo;
    @Autowired PatientRepo patientRepo;
    @Autowired PatientScheduleRepo patientScheduleRepo;
    
    @GetMapping("/doctors") public String doctors() { return "doctors"; }
    @GetMapping("/appointments") public String appointments() { return "appointments"; }
    @GetMapping("/admin") public String admin() { return "admin"; }
    
    @GetMapping("/login") public String login() { return "login"; }
    @GetMapping("/admin_dashboard") public String adminDashboard() { return "admin_dashboard"; }
    @GetMapping("/patient_dashboard") public String patientDashboard() { return "patient_dashboard"; }
    @GetMapping("/book_appointment") public String bookAppointment() { return "book_appointment"; }
    @GetMapping("/view_doctors") public String viewDoctors() { return "view_doctors"; }
    @GetMapping("/patient_doctors") public String patientDoctors() { return "patient_doctors"; }
    @GetMapping("/admin_doctors") public String adminDoctors() { return "admin_doctors"; }
    @GetMapping("/patient_home") public String patientHome() { return "patient_dashboard"; }
    @GetMapping("/patient_profile") public String patientProfile() { return "patient_profile"; }
    
    @GetMapping("/api/doctors") @ResponseBody
    public ResponseEntity<List<Doctor>> getDoctors() {
        try {
            List<Doctor> doctors = doctorRepo.findAll();
            logger.info("Retrieved {} doctors", doctors.size());
            return ResponseEntity.ok(doctors);
        } catch (Exception e) {
            logger.error("Error retrieving doctors: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/api/doctors") @ResponseBody
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor) {
        try {
            if (doctor == null || doctor.getFirstName() == null || doctor.getLastName() == null) {
                logger.warn("Invalid doctor data provided");
                return ResponseEntity.badRequest().build();
            }
            
            // Generate random credentials
            String username = "dr." + doctor.getLastName().toLowerCase() + (int)(Math.random() * 1000);
            String password = "doc" + (int)(Math.random() * 9000 + 1000);
            
            doctor.setUsername(username);
            doctor.setPassword(password);
            doctor.setRole("DOCTOR");
            doctor.setActive(true);
            
            Doctor savedDoctor = doctorRepo.save(doctor);
            savedDoctor.setPassword(password); // Temporarily include for display
            
            logger.info("Doctor added successfully: {}", username);
            return ResponseEntity.ok(savedDoctor);
        } catch (Exception e) {
            logger.error("Error adding doctor: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/api/doctors/{id}") @ResponseBody
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                logger.warn("Invalid doctor ID provided: {}", id);
                return ResponseEntity.badRequest().build();
            }
            
            if (!doctorRepo.existsById(id)) {
                logger.warn("Doctor not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            
            doctorRepo.deleteById(id);
            logger.info("Doctor deleted successfully: {}", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting doctor with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/api/appointments") @ResponseBody
    public List<Appointment> getAppointments() { return appointmentRepo.findAll(); }
    
    @PostMapping("/api/appointments") @ResponseBody
    public ResponseEntity<Appointment> bookAppointment(@RequestBody Appointment appointment) {
        try {
            if (appointment == null) {
                logger.warn("Invalid appointment data provided");
                return ResponseEntity.badRequest().build();
            }
            
            Appointment savedAppointment = appointmentRepo.save(appointment);
            logger.info("Appointment booked successfully");
            return ResponseEntity.ok(savedAppointment);
        } catch (Exception e) {
            logger.error("Error booking appointment: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/api/appointments/{id}") @ResponseBody
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                logger.warn("Invalid appointment ID provided: {}", id);
                return ResponseEntity.badRequest().build();
            }
            
            if (!appointmentRepo.existsById(id)) {
                logger.warn("Appointment not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            
            appointmentRepo.deleteById(id);
            logger.info("Appointment deleted successfully: {}", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting appointment with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/api/appointments/{id}") @ResponseBody
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        try {
            if (id == null || id <= 0 || appointment == null) {
                logger.warn("Invalid appointment update data provided");
                return ResponseEntity.badRequest().build();
            }
            
            if (!appointmentRepo.existsById(id)) {
                logger.warn("Appointment not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            
            appointment.setId(id);
            Appointment updatedAppointment = appointmentRepo.save(appointment);
            logger.info("Appointment updated successfully: {}", id);
            return ResponseEntity.ok(updatedAppointment);
        } catch (Exception e) {
            logger.error("Error updating appointment with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/api/appointments/{id}/status") @ResponseBody
    public ResponseEntity<Appointment> updateAppointmentStatus(@PathVariable Long id, @RequestBody String status) {
        try {
            if (id == null || id <= 0 || status == null || status.trim().isEmpty()) {
                logger.warn("Invalid appointment status update data provided");
                return ResponseEntity.badRequest().build();
            }
            
            Optional<Appointment> appointmentOpt = appointmentRepo.findById(id);
            if (!appointmentOpt.isPresent()) {
                logger.warn("Appointment not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            
            Appointment appointment = appointmentOpt.get();
            appointment.setStatus(status.replace("\"", ""));
            Appointment updatedAppointment = appointmentRepo.save(appointment);
            logger.info("Appointment status updated successfully: {}", id);
            return ResponseEntity.ok(updatedAppointment);
        } catch (Exception e) {
            logger.error("Error updating appointment status with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/api/medical-records/{email}") @ResponseBody
    public ResponseEntity<List<MedicalRecord>> getMedicalRecords(@PathVariable String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                logger.warn("Invalid email provided for medical records");
                return ResponseEntity.badRequest().build();
            }
            
            List<MedicalRecord> records = medicalRecordRepo.findByPatientEmail(email);
            logger.info("Retrieved {} medical records for email: {}", records.size(), email);
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            logger.error("Error retrieving medical records for email {}: {}", email, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/api/medical-records") @ResponseBody
    public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord record) {
        try {
            if (record == null || record.getPatientEmail() == null) {
                logger.warn("Invalid medical record data provided");
                return ResponseEntity.badRequest().build();
            }
            
            MedicalRecord savedRecord = medicalRecordRepo.save(record);
            logger.info("Medical record added successfully for patient: {}", record.getPatientEmail());
            return ResponseEntity.ok(savedRecord);
        } catch (Exception e) {
            logger.error("Error adding medical record: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/api/prescriptions/{doctorUsername}") @ResponseBody
    public List<Prescription> getDoctorPrescriptions(@PathVariable String doctorUsername) {
        return prescriptionRepo.findByDoctorUsername(doctorUsername);
    }
    
    @GetMapping("/api/medical-records/all") @ResponseBody
    public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() {
        try {
            List<MedicalRecord> records = medicalRecordRepo.findAll();
            logger.info("Retrieved {} medical records", records.size());
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            logger.error("Error retrieving all medical records: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/api/medical-records/{id}") @ResponseBody
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable Long id, @RequestBody MedicalRecord record) {
        try {
            if (id == null || id <= 0 || record == null) {
                logger.warn("Invalid medical record update data provided");
                return ResponseEntity.badRequest().build();
            }
            
            if (!medicalRecordRepo.existsById(id)) {
                logger.warn("Medical record not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            
            record.setId(id);
            MedicalRecord updatedRecord = medicalRecordRepo.save(record);
            logger.info("Medical record updated successfully: {}", id);
            return ResponseEntity.ok(updatedRecord);
        } catch (Exception e) {
            logger.error("Error updating medical record with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/api/medical-records/{id}") @ResponseBody
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                logger.warn("Invalid medical record ID provided: {}", id);
                return ResponseEntity.badRequest().build();
            }
            
            if (!medicalRecordRepo.existsById(id)) {
                logger.warn("Medical record not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            
            medicalRecordRepo.deleteById(id);
            logger.info("Medical record deleted successfully: {}", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting medical record with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/api/prescriptions") @ResponseBody
    public ResponseEntity<Prescription> addPrescription(@RequestBody Prescription prescription) {
        try {
            if (prescription == null || prescription.getPatientEmail() == null || prescription.getDoctorUsername() == null) {
                logger.warn("Invalid prescription data provided");
                return ResponseEntity.badRequest().build();
            }
            
            Prescription savedPrescription = prescriptionRepo.save(prescription);
            logger.info("Prescription added successfully for patient: {}", prescription.getPatientEmail());
            return ResponseEntity.ok(savedPrescription);
        } catch (Exception e) {
            logger.error("Error adding prescription: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/api/prescriptions/{id}") @ResponseBody
    public ResponseEntity<Prescription> updatePrescription(@PathVariable Long id, @RequestBody Prescription prescription) {
        try {
            if (id == null || id <= 0 || prescription == null) {
                logger.warn("Invalid prescription update data provided");
                return ResponseEntity.badRequest().build();
            }
            
            if (!prescriptionRepo.existsById(id)) {
                logger.warn("Prescription not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            
            prescription.setId(id);
            Prescription updatedPrescription = prescriptionRepo.save(prescription);
            logger.info("Prescription updated successfully: {}", id);
            return ResponseEntity.ok(updatedPrescription);
        } catch (Exception e) {
            logger.error("Error updating prescription with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/api/prescriptions/{id}") @ResponseBody
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                logger.warn("Invalid prescription ID provided: {}", id);
                return ResponseEntity.badRequest().build();
            }
            
            if (!prescriptionRepo.existsById(id)) {
                logger.warn("Prescription not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            
            prescriptionRepo.deleteById(id);
            logger.info("Prescription deleted successfully: {}", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting prescription with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/api/patients/register") @ResponseBody
    public ResponseEntity<Patient> registerPatient(@RequestBody Patient patient) {
        try {
            if (patient == null || patient.getFirstName() == null || patient.getLastName() == null || patient.getEmail() == null) {
                logger.warn("Invalid patient registration data provided");
                return ResponseEntity.badRequest().build();
            }
            
            // Check if email already exists
            if (patientRepo.findByEmail(patient.getEmail()) != null) {
                logger.warn("Patient with email already exists: {}", patient.getEmail());
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            
            // Generate random credentials
            String username = patient.getFirstName().toLowerCase() + (int)(Math.random() * 1000);
            String password = "pat" + (int)(Math.random() * 9000 + 1000);
            
            patient.setUsername(username);
            patient.setPassword(password);
            patient.setRole("PATIENT");
            patient.setActive(true);
            
            Patient savedPatient = patientRepo.save(patient);
            savedPatient.setPassword(password); // Include for display
            
            logger.info("Patient registered successfully: {}", username);
            return ResponseEntity.ok(savedPatient);
        } catch (Exception e) {
            logger.error("Error registering patient: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/api/auth/patient") @ResponseBody
    public ResponseEntity<Patient> authenticatePatient(@RequestBody LoginRequest request) {
        try {
            if (request == null || request.getUsername() == null || request.getPassword() == null) {
                logger.warn("Invalid patient authentication data provided");
                return ResponseEntity.badRequest().build();
            }
            
            Patient patient = patientRepo.findByUsernameAndPassword(request.getUsername(), request.getPassword());
            if (patient == null) {
                logger.warn("Patient authentication failed for username: {}", request.getUsername());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            if (!patient.isActive()) {
                logger.warn("Inactive patient attempted login: {}", request.getUsername());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            patient.setPassword(null); // Remove password from response
            logger.info("Patient authenticated successfully: {}", request.getUsername());
            return ResponseEntity.ok(patient);
        } catch (Exception e) {
            logger.error("Error authenticating patient: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/api/auth/doctor") @ResponseBody
    public ResponseEntity<Doctor> authenticateDoctor(@RequestBody LoginRequest request) {
        try {
            if (request == null || request.getUsername() == null || request.getPassword() == null) {
                logger.warn("Invalid doctor authentication data provided");
                return ResponseEntity.badRequest().build();
            }
            
            Doctor doctor = doctorRepo.findByUsernameAndPassword(request.getUsername(), request.getPassword());
            if (doctor == null) {
                logger.warn("Doctor authentication failed for username: {}", request.getUsername());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            if (!doctor.isActive()) {
                logger.warn("Inactive doctor attempted login: {}", request.getUsername());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            doctor.setPassword(null); // Remove password from response
            logger.info("Doctor authenticated successfully: {}", request.getUsername());
            return ResponseEntity.ok(doctor);
        } catch (Exception e) {
            logger.error("Error authenticating doctor: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    

    @GetMapping("/patient_records") public String patientRecords() { return "patient_records"; }
    @GetMapping("/admin_reports") public String adminReports() { return "admin_reports"; }
    @GetMapping("/demo") public String demoPatients() { return "demo_patients"; }
    @GetMapping("/about") public String about() { return "about"; }
    @GetMapping("/services") public String services() { return "services"; }
    @GetMapping("/contact") public String contact() { return "contact"; }
    @GetMapping("/") public String home() { return "index"; }
    @GetMapping("/doctor_dashboard") public String doctorDashboard() { return "doctor_dashboard"; }
    @GetMapping("/doctor_patients") public String doctorPatients() { return "doctor_patients"; }
    @GetMapping("/doctor_appointments") public String doctorAppointments() { return "doctor_appointments"; }
    @GetMapping("/doctor_prescriptions") public String doctorPrescriptions() { return "doctor_prescriptions"; }
    @GetMapping("/doctor_records") public String doctorRecords() { return "doctor_records"; }
    @GetMapping("/doctor_schedule") public String doctorSchedule() { return "doctor_schedule"; }
    @GetMapping("/patient_register") public String patientRegister() { return "patient_register"; }
    @GetMapping("/patient_schedule") public String patientSchedule() { return "patient_schedule"; }
    
    @GetMapping("/api/patient-schedule/{email}") @ResponseBody
    public ResponseEntity<List<PatientSchedule>> getPatientSchedule(@PathVariable String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                logger.warn("Invalid email provided for patient schedule");
                return ResponseEntity.badRequest().build();
            }
            
            List<PatientSchedule> schedules = patientScheduleRepo.findByPatientEmailOrderByAppointmentDateTimeAsc(email);
            logger.info("Retrieved {} patient schedules for email: {}", schedules.size(), email);
            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            logger.error("Error retrieving patient schedule for email {}: {}", email, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/api/patient-schedule/all") @ResponseBody
    public ResponseEntity<List<PatientSchedule>> getAllPatientSchedules() {
        try {
            List<PatientSchedule> schedules = patientScheduleRepo.findAll();
            logger.info("Retrieved {} patient schedules", schedules.size());
            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            logger.error("Error retrieving all patient schedules: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/api/patient-schedule") @ResponseBody
    public ResponseEntity<PatientSchedule> addPatientSchedule(@RequestBody PatientSchedule schedule) {
        try {
            if (schedule == null || schedule.getPatientEmail() == null || schedule.getAppointmentDateTime() == null) {
                logger.warn("Invalid patient schedule data provided");
                return ResponseEntity.badRequest().build();
            }
            
            PatientSchedule savedSchedule = patientScheduleRepo.save(schedule);
            logger.info("Patient schedule added successfully for: {}", schedule.getPatientEmail());
            return ResponseEntity.ok(savedSchedule);
        } catch (Exception e) {
            logger.error("Error adding patient schedule: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/api/patient-schedule/{id}") @ResponseBody
    public ResponseEntity<PatientSchedule> updatePatientSchedule(@PathVariable Long id, @RequestBody PatientSchedule schedule) {
        try {
            if (id == null || id <= 0 || schedule == null) {
                logger.warn("Invalid patient schedule update data provided");
                return ResponseEntity.badRequest().build();
            }
            
            if (!patientScheduleRepo.existsById(id)) {
                logger.warn("Patient schedule not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            
            schedule.setId(id);
            PatientSchedule updatedSchedule = patientScheduleRepo.save(schedule);
            logger.info("Patient schedule updated successfully: {}", id);
            return ResponseEntity.ok(updatedSchedule);
        } catch (Exception e) {
            logger.error("Error updating patient schedule with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/api/patient-schedule/{id}") @ResponseBody
    public ResponseEntity<Void> deletePatientSchedule(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                logger.warn("Invalid patient schedule ID provided: {}", id);
                return ResponseEntity.badRequest().build();
            }
            
            if (!patientScheduleRepo.existsById(id)) {
                logger.warn("Patient schedule not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            
            patientScheduleRepo.deleteById(id);
            logger.info("Patient schedule deleted successfully: {}", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting patient schedule with ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}