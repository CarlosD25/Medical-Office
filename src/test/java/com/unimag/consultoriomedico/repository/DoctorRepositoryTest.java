package com.unimag.consultoriomedico.repository;

import com.unimag.consultoriomedico.model.Appointment;
import com.unimag.consultoriomedico.model.Doctor;
import com.unimag.consultoriomedico.model.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    private Doctor doctor;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        appointment= Appointment.builder()
                .startTime(LocalDateTime.of(2025,4,25,11,0))
                .endTime(LocalDateTime.of(2025,4,25,12,0))
                .status(Status.SCHEDULED).build();


        appointmentRepository.save(appointment);

        doctor = Doctor.builder().fullName("Dr. John Doe")
                .email("john.doe@example.com")
                .avaliableFrom(LocalDateTime.of(2025,4,25,10,0))
                .avaliableTo(LocalDateTime.of(2025,4,25,14,0))
                .specialty("Cardiology")
                .build();

        appointment.setDoctor(doctor);
        doctorRepository.save(doctor);
    }

    @AfterEach
    void tearDown() {

        appointmentRepository.deleteAll();
        doctorRepository.deleteAll();

    }

    @Test
    void findBySpecialtyIgnoreCase() {
        List<Doctor> doctors = doctorRepository.findBySpecialtyIgnoreCase("carDiology");
        assertTrue(doctors.size() > 0);
        assertTrue(doctors.get(0).getSpecialty().equalsIgnoreCase("carDiology"));
    }

    @Test
    void hasAppointmentsInTimeRange() {
        LocalDateTime startTime = LocalDateTime.of(2025, 4, 25, 10, 30);
        LocalDateTime endTime = LocalDateTime.of(2025, 4, 25, 12, 30);
        boolean hasAppointmentsInTimeRange = doctorRepository.hasAppointmentsInTimeRange(doctor.getId(),startTime,endTime);
        assertTrue(hasAppointmentsInTimeRange);
    }

    @Test
    void hasNotAppointmentsInTimeRange() {
        LocalDateTime startTime = LocalDateTime.of(2025, 4, 26, 10, 30);
        LocalDateTime endTime = LocalDateTime.of(2025, 4, 26, 12, 30);
        boolean hasAppointmentsInTimeRange = doctorRepository.hasAppointmentsInTimeRange(doctor.getId(),startTime,endTime);
        assertFalse(hasAppointmentsInTimeRange);
    }

    @Test
    void isWithinDoctorSchedule() {
        LocalDateTime startTime = LocalDateTime.of(2025, 4, 25, 10, 30);
        LocalDateTime endTime = LocalDateTime.of(2025, 4, 25, 13, 30);
        boolean isDoctorSchedule = doctorRepository.isWithinDoctorSchedule(doctor.getId(),startTime,endTime);
        assertTrue(isDoctorSchedule);
    }

    @Test
    void isNotWithinDoctorSchedule() {
        LocalDateTime startTime = LocalDateTime.of(2025, 4, 25, 11, 30);
        LocalDateTime endTime = LocalDateTime.of(2025, 4, 25, 16, 30);
        boolean isNotDoctorSchedule = doctorRepository.isWithinDoctorSchedule(doctor.getId(),startTime,endTime);
        assertFalse(isNotDoctorSchedule);
    }
}