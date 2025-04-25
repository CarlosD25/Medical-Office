package com.unimag.consultoriomedico.repository;

import com.unimag.consultoriomedico.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private ConsultRoomRepository consultRoomRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;
    private Patient patient;
    private ConsultRoom consultRoom;
    private Doctor doctor;
    private Appointment appointment;
    @BeforeEach
    void setUp() {
        patient = Patient.builder()
                .fullName("John Doe")
                .email("john.doe@example.com")
                .build();
        patientRepository.save(patient);

        consultRoom = ConsultRoom.builder()
                .name("Consultorio A")
                .floor(1)
                .build();
        consultRoomRepository.save(consultRoom);

        doctor = Doctor.builder()
                .fullName("Dr. John Doe")
                .email("john.doe@example.com")
                .avaliableFrom(LocalDateTime.of(2025, 4, 25, 10, 0))
                .avaliableTo(LocalDateTime.of(2025, 4, 25, 14, 0))
                .specialty("Cardiology")
                .build();
        doctorRepository.save(doctor);

        appointment = Appointment.builder()
                .startTime(LocalDateTime.of(2025, 4, 25, 10, 30))
                .endTime(LocalDateTime.of(2025, 4, 25, 11, 30))
                .doctor(doctor)
                .patient(patient)
                .consultRoom(consultRoom)
                .status(Status.SCHEDULED)
                .build();
        appointmentRepository.save(appointment);
    }

    @AfterEach
    void tearDown() {
        appointmentRepository.deleteAll();
        consultRoomRepository.deleteAll();
        doctorRepository.deleteAll();
        patientRepository.deleteAll();
    }

    @Test
    void findByDoctorIdAndStartTimeBetween() {
        LocalDateTime start = LocalDateTime.of(2025, 4, 25, 10, 0);
        LocalDateTime end = LocalDateTime.of(2025, 4, 25, 12, 0);

        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndStartAndEndTimeBetween(doctor.getId(), start, end);

        assertNotNull(appointments);
        assertEquals(1, appointments.size());
        assertEquals(appointment, appointments.get(0));
    }

    @Test
    void findByConsultRoomIdAndStartTimeBetween() {
        LocalDateTime start = LocalDateTime.of(2025, 4, 25, 10, 0);
        LocalDateTime end = LocalDateTime.of(2025, 4, 25, 12, 0);

        List<Appointment> appointments = appointmentRepository.findByConsultRoomIdAndStartAndEndTimeBetween(consultRoom.getId(), start, end);

        assertNotNull(appointments);
        assertEquals(1, appointments.size());
        assertEquals(appointment, appointments.get(0));
    }

    @Test
    void findByPatientId() {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patient.getId());
        assertNotNull(appointments);
        assertEquals(1, appointments.size());
        assertEquals(appointment, appointments.get(0));
    }

}