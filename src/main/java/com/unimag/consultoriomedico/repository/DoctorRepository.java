package com.unimag.consultoriomedico.repository;

import com.unimag.consultoriomedico.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecialtyIgnoreCase(String specialty);
    boolean hasAppointmentsInTimeRange(Long doctorId, LocalDateTime startTime, LocalDateTime endTime);
    boolean isWithinDoctorSchedule(Long doctorId, LocalDateTime startTime, LocalDateTime endTime);


}
