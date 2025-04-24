package com.unimag.consultoriomedico.service;

import com.unimag.consultoriomedico.dto.AppointmentDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppoinmentService {
    List<AppointmentDTO> findAll();
    AppointmentDTO findById(Long id);
    AppointmentDTO create(AppointmentDTO appointmentDto);
    AppointmentDTO update(Long id, AppointmentDTO appointmentDto);
    void cancel(Long id);

    List<AppointmentDTO> findByDoctorAndDate(Long doctorId, LocalDate date);
    boolean isDoctorAvailable(Long doctorId, LocalDateTime startTime, LocalDateTime endTime);
    boolean isConsultRoomAvailable(Long roomId, LocalDateTime startTime, LocalDateTime endTime);
    boolean isWithinDoctorSchedule(Long doctorId, LocalDateTime startTime, LocalDateTime endTime);
    boolean isInThePast(LocalDateTime startTime);
}
