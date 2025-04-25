package com.unimag.consultoriomedico.repository;

import com.unimag.consultoriomedico.model.MedicalRecord;
import com.unimag.consultoriomedico.model.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MedicalRecordRepositoryTest {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private PatientRepository patientRepository;
    private Patient patient1;
    private MedicalRecord medicalRecord;

    @BeforeEach
    void setUp() {
        patient1 = Patient.builder().fullName("carlos").email("carlos@gmail.com").build();
        patient1 = patientRepository.save(patient1);
        medicalRecord= MedicalRecord.builder().diagnosis("jaja")
                .notes("notes")
                .createdAt(LocalTime.of(10,40))
                .patient(patient1).build();

        medicalRecordRepository.save(medicalRecord);
    }

    @AfterEach
    void tearDown() {
        medicalRecordRepository.deleteAll();
        patientRepository.deleteAll();
    }

    @Test
    void findByPatientId() {
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findByPatientId(patient1.getId());
        assertTrue(!medicalRecords.isEmpty());
        assertTrue(medicalRecords.size()==1);
    }
}