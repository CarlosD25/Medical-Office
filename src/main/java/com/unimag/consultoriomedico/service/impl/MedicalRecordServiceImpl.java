package com.unimag.consultoriomedico.service.impl;

import com.unimag.consultoriomedico.dto.MedicalRecordDTO;
import com.unimag.consultoriomedico.exception.MedicalRecordCreationException;
import com.unimag.consultoriomedico.exception.ResourceNotFoundException;
import com.unimag.consultoriomedico.mapper.MedicalRecordMapper;
import com.unimag.consultoriomedico.model.Appointment;
import com.unimag.consultoriomedico.model.MedicalRecord;
import com.unimag.consultoriomedico.model.Status;
import com.unimag.consultoriomedico.repository.AppointmentRepository;
import com.unimag.consultoriomedico.repository.MedicalRecordRepository;
import com.unimag.consultoriomedico.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordMapper medicalRecordMapper;
    private final AppointmentRepository appointmentRepository;

    @Override
    public List<MedicalRecordDTO> findAll() {
        return medicalRecordRepository.findAll().stream().map(medicalRecordMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public MedicalRecordDTO findById(Long id) {
        return medicalRecordRepository.findById(id).map(medicalRecordMapper::toDTO).orElseThrow(() -> new ResourceNotFoundException("Medical Record not found "+id));
    }

    @Override
    public List<MedicalRecordDTO> findByPatientId(Long patientId) {
        return medicalRecordRepository.findByPatientId(patientId).stream().map(medicalRecordMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public MedicalRecordDTO create(MedicalRecordDTO recordDto) {
        if(!isAppointmentCompleted(recordDto.getAppointmentId())) {
            throw new MedicalRecordCreationException("Appointment not completed");
        }
        MedicalRecord medicalRecord = medicalRecordMapper.toEntity(recordDto);
        return medicalRecordMapper.toDTO(medicalRecordRepository.save(medicalRecord));
    }

    @Override
    public void delete(Long id) {

        if(!medicalRecordRepository.existsById(id)) {
            throw new ResourceNotFoundException("Medical Record not found "+id);
        }
        medicalRecordRepository.deleteById(id);

    }

    @Override
    public boolean isAppointmentCompleted(Long appointmentId) {
        Optional<Appointment> appointment = appointmentRepository.findByAppointmentId(appointmentId);
        if(!appointment.isPresent()) {
            throw new ResourceNotFoundException("Appointment not found");
        }
        return appointment.get().getStatus()== Status.COMPLETED;

    }
}
