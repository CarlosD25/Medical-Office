package com.unimag.consultoriomedico.controller;

import com.unimag.consultoriomedico.dto.ConsultRoomDTO;
import com.unimag.consultoriomedico.service.ConsultRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class ConsultRoomController {
    private final ConsultRoomService consultRoomService;

    @GetMapping
    public ResponseEntity<List<ConsultRoomDTO>> getAllConsultRooms() {
        return ResponseEntity.ok(consultRoomService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultRoomDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(consultRoomService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ConsultRoomDTO> create(@Valid @RequestBody ConsultRoomDTO consultRoomDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consultRoomService.save(consultRoomDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultRoomDTO> update(@PathVariable Long id, @Valid @RequestBody ConsultRoomDTO consultRoomDTO) {
        return ResponseEntity.ok(consultRoomService.update(id, consultRoomDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        consultRoomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
