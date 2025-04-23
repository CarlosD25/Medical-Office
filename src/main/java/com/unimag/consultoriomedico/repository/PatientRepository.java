package com.unimag.consultoriomedico.repository;

import com.unimag.consultoriomedico.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
