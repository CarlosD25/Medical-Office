package com.unimag.consultoriomedico.repository;

import com.unimag.consultoriomedico.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByConsultRoomIdAndStartTimeBetween(Long roomId, LocalDateTime start, LocalDateTime end);

    List<Appointment> findByDoctorIdAndStartTimeBetweenOrderByStartTime(Long doctorId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    boolean existsByDoctorIdAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(Long doctorId, LocalDateTime endTime, LocalDateTime startTime);

    boolean existsByConsultRoomIdAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(Long roomId, LocalDateTime endTime, LocalDateTime startTime);
}
