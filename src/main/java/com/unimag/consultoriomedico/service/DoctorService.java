package com.unimag.consultoriomedico.service;

import com.unimag.consultoriomedico.dto.AppointmentDTO;
import com.unimag.consultoriomedico.dto.DoctorDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DoctorService {
    List<DoctorDTO> findAll();

    DoctorDTO findById(Long id);

    DoctorDTO save(DoctorDTO doctorDto);

    DoctorDTO update(Long id, DoctorDTO doctorDto);

    void delete(Long id);

    List<DoctorDTO> findBySpecialty(String specialty);

    boolean hasAppointmentsInTimeRange(Long doctorId, LocalDateTime startTime, LocalDateTime endTime);

    boolean isWithinSchedule(Long doctorId, LocalDateTime startTime, LocalDateTime endTime);
}
