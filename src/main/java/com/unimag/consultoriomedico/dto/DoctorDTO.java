package com.unimag.consultoriomedico.dto;

import com.unimag.consultoriomedico.model.Appointment;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDTO {

    private Long id;

    @NotBlank
    private String fullName;

    @Email
    private String email;

    private String specialty;

    @Future
    private LocalDateTime avaliableFrom;

    @Future
    private LocalDateTime avaliableTo;


}
