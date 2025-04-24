package com.unimag.consultoriomedico.mapper;

import com.unimag.consultoriomedico.dto.PatientDTO;
import com.unimag.consultoriomedico.model.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO toDto(Patient patient);

    Patient toEntity(PatientDTO dto);
}
