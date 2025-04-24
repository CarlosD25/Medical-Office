package com.unimag.consultoriomedico.mapper;

import com.unimag.consultoriomedico.dto.ConsultRoomDTO;
import com.unimag.consultoriomedico.model.ConsultRoom;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsultRoomMapper {


    ConsultRoomDTO toDTO(ConsultRoom consultRoom);

    ConsultRoom toEntity(ConsultRoomDTO consultRoomDTO);
}
