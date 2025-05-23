package com.unimag.consultoriomedico.service;

import com.unimag.consultoriomedico.dto.ConsultRoomDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultRoomService {
    List<ConsultRoomDTO> findAll();

    ConsultRoomDTO findById(Long id);

    ConsultRoomDTO save(ConsultRoomDTO roomDto);

    ConsultRoomDTO update(Long id, ConsultRoomDTO roomDto);

    void delete(Long id);

    boolean hasAppointmentBetween(Long roomId, LocalDateTime startTime, LocalDateTime endTime);
}
