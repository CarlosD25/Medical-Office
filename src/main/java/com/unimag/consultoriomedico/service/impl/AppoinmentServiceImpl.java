package com.unimag.consultoriomedico.service.impl;

import com.unimag.consultoriomedico.dto.AppointmentDTO;
import com.unimag.consultoriomedico.exception.ResourceNotFoundException;
import com.unimag.consultoriomedico.exception.TimeConflictException;
import com.unimag.consultoriomedico.mapper.AppointmentMapper;
import com.unimag.consultoriomedico.model.Appointment;
import com.unimag.consultoriomedico.model.Status;
import com.unimag.consultoriomedico.repository.AppointmentRepository;
import com.unimag.consultoriomedico.service.AppoinmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppoinmentServiceImpl implements AppoinmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public List<AppointmentDTO> findAll() {
        return appointmentRepository.findAll().stream().map(appointmentMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO findById(Long id) {
        if(!appointmentRepository.existsById(id)){
            throw new ResourceNotFoundException("Appointment not found "+id);
        }
        return appointmentMapper.toDTO(appointmentRepository.findById(id).get());
    }

    @Override
    public AppointmentDTO create(AppointmentDTO appointmentDto) {
        return appointmentMapper.toDTO(appointmentRepository.save(appointmentMapper.toEntity(appointmentDto)));
    }

    @Override
    public AppointmentDTO update(Long id, AppointmentDTO appointmentDto) {
        if(!appointmentRepository.existsById(id)){
            throw new ResourceNotFoundException("Appointment not found "+id);
        }
        Appointment appointment = appointmentRepository.findById(id).get();
        appointment.setStartTime(appointmentDto.getStartTime());
        appointment.setEndTime(appointmentDto.getEndTime());
        appointment.setStatus(appointmentDto.getStatus());
        return appointmentMapper.toDTO(appointmentRepository.save(appointment));
    }

    @Override
    public void cancel(Long id) {
        if(!appointmentRepository.existsById(id)){
            throw new ResourceNotFoundException("Appointment not found "+id);
        }
        Appointment appointment = appointmentRepository.findById(id).get();
        appointment.setStatus(Status.CANCELLED);
        appointmentRepository.save(appointment);
    }

    @Override
    public List<AppointmentDTO> findByDoctorAndDate(Long doctorId, LocalDate date) {
        return appointmentRepository.findByDoctorIdAndDate(doctorId,date).stream().map(appointmentMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public boolean isDoctorAvailable(Long doctorId, LocalDateTime startTime, LocalDateTime endTime) {
        return false;
    }

    @Override
    public boolean isConsultRoomAvailable(Long roomId, LocalDateTime startTime, LocalDateTime endTime) {
        return false;
    }

    @Override
    public boolean isWithinDoctorSchedule(Long doctorId, LocalDateTime startTime, LocalDateTime endTime) {
        return false;
    }

    @Override
    public boolean isInThePast(LocalDateTime startTime) {
        return startTime.isBefore(LocalDateTime.now());
    }
}
