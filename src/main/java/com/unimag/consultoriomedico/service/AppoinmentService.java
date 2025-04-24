package com.unimag.consultoriomedico.service;

import com.unimag.consultoriomedico.dto.AppointmentDTO;
import com.unimag.consultoriomedico.dto.DoctorDTO;
import com.unimag.consultoriomedico.model.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppoinmentService {
    List<AppointmentDTO> findAll();
    AppointmentDTO findById(Long id);
    AppointmentDTO create(AppointmentDTO appointmentDto);
    AppointmentDTO update(Long id, AppointmentDTO appointmentDto);
    void cancel(Long id);
    void delete(Long id);
    List<AppointmentDTO> findAppointmentsByDoctorAndDate(Long doctorId, LocalDate date);
    boolean isValidAppointmentDuration(LocalDateTime startTime, LocalDateTime endTime);
    boolean isInThePast(LocalDateTime startTime);
    Status checkPointStatus(Long id);
}
