package com.unimag.consultoriomedico.dto;

import com.unimag.consultoriomedico.model.Appointment;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDTO {

    private Long id;

    @NotBlank
    private String fullName;

    @Email
    @NotBlank
    private String email;

    private String phone;

    private Set<AppointmentDTO> appointmentsDTO;
}
