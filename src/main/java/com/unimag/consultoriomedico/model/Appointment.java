package com.unimag.consultoriomedico.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patientId",referencedColumnName = "id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name= "doctorId", referencedColumnName = "id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name="consultRoomId",referencedColumnName = "id")
    private ConsultRoom consultRoom;

    @Future
    private LocalDateTime startTime;

    @Future
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private Status status;
}
