package com.unimag.consultoriomedico.service;

import com.unimag.consultoriomedico.dto.MedicalRecordDTO;
import com.unimag.consultoriomedico.dto.PatientDTO;

import java.util.List;

public interface MedicalRecordService {
    List<MedicalRecordDTO> findAll();
    MedicalRecordDTO findById(Long id);
    List<MedicalRecordDTO> findByPatientId(Long patientId);
    MedicalRecordDTO create(MedicalRecordDTO recordDto);
    void delete(Long id);
    boolean isAppointmentCompleted(Long appointmentId);
    MedicalRecordDTO update(Long id, MedicalRecordDTO medicalRecordDTO);
}
