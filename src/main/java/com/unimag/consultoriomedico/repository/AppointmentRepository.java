package com.unimag.consultoriomedico.repository;

import com.unimag.consultoriomedico.model.Appointment;
import jakarta.validation.constraints.Future;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId AND a.startTime < :end AND a.endTime > :start")
    List<Appointment> findByDoctorIdAndStartAndEndTimeBetween(
            @Param("doctorId") Long doctorId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("SELECT a FROM Appointment a WHERE a.consultRoom.id = :consultRoomId AND a.startTime < :end AND a.endTime > :start")
    List<Appointment> findByConsultRoomIdAndStartAndEndTimeBetween(
            @Param("consultRoomId") Long consultRoomId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    List<Appointment> findByPatientId(Long patientId);


}
