package com.unimag.consultoriomedico.controller;

import com.unimag.consultoriomedico.dto.AppointmentDTO;
import com.unimag.consultoriomedico.repository.AppointmentRepository;
import com.unimag.consultoriomedico.service.AppoinmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppoinmentService appoinmentService;

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        return ResponseEntity.ok(appoinmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(appoinmentService.findById(id));
    }

    @GetMapping(params = {"doctorId", "date"})
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDoctorAndDate(@RequestParam Long doctorId, @RequestParam LocalDate date){
        return ResponseEntity.ok(appoinmentService.findAppointmentsByDoctorAndDate(doctorId,date));
    }

    @PostMapping
    public ResponseEntity<AppointmentDTO> create(@Valid @RequestBody AppointmentDTO appointmentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appoinmentService.create(appointmentDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> update(@PathVariable Long id, @Valid @RequestBody AppointmentDTO appointmentDTO) {
        return ResponseEntity.ok(appoinmentService.update(id, appointmentDTO));
    }

    @DeleteMapping
    public ResponseEntity<AppointmentDTO> delete(@PathVariable Long id) {
        appoinmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
