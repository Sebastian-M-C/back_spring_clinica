package com.segundop.clinicasystem.mapper;

import com.segundop.clinicasystem.dto.EspecialidadDTO;
import com.segundop.clinicasystem.dto.PacienteDTO;
import com.segundop.clinicasystem.entity.Especialidad;
import com.segundop.clinicasystem.entity.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PacienteMapper {

    PacienteMapper INSTANCE = Mappers.getMapper(PacienteMapper.class);

    PacienteDTO pacienteToPacienteDTO(Paciente paciente);

    Paciente pacienteDTOToPaciente(PacienteDTO pacienteDTO);
}
