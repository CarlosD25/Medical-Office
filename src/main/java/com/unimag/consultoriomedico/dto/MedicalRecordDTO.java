package com.unimag.consultoriomedico.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecordDTO {

    private Long id;

    @NotNull
    private Long patientId;

    @NotNull
    private Long appointmentId;

    @NotBlank
    private String diagnosis;

    private String notes;

    private LocalTime createdAt;
}
