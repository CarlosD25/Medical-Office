package com.unimag.consultoriomedico.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patientId",referencedColumnName = "id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name= "appointmentId",referencedColumnName = "id")
    private Appointment appointment;

    @NotBlank
    private String diagnosis;

    private String notes;

    @Future
    private LocalTime createdAt;
}
