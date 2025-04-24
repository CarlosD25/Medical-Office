package com.unimag.consultoriomedico.service.impl;

import com.unimag.consultoriomedico.dto.AppointmentDTO;
import com.unimag.consultoriomedico.dto.DoctorDTO;
import com.unimag.consultoriomedico.exception.ResourceNotFoundException;
import com.unimag.consultoriomedico.mapper.DoctorMapper;
import com.unimag.consultoriomedico.model.Doctor;
import com.unimag.consultoriomedico.repository.DoctorRepository;
import com.unimag.consultoriomedico.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ReflectiveScan;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public List<DoctorDTO> findAll() {
        return doctorRepository.findAll().stream().map(doctorMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public DoctorDTO findById(Long id) {
        return doctorMapper.toDto(doctorRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Doctor not found "+id)));
    }

    @Override
    public DoctorDTO save(DoctorDTO doctorDto) {
        Doctor doctor = doctorMapper.toEntity(doctorDto);
        return doctorMapper.toDto(doctorRepository.save(doctor));
    }

    @Override
    public DoctorDTO update(Long id, DoctorDTO doctorDto) {
        if(!doctorRepository.findById(id).isPresent()){
            throw new ResourceNotFoundException("Error: Doctor not found "+id);
        }
        Doctor doctor = doctorMapper.toEntity(doctorDto);
        doctor.setFullName(doctorDto.getFullName());
        doctor.setEmail(doctorDto.getEmail());
        doctor.setSpecialty(doctorDto.getSpecialty());
        doctor.setAvaliableFrom(doctorDto.getAvaliableFrom());
        doctor.setAvaliableTo(doctorDto.getAvaliableTo());
        return doctorMapper.toDto(doctorRepository.save(doctor));
    }

    @Override
    public void delete(Long id) {
        if(!doctorRepository.findById(id).isPresent()){
            throw new ResourceNotFoundException("Error: Doctor not found "+id);
        }
        doctorRepository.deleteById(id);
    }

    @Override
    public List<DoctorDTO> findBySpecialty(String specialty) {
        return doctorRepository.findBySpecialtyIgnoreCase(specialty).stream().map(doctorMapper::toDto).collect(Collectors.toList());
    }

}
