package com.unimag.consultoriomedico.repository;

import com.unimag.consultoriomedico.model.Appointment;
import com.unimag.consultoriomedico.model.Doctor;
import com.unimag.consultoriomedico.model.Patient;
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
    @Autowired
    private PatientRepository patientRepository;
    private Patient patient1;
    private Doctor doctor;
    private Appointment appointment;
    private HashSet<Appointment> appointments;

    @BeforeEach
    void setUp() {
       /* patient1 = Patient.builder()
                .fullName("Carlos D")
                .email("carlos@gmail.com")
                .build();
        patientRepository.save(patient1); // Hibernate asigna un ID automáticamente*/

        // Crear y guardar la cita
        appointment = Appointment.builder()
                .startTime(LocalDateTime.now().plusHours(1))
                .endTime(LocalDateTime.now().plusHours(2))
                .status(Status.SCHEDULED)
                .patient(patient1) // Relacionar el paciente
                .build();
        appointment = appointmentRepository.save(appointment); // Guardar cita con relación al paciente

        // Crear y guardar el doctor
        doctor = Doctor.builder()
                .fullName("Dr. John Doe")
                .email("john.doe@example.com")
                .avaliableFrom(LocalDateTime.of(2025, 4, 29, 10, 0))
                .avaliableTo(LocalDateTime.of(2025, 4, 29, 14, 0))
                .specialty("Cardiology")
                .build();
        doctor = doctorRepository.save(doctor); // Guardar doctor

        // Relacionar la cita con el doctor
        appointment.setDoctor(doctor);
        appointmentRepository.save(appointment); // Guardar la cita ya relacionada con el doctor

        // Relacionar el doctor con sus citas
        Set<Appointment> appointments = new HashSet<>();
        appointments.add(appointment);
        doctor.setAppointments(appointments);
    }

    @AfterEach
    void tearDown() {

        appointmentRepository.deleteAll();
        doctorRepository.deleteAll();
        //patientRepository.deleteAll();

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
        LocalDateTime startTime = LocalDateTime.of(2025, 4, 29, 10, 30);
        LocalDateTime endTime = LocalDateTime.of(2025, 4, 29, 12, 30);
        boolean hasAppointmentsInTimeRange = doctorRepository.hasAppointmentsInTimeRange(doctor.getId(),startTime,endTime);
        assertFalse(hasAppointmentsInTimeRange);
    }

    @Test
    void isWithinDoctorSchedule() {
        LocalDateTime startTime = LocalDateTime.of(2025, 4, 29, 10, 30);
        LocalDateTime endTime = LocalDateTime.of(2025, 4, 29, 13, 30);
        boolean isDoctorSchedule = doctorRepository.isWithinDoctorSchedule(doctor.getId(),startTime,endTime);
        assertTrue(isDoctorSchedule);
    }

    @Test
    void isNotWithinDoctorSchedule() {
        LocalDateTime startTime = LocalDateTime.of(2025, 4, 29, 18, 30);
        LocalDateTime endTime = LocalDateTime.of(2025, 4, 29, 20, 30);
        boolean isNotDoctorSchedule = doctorRepository.isWithinDoctorSchedule(doctor.getId(),startTime,endTime);
        assertFalse(isNotDoctorSchedule);
    }
}