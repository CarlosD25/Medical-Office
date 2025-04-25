package com.unimag.consultoriomedico.service;

import com.unimag.consultoriomedico.dto.MedicalRecordDTO;
import com.unimag.consultoriomedico.dto.PatientDTO;

import java.util.List;

public interface PatientService {
    List<PatientDTO> findAll();

    PatientDTO findById(Long id);

    PatientDTO save(PatientDTO patientDto);

    PatientDTO update(Long id, PatientDTO patientDto);

    void delete(Long id);
}
