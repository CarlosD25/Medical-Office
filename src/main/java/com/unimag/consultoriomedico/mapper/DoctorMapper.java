package com.unimag.consultoriomedico.mapper;

import com.unimag.consultoriomedico.dto.DoctorDTO;
import com.unimag.consultoriomedico.model.Doctor;
import org.mapstruct.Mapper;

import javax.swing.*;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorDTO toDto(Doctor doctor);
    Doctor toEntity(DoctorDTO dto);
}
