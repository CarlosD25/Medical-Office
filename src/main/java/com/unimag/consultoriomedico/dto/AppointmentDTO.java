package com.unimag.consultoriomedico.dto;

import com.unimag.consultoriomedico.model.Patient;
import com.unimag.consultoriomedico.model.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDTO {

    private Long id;

    @NotNull
    private Long patientId;

    @NotNull
    private Long doctorId;

    @NotNull
    private Long consultationId;

    @Future
    private LocalDateTime startTime;

    @Future
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private Status status;
}
