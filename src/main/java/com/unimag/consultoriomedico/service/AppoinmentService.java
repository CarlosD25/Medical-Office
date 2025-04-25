package com.unimag.consultoriomedico.service;

import com.unimag.consultoriomedico.dto.AppointmentDTO;
import com.unimag.consultoriomedico.dto.DoctorDTO;
import com.unimag.consultoriomedico.model.Appointment;
import com.unimag.consultoriomedico.model.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    List<AppointmentDTO> findAppointmentsByDoctorIdAndStartTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end);

    boolean isValidAppointmentDuration(LocalDateTime start, LocalDateTime end);

    boolean isInThePast(LocalDateTime startTime);

    Status checkPointStatus(Long id);
}
