package com.hospital;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPatientEmail(String patientEmail);
}