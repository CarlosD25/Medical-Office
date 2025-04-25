package com.unimag.consultoriomedico.repository;

import com.unimag.consultoriomedico.model.Appointment;
import com.unimag.consultoriomedico.model.ConsultRoom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ConsultRoomRepositoryTest {

    @Autowired
    private ConsultRoomRepository consultRoomRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    private ConsultRoom consultRoom;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        consultRoom = new ConsultRoom();
        consultRoom.setName("Room 101");
        consultRoom.setFloor(1);
        consultRoom = consultRoomRepository.save(consultRoom);

        // Crear una cita desde las 10:00 hasta las 11:00
        appointment = new Appointment();
        appointment.setConsultRoom(consultRoom);
        appointment.setStartTime(LocalDateTime.of(2025, 5, 24, 10, 30));
        appointment.setEndTime(LocalDateTime.of(2025, 5, 24, 11, 30));
        appointmentRepository.save(appointment);

    }

    @AfterEach
    void tearDown() {
        appointmentRepository.deleteAll();
        consultRoomRepository.deleteAll();
    }

    @Test
    void whenAppointmentOverlaps_thenReturnTrue() {
        boolean result = consultRoomRepository.consultRoomHasAppointment(
                consultRoom.getId(),
                LocalDateTime.of(2025, 5, 24, 10, 30),
                LocalDateTime.of(2025, 5, 24, 11, 30)
        );
        assertTrue(result);
    }

    @Test
    void whenNoAppointmentOverlap_thenReturnFalse() {
        boolean result = consultRoomRepository.consultRoomHasAppointment(
                consultRoom.getId(),
                LocalDateTime.of(2025, 6, 24, 11, 0),
                LocalDateTime.of(2025, 6, 24, 12, 0)
        );
        assertFalse(result);
    }
}