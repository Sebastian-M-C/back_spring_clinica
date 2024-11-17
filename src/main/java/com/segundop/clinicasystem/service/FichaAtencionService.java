package com.segundop.clinicasystem.service;

import com.segundop.clinicasystem.dto.FichaAtencionDTO;
import com.segundop.clinicasystem.entity.*;
import com.segundop.clinicasystem.repository.FichaAtencionRepository;
import org.hibernate.boot.model.source.spi.AttributePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        // Validar existencia de Paciente
        Paciente paciente = pacienteService.findById(fichaAtencionDTO.getPacienteId())
                .orElseThrow(() -> new IllegalStateException("Paciente no encontrado"));

        // Validar existencia de Médico
        Medico medico = medicoService.findById(fichaAtencionDTO.getMedicoId())
                .orElseThrow(() -> new IllegalStateException("Médico no encontrado"));

        // Validar existencia de Especialidad
        Especialidad especialidad = especialidadService.findById(fichaAtencionDTO.getEspecialidadId())
                .orElseThrow(() -> new IllegalStateException("Especialidad no encontrada"));

        // Validar existencia de Horario
        Horario horario = horarioService.findById(fichaAtencionDTO.getHorarioId())
                .orElseThrow(() -> new IllegalStateException("Horario no encontrado"));

        // Crear la ficha
        FichaAtencion ficha = new FichaAtencion();
        ficha.setFechaAtencion(LocalDate.parse(fichaAtencionDTO.getFechaAtencion()));
        ficha.setPaciente(paciente);
        ficha.setMedico(medico);
        ficha.setEspecialidad(especialidad);
        ficha.setHorario(horario);

        return fichaAtencionRepository.save(ficha);
    }

    public FichaAtencionDTO getFichaAtencionById(Long id) {
        FichaAtencion ficha = fichaAtencionRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Ficha no encontrada"));
        return convertToDTO(ficha);
    }


    public FichaAtencionDTO convertToDTO(FichaAtencion ficha) {
        FichaAtencionDTO dto = new FichaAtencionDTO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // Cambia el formato según tu preferencia
        dto.setId(ficha.getId());
        dto.setFechaAtencion(ficha.getFechaAtencion().toString());
        dto.setPacienteId(ficha.getPaciente().getId());
        dto.setPacienteNombre(ficha.getPaciente().getNombreCompleto());
        dto.setMedicoId(ficha.getMedico().getId());
        dto.setMedicoNombre(ficha.getMedico().getNombreCompleto());
        dto.setHorarioId(ficha.getHorario().getId());
        dto.setHorarioDescripcion(ficha.getHorario().getHoraFin().format(formatter)); // Formatear LocalTime
        dto.setEspecialidadId(ficha.getEspecialidad().getId());
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
        if (!fichaAtencionRepository.existsById(id)) {
            throw new IllegalStateException("La ficha no existe.");
        }
        fichaAtencionRepository.deleteById(id);
    }

    public List<FichaAtencion> findAllForTriaje() {
        return fichaAtencionRepository.findAll();
    }

    public List<FichaAtencionDTO> getAllFichas() {
        return fichaAtencionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
