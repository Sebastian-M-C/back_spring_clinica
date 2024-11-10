package com.segundop.clinicasystem.service;

import com.segundop.clinicasystem.dto.FichaAtencionDTO;
import com.segundop.clinicasystem.entity.FichaAtencion;
import com.segundop.clinicasystem.repository.FichaAtencionRepository;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FichaAtencionService {

    @Autowired
    private FichaAtencionRepository fichaAtencionRepository;


    @Autowired
    private MedicoService medicoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private EspecialidadService especialidadService;
    @Autowired
    private HorarioService horarioService;



    public FichaAtencion createFichaAtencion(FichaAtencionDTO fichaAtencionDTO) {
        FichaAtencion ficha = new FichaAtencion();
        ficha.setFechaAtencion(LocalDate.parse(fichaAtencionDTO.getFechaAtencion()) );
        ficha.setMedico(medicoService.findById(fichaAtencionDTO.getMedicoId()).orElseThrow());
        ficha.setPaciente(pacienteService.findById(fichaAtencionDTO.getPacienteId()).orElseThrow());
        ficha.setEspecialidad(especialidadService.findById(fichaAtencionDTO.getEspecialidadId()).orElseThrow());
        ficha.setHorario(horarioService.findById(fichaAtencionDTO.getHorarioId()).orElseThrow());
        return fichaAtencionRepository.save(ficha);
    }

    public FichaAtencionDTO getFichaAtencionById(Long id) {
        FichaAtencion ficha = fichaAtencionRepository.findById(id).orElseThrow();
        return convertToDTO(ficha);
    }

    public FichaAtencionDTO convertToDTO(FichaAtencion ficha) {
        FichaAtencionDTO dto = new FichaAtencionDTO();
        dto.setId(ficha.getId());
        dto.setFechaAtencion(ficha.getFechaAtencion().toString());
        dto.setMedicoId(ficha.getMedico().getId());
        dto.setPacienteId(ficha.getPaciente().getId());
        dto.setEspecialidadId(ficha.getEspecialidad().getId());
        dto.setHorarioId(ficha.getHorario().getId());
        return dto;
    }

    public FichaAtencion save(FichaAtencion fichaAtencion) {
        return fichaAtencionRepository.save(fichaAtencion);
    }

    public Optional<FichaAtencion> findById(Long id) {
        return fichaAtencionRepository.findById(id);
    }

    public List<FichaAtencion> findAll() {
        return fichaAtencionRepository.findAll();
    }

    public void deleteById(Long id) {
        fichaAtencionRepository.deleteById(id);
    }

    public List<FichaAtencion> findAllForTriaje() {
        return fichaAtencionRepository.findAll();
    }
}
