package com.unimag.consultoriomedico.repository;

import com.unimag.consultoriomedico.model.ConsultRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultRoomRepository extends JpaRepository<ConsultRoom, Long> {
    boolean consultRoomHasAppointment(Long roomId, LocalDateTime startTime, LocalDateTime endTime);
}
