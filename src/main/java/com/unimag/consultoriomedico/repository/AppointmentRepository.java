package com.unimag.consultoriomedico.repository;

import com.unimag.consultoriomedico.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorIdAndDate(Long doctorId, LocalDate date);

    Optional<Appointment> findByAppointmentId(Long appointmentId);

    boolean isValidAppointmentDuration(LocalDateTime startTime, LocalDateTime endTime);

}
