package com.unimag.consultoriomedico.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany(mappedBy = "doctor")
    private Set<Appointment> appointments;

}
