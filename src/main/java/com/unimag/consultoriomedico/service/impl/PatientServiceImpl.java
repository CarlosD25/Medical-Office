package com.unimag.consultoriomedico.service.impl;

import com.unimag.consultoriomedico.dto.MedicalRecordDTO;
import com.unimag.consultoriomedico.dto.PatientDTO;
import com.unimag.consultoriomedico.exception.ResourceNotFoundException;
import com.unimag.consultoriomedico.mapper.AppointmentMapper;
import com.unimag.consultoriomedico.mapper.PatientMapper;
import com.unimag.consultoriomedico.model.Patient;
import com.unimag.consultoriomedico.repository.PatientRepository;
import com.unimag.consultoriomedico.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final AppointmentMapper appointmentMapper;

    @Override
    public List<PatientDTO> findAll() {
        return patientRepository.findAll().stream().map(patientMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public PatientDTO findById(Long id) {
        return patientRepository.findById(id).map(patientMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + id));
    }

    @Override
    public PatientDTO save(PatientDTO patientDto) {
        Patient patient = patientMapper.toEntity(patientDto);
        return patientMapper.toDto(patientRepository.save(patient));
    }

    @Override
    public PatientDTO update(Long id, PatientDTO patientDto) {
        if(!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found with id " + id);
        }
        Optional<Patient> patientOptional = patientRepository.findById(id);

        Patient patient = patientOptional.get();

        patient.setFullName(patientDto.getFullName());
        patient.setEmail(patientDto.getEmail());
        patient.setPhone(patientDto.getPhone());

        return patientMapper.toDto(patientRepository.save(patient));
    }

    @Override
    public void delete(Long id) {

        if(!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found with id " + id);
        }
        patientRepository.deleteById(id);

    }

}
