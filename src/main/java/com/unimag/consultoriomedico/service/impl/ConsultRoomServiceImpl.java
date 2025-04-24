package com.unimag.consultoriomedico.service.impl;

import com.unimag.consultoriomedico.dto.ConsultRoomDTO;
import com.unimag.consultoriomedico.exception.ResourceNotFoundException;
import com.unimag.consultoriomedico.mapper.ConsultRoomMapper;
import com.unimag.consultoriomedico.model.ConsultRoom;
import com.unimag.consultoriomedico.repository.ConsultRoomRepository;
import com.unimag.consultoriomedico.service.ConsultRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultRoomServiceImpl implements ConsultRoomService {

    private final ConsultRoomRepository consultRoomRepository;
    private final ConsultRoomMapper consultRoomMapper;

    @Override
    public List<ConsultRoomDTO> findAll() {
        return consultRoomRepository.findAll().stream().map(consultRoomMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ConsultRoomDTO findById(Long id) {
        return consultRoomMapper.toDTO(consultRoomRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("ConsultRoomDTO not found "+id)));
    }

    @Override
    public ConsultRoomDTO save(ConsultRoomDTO roomDto) {
        return consultRoomMapper.toDTO(consultRoomRepository.save(consultRoomMapper.toEntity(roomDto)));
    }

    @Override
    public ConsultRoomDTO update(Long id, ConsultRoomDTO roomDto) {
        if(!consultRoomRepository.existsById(id)) {
            throw new ResourceNotFoundException("ConsultRoomDTO not found "+id);
        }
        ConsultRoom consultRoom = consultRoomMapper.toEntity(roomDto);
        consultRoom.setName(roomDto.getName());
        consultRoom.setFloor(roomDto.getFloor());
        consultRoom.setDescription(roomDto.getDescription());
        return consultRoomMapper.toDTO(consultRoomRepository.save(consultRoom));
    }

    @Override
    public void delete(Long id) {
        if(!consultRoomRepository.existsById(id)) {
            throw new ResourceNotFoundException("ConsultRoomDTO not found "+id);
        }
        consultRoomRepository.deleteById(id);
    }
}
