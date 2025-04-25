package com.unimag.consultoriomedico.repository;

import com.unimag.consultoriomedico.model.ConsultRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ConsultRoomRepository extends JpaRepository<ConsultRoom, Long> {

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM Appointment a WHERE a.consultRoom.id = :roomId " +
            "AND a.startTime < :endTime AND a.endTime > :startTime")
    boolean consultRoomHasAppointment(@Param("roomId") Long roomId,
                                      @Param("startTime") LocalDateTime startTime,
                                      @Param("endTime") LocalDateTime endTime);
}
