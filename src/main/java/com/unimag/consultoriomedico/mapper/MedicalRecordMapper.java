package com.unimag.consultoriomedico.mapper;

import com.unimag.consultoriomedico.dto.MedicalRecordDTO;
import com.unimag.consultoriomedico.model.MedicalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {

    @Mapping(source = "patient.id",target = "patientId")
    @Mapping(source = "appointment.id",target = "appointmentId")
    MedicalRecordDTO toDTO(MedicalRecord medicalRecord);

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "appointment",ignore = true)
    MedicalRecord toEntity(MedicalRecordDTO medicalRecordDTO);
}
