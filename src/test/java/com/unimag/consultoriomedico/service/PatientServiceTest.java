package com.unimag.consultoriomedico.service;

import com.unimag.consultoriomedico.dto.PatientDTO;
import com.unimag.consultoriomedico.mapper.PatientMapper;
import com.unimag.consultoriomedico.model.Patient;
import com.unimag.consultoriomedico.repository.PatientRepository;
import com.unimag.consultoriomedico.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;
    @Mock
    private PatientMapper patientMapper;
    @InjectMocks
    private PatientServiceImpl patientServiceImpl;

    private Patient patient;
    private List<Patient> patients;

    @Test
    void findAll() {
        Patient patient = Patient.builder()
                .id(1L)
                .fullName("Jane Doe")
                .email("jane@example.com")
                .phone("1234567890")
                .build();


        PatientDTO dto = PatientDTO.builder()
                .id(1L)
                .fullName("Jane Doe")
                .email("jane@example.com")
                .phone("1234567890")
                .build();


        when(patientRepository.findAll()).thenReturn(List.of(patient));

        when(patientMapper.toDto(patient)).thenReturn(dto);

        List<PatientDTO> patients = patientServiceImpl.findAll();

        assertEquals(1, patients.size());
        assertEquals("Jane Doe", patients.get(0).getFullName());
    }

    @Test
    void findById() {
        Patient patient = Patient.builder()
                .id(1L)
                .fullName("Jane Doe")
                .email("jane@example.com")
                .phone("1234567890")
                .build();

        PatientDTO dto = PatientDTO.builder()
                .id(1L)
                .fullName("Jane Doe")
                .email("jane@example.com")
                .phone("1234567890")
                .build();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        when(patientMapper.toDto(patient)).thenReturn(dto);


        PatientDTO result = patientServiceImpl.findById(1L);

        assertEquals("Jane Doe", result.getFullName());
    }

    @Test
    void save() {
        Patient patient = Patient.builder()
                .id(1L)
                .fullName("Jane Doe")
                .email("jane@example.com")
                .phone("1234567890")
                .build();

        PatientDTO dto = PatientDTO.builder()
                .id(1L)
                .fullName("Jane Doe")
                .email("jane@example.com")
                .phone("1234567890")
                .build();

        when(patientMapper.toEntity(dto)).thenReturn(patient);

        when(patientRepository.save(patient)).thenReturn(patient);

        when(patientMapper.toDto(patient)).thenReturn(dto);

        PatientDTO result = patientServiceImpl.save(dto);

        assertEquals("Jane Doe", result.getFullName());
    }

    @Test
    void update() {

        Patient patient = Patient.builder()
                .id(1L)
                .fullName("Jane Doe")
                .email("jane@example.com")
                .phone("1234567890")
                .build();

        PatientDTO dto = PatientDTO.builder()
                .id(1L)
                .fullName("Jane Doe Updated")
                .email("jane.updated@example.com")
                .phone("0987654321")
                .build();


        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));


        when(patientRepository.save(any())).thenReturn(patient);


        when(patientMapper.toDto(any())).thenReturn(dto);


        PatientDTO result = patientServiceImpl.update(1L, dto);


        assertEquals("Jane Doe Updated", result.getFullName());
        assertEquals("jane.updated@example.com", result.getEmail());
        assertEquals("0987654321", result.getPhone());
    }

    @Test
    void delete() {
        when(patientRepository.existsById(1L)).thenReturn(true);

        patientServiceImpl.delete(1L);

        verify(patientRepository).deleteById(1L);
    }
}