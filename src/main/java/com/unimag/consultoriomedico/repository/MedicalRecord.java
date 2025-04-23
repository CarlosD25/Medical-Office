package com.unimag.consultoriomedico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalRecord extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPatientId(Long patientId);
    Optional<MedicalRecord> findByAppointmentId(Long appointmentId);
}
