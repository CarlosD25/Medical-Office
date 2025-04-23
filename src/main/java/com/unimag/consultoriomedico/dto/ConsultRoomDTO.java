package com.unimag.consultoriomedico.dto;

import com.unimag.consultoriomedico.model.Appointment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultRoomDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Integer floor;

    private String description;

    private Set<AppointmentDTO> appointmentsDTO;
}
