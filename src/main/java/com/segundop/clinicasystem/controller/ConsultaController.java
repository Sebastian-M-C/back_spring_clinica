package com.segundop.clinicasystem.controller;

import com.segundop.clinicasystem.dto.ConsultaDTO;
import com.segundop.clinicasystem.entity.Consulta;
import com.segundop.clinicasystem.entity.HistoriaClinica;
import com.segundop.clinicasystem.entity.Medico;
import com.segundop.clinicasystem.service.ConsultaService;
import com.segundop.clinicasystem.service.HistoriaClinicaService;
import com.segundop.clinicasystem.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private HistoriaClinicaService historiaClinicaService;

    @Autowired
    private MedicoService medicoService;

    // Método para convertir de Consulta a ConsultaDTO
    private ConsultaDTO convertToDTO(Consulta consulta) {
        ConsultaDTO consultaDTO = new ConsultaDTO();
        consultaDTO.setId(consulta.getId());
        consultaDTO.setFechaConsulta(consulta.getFechaConsulta().toString());
        consultaDTO.setNotas(consulta.getNotas());
        consultaDTO.setHistoriaClinicaId(consulta.getHistoriaClinica().getId());
        consultaDTO.setMedicoId(consulta.getMedico().getId());
        consultaDTO.setMedicoNombre(consulta.getMedico().getNombreCompleto());
        return consultaDTO;
    }

    // Método para convertir de ConsultaDTO a Consulta
    private Consulta convertToEntity(ConsultaDTO consultaDTO) {
        Consulta consulta = new Consulta();
        consulta.setFechaConsulta(java.time.LocalDate.parse(consultaDTO.getFechaConsulta()));
        consulta.setNotas(consultaDTO.getNotas());

        // Asignar historia clínica
        Optional<HistoriaClinica> historiaClinica = historiaClinicaService.findById(consultaDTO.getHistoriaClinicaId());
        historiaClinica.ifPresent(consulta::setHistoriaClinica);

        // Asignar médico
        Optional<Medico> medico = medicoService.findById(consultaDTO.getMedicoId());
        medico.ifPresent(consulta::setMedico);

        return consulta;
    }

    // Obtener todas las consultas
    @GetMapping
    public ResponseEntity<List<ConsultaDTO>> getAllConsultas() {
        List<Consulta> consultas = consultaService.findAll();
        List<ConsultaDTO> consultaDTOS = consultas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(consultaDTOS);
    }

    // Obtener una consulta por ID
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> getConsultaById(@PathVariable Long id) {
        Optional<Consulta> consulta = consultaService.findById(id);
        return consulta.map(value -> ResponseEntity.ok(convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva consulta
    @PostMapping
    public ResponseEntity<ConsultaDTO> createConsulta(@RequestBody ConsultaDTO consultaDTO) {
        Consulta consulta = convertToEntity(consultaDTO);
        Consulta nuevaConsulta = consultaService.save(consulta);
        ConsultaDTO nuevaConsultaDTO = convertToDTO(nuevaConsulta);
        return new ResponseEntity<>(nuevaConsultaDTO, HttpStatus.CREATED);
    }

    // Actualizar una consulta existente
    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDTO> updateConsulta(@PathVariable Long id, @RequestBody ConsultaDTO consultaDTO) {
        Optional<Consulta> consultaExistente = consultaService.findById(id);
        if (consultaExistente.isPresent()) {
            Consulta consulta = convertToEntity(consultaDTO);
            consulta.setId(id); // Establece el ID de la consulta a actualizar
            Consulta consultaActualizada = consultaService.save(consulta);
            ConsultaDTO consultaActualizadaDTO = convertToDTO(consultaActualizada);
            return ResponseEntity.ok(consultaActualizadaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una consulta por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsulta(@PathVariable Long id) {
        Optional<Consulta> consulta = consultaService.findById(id);
        if (consulta.isPresent()) {
            consultaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
