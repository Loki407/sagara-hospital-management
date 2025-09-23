package com.hospital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

@Controller
public class HospitalController {
    
    @Autowired DoctorRepo doctorRepo;
    @Autowired AppointmentRepo appointmentRepo;
    @Autowired MedicalRecordRepo medicalRecordRepo;
    @Autowired PrescriptionRepo prescriptionRepo;
    @Autowired PatientRepo patientRepo;
    
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
    public List<Doctor> getDoctors() { return doctorRepo.findAll(); }
    
    @PostMapping("/api/doctors") @ResponseBody
    public Doctor addDoctor(@RequestBody Doctor doctor) {
        if (doctor.getFirstName() == null || doctor.getLastName() == null) {
            return null;
        }
        // Generate random credentials
        String username = "dr." + doctor.getLastName().toLowerCase() + Math.random() * 1000;
        String password = "doc" + (int)(Math.random() * 9000 + 1000);
        
        doctor.setUsername(username);
        doctor.setPassword(password);
        doctor.setRole("DOCTOR");
        doctor.setActive(true);
        
        Doctor savedDoctor = doctorRepo.save(doctor);
        
        // Return credentials in response for display
        savedDoctor.setPassword(password); // Temporarily include for display
        return savedDoctor;
    }
    
    @DeleteMapping("/api/doctors/{id}") @ResponseBody
    public void deleteDoctor(@PathVariable Long id) {
        try {
            if (doctorRepo.existsById(id)) {
                doctorRepo.deleteById(id);
            }
        } catch (Exception e) {
            // Log error but don't crash
        }
    }
    
    @GetMapping("/api/appointments") @ResponseBody
    public List<Appointment> getAppointments() { return appointmentRepo.findAll(); }
    
    @PostMapping("/api/appointments") @ResponseBody
    public Appointment bookAppointment(@RequestBody Appointment appointment) {
        return appointmentRepo.save(appointment);
    }
    
    @DeleteMapping("/api/appointments/{id}") @ResponseBody
    public void deleteAppointment(@PathVariable Long id) {
        try {
            if (appointmentRepo.existsById(id)) {
                appointmentRepo.deleteById(id);
            }
        } catch (Exception e) {
            // Log error but don't crash
        }
    }
    
    @PutMapping("/api/appointments/{id}") @ResponseBody
    public Appointment updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        if (appointmentRepo.existsById(id)) {
            appointment.setId(id);
            return appointmentRepo.save(appointment);
        }
        return null;
    }
    
    @PutMapping("/api/appointments/{id}/status") @ResponseBody
    public Appointment updateAppointmentStatus(@PathVariable Long id, @RequestBody String status) {
        Appointment appointment = appointmentRepo.findById(id).orElse(null);
        if (appointment != null) {
            appointment.setStatus(status.replace("\"", ""));
            return appointmentRepo.save(appointment);
        }
        return null;
    }
    
    @GetMapping("/api/medical-records/{email}") @ResponseBody
    public List<MedicalRecord> getMedicalRecords(@PathVariable String email) {
        return medicalRecordRepo.findByPatientEmail(email);
    }
    
    @PostMapping("/api/medical-records") @ResponseBody
    public MedicalRecord addMedicalRecord(@RequestBody MedicalRecord record) {
        return medicalRecordRepo.save(record);
    }
    
    @GetMapping("/api/prescriptions/{doctorUsername}") @ResponseBody
    public List<Prescription> getDoctorPrescriptions(@PathVariable String doctorUsername) {
        return prescriptionRepo.findByDoctorUsername(doctorUsername);
    }
    
    @PostMapping("/api/prescriptions") @ResponseBody
    public Prescription addPrescription(@RequestBody Prescription prescription) {
        return prescriptionRepo.save(prescription);
    }
    
    @PutMapping("/api/prescriptions/{id}") @ResponseBody
    public Prescription updatePrescription(@PathVariable Long id, @RequestBody Prescription prescription) {
        if (prescriptionRepo.existsById(id)) {
            prescription.setId(id);
            return prescriptionRepo.save(prescription);
        }
        return null;
    }
    
    @DeleteMapping("/api/prescriptions/{id}") @ResponseBody
    public void deletePrescription(@PathVariable Long id) {
        prescriptionRepo.deleteById(id);
    }
    
    @PostMapping("/api/patients/register") @ResponseBody
    public Patient registerPatient(@RequestBody Patient patient) {
        if (patient.getFirstName() == null || patient.getLastName() == null || patient.getEmail() == null) {
            return null;
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
        return savedPatient;
    }
    
    @PostMapping("/api/auth/patient") @ResponseBody
    public Patient authenticatePatient(@RequestBody LoginRequest request) {
        Patient patient = patientRepo.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (patient != null && patient.isActive()) {
            patient.setPassword(null); // Remove password from response
            return patient;
        }
        return null;
    }
    
    @PostMapping("/api/auth/doctor") @ResponseBody
    public Doctor authenticateDoctor(@RequestBody LoginRequest request) {
        Doctor doctor = doctorRepo.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (doctor != null && doctor.isActive()) {
            // Remove password from response for security
            doctor.setPassword(null);
            return doctor;
        }
        return null;
    }
    
    @GetMapping("/medical_records") public String medicalRecords() { return "medical_records"; }
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
    @GetMapping("/patient_profile") public String patientProfile() { return "patient_profile"; }
}