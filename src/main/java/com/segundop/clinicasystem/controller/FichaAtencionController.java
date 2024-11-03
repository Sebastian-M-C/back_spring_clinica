package com.segundop.clinicasystem.controller;

import com.segundop.clinicasystem.dto.FichaAtencionDTO;
import com.segundop.clinicasystem.entity.FichaAtencion;
import com.segundop.clinicasystem.entity.Horario;
import com.segundop.clinicasystem.entity.Medico;
import com.segundop.clinicasystem.entity.Paciente;
import com.segundop.clinicasystem.service.FichaAtencionService;
import com.segundop.clinicasystem.service.HorarioService;
import com.segundop.clinicasystem.service.MedicoService;
import com.segundop.clinicasystem.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/fichas")
public class FichaAtencionController {

    @Autowired
    private FichaAtencionService fichaAtencionService;

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
        fichaDTO.setHorarioDescripcion(fichaAtencion.getHorario().toString()); // Ajusta la descripción según tu implementación

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
    public ResponseEntity<FichaAtencionDTO> getFichaById(@PathVariable Long id) {
        Optional<FichaAtencion> ficha = fichaAtencionService.findById(id);
        return ficha.map(value -> ResponseEntity.ok(convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva ficha de atención
    @PostMapping
    public ResponseEntity<FichaAtencionDTO> createFicha(@RequestBody FichaAtencionDTO fichaDTO) {
        FichaAtencion fichaAtencion = convertToEntity(fichaDTO);
        FichaAtencion nuevaFicha = fichaAtencionService.save(fichaAtencion);
        FichaAtencionDTO nuevaFichaDTO = convertToDTO(nuevaFicha);
        return new ResponseEntity<>(nuevaFichaDTO, HttpStatus.CREATED);
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
