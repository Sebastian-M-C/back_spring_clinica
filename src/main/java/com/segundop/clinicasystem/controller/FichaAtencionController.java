package com.segundop.clinicasystem.controller;

import com.segundop.clinicasystem.dto.FichaAtencionDTO;
import com.segundop.clinicasystem.entity.*;
import com.segundop.clinicasystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/fichas")
public class FichaAtencionController {

    @Autowired
    private FichaAtencionService fichaAtencionService;

    @Autowired
    private EspecialidadService especialidadService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private HorarioService horarioService;

    // Método para convertir de FichaAtencion a FichaAtencionDTO
    private FichaAtencionDTO convertToDTO(FichaAtencion fichaAtencion) {
        FichaAtencionDTO fichaDTO = new FichaAtencionDTO();
        fichaDTO.setId(fichaAtencion.getId());
        fichaDTO.setFechaAtencion(fichaAtencion.getFechaAtencion().toString());
        fichaDTO.setPacienteId(fichaAtencion.getPaciente().getId());
        fichaDTO.setPacienteNombre(fichaAtencion.getPaciente().getNombreCompleto());
        fichaDTO.setMedicoId(fichaAtencion.getMedico().getId());
        fichaDTO.setMedicoNombre(fichaAtencion.getMedico().getNombreCompleto());
        fichaDTO.setHorarioId(fichaAtencion.getHorario().getId());
        fichaDTO.setEspecialidadId(fichaAtencion.getEspecialidad().getId());

        // Añadir la descripción del horario
        Horario horario = fichaAtencion.getHorario();
        fichaDTO.setHorarioDescripcion("Fecha: " + horario.getFecha() + ", Hora Fin: " + horario.getHoraFin());

        return fichaDTO;
    }


    // Método para convertir de FichaAtencionDTO a FichaAtencion
    private FichaAtencion convertToEntity(FichaAtencionDTO fichaDTO) {
        FichaAtencion fichaAtencion = new FichaAtencion();
        fichaAtencion.setFechaAtencion(LocalDate.parse(fichaDTO.getFechaAtencion()));

        // Asignar paciente
        Optional<Paciente> paciente = pacienteService.findById(fichaDTO.getPacienteId());
        paciente.ifPresent(fichaAtencion::setPaciente);

        // Asignar médico
        Optional<Medico> medico = medicoService.findById(fichaDTO.getMedicoId());
        medico.ifPresent(fichaAtencion::setMedico);

        // Asignar horario
        Optional<Horario> horario = horarioService.findById(fichaDTO.getHorarioId());
        horario.ifPresent(fichaAtencion::setHorario);

        // Asignar especialidad
        Optional<Especialidad> especialidad = especialidadService.findById(fichaDTO.getEspecialidadId());
        especialidad.ifPresent(fichaAtencion::setEspecialidad);

        return fichaAtencion;
    }



    // Obtener todas las fichas de atención
    @GetMapping
    public ResponseEntity<List<FichaAtencionDTO>> getAllFichas() {
        List<FichaAtencion> fichas = fichaAtencionService.findAll();
        List<FichaAtencionDTO> fichaDTOS = fichas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(fichaDTOS);
    }

    // Obtener una ficha por ID
    @GetMapping("/{id}")
    public ResponseEntity<FichaAtencionDTO> getFichaAtencion(@PathVariable Long id) {
        FichaAtencionDTO fichaAtencionDTO = fichaAtencionService.getFichaAtencionById(id);
        return ResponseEntity.ok(fichaAtencionDTO);
    }

    // Crear una nueva ficha de atención
    @PostMapping
    public ResponseEntity<FichaAtencionDTO> createFichaAtencion(@RequestBody FichaAtencionDTO fichaAtencionDTO) {
        if (fichaAtencionDTO.getPacienteId() == null || fichaAtencionDTO.getMedicoId() == null || fichaAtencionDTO.getHorarioId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        FichaAtencion ficha = fichaAtencionService.createFichaAtencion(fichaAtencionDTO);
        FichaAtencionDTO responseDto = fichaAtencionService.convertToDTO(ficha);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // Actualizar una ficha de atención existente
    @PutMapping("/{id}")
    public ResponseEntity<FichaAtencionDTO> updateFicha(@PathVariable Long id, @RequestBody FichaAtencionDTO fichaDTO) {
        Optional<FichaAtencion> fichaExistente = fichaAtencionService.findById(id);
        if (fichaExistente.isPresent()) {
            FichaAtencion fichaAtencion = convertToEntity(fichaDTO);
            fichaAtencion.setId(id); // Establece el ID de la ficha a actualizar
            FichaAtencion fichaActualizada = fichaAtencionService.save(fichaAtencion);
            FichaAtencionDTO fichaActualizadaDTO = convertToDTO(fichaActualizada);
            return ResponseEntity.ok(fichaActualizadaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una ficha de atención por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFicha(@PathVariable Long id) {
        Optional<FichaAtencion> ficha = fichaAtencionService.findById(id);
        if (ficha.isPresent()) {
            fichaAtencionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
