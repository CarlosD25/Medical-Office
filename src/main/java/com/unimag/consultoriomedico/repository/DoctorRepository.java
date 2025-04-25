package com.unimag.consultoriomedico.repository;

import com.unimag.consultoriomedico.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecialtyIgnoreCase(String specialty);


    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM Appointment a WHERE a.doctor.id = :doctorId " +
            "AND a.startTime < :endTime AND a.endTime > :startTime")
    boolean hasAppointmentsInTimeRange(@Param("doctorId") Long doctorId,
                                       @Param("startTime") LocalDateTime startTime,
                                       @Param("endTime") LocalDateTime endTime);

    @Query("SELECT CASE WHEN :startTime >= a.avaliableFrom AND :endTime <= a.avaliableTo THEN true ELSE false END " +
            "FROM Doctor a " +
            "WHERE a.id = :doctorId")
    boolean isWithinDoctorSchedule(@Param("doctorId") Long doctorId,
                                   @Param("startTime") LocalDateTime startTime,
                                   @Param("endTime") LocalDateTime endTime);


}
