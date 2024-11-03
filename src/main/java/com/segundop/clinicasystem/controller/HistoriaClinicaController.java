package com.segundop.clinicasystem.controller;

import com.segundop.clinicasystem.dto.HistoriaClinicaDTO;
import com.segundop.clinicasystem.dto.ConsultaDTO;
import com.segundop.clinicasystem.entity.HistoriaClinica;
import com.segundop.clinicasystem.entity.Paciente;
import com.segundop.clinicasystem.entity.Consulta;
import com.segundop.clinicasystem.service.HistoriaClinicaService;
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
@RequestMapping("/api/historias-clinicas")
public class HistoriaClinicaController {

    @Autowired
    private HistoriaClinicaService historiaClinicaService;

    @Autowired
    private PacienteService pacienteService;

    // Método para convertir de HistoriaClinica a HistoriaClinicaDTO
    private HistoriaClinicaDTO convertToDTO(HistoriaClinica historiaClinica) {
        HistoriaClinicaDTO historiaDTO = new HistoriaClinicaDTO();
        historiaDTO.setId(historiaClinica.getId());
        historiaDTO.setFechaCreacion(historiaClinica.getFechaCreacion().toString());
        historiaDTO.setPacienteId(historiaClinica.getPaciente().getId());
        historiaDTO.setPacienteNombre(historiaClinica.getPaciente().getNombreCompleto());

        // Convertir las consultas a ConsultaDTO
        List<ConsultaDTO> consultaDTOS = historiaClinica.getConsultas().stream()
                .map(this::convertConsultaToDTO)
                .collect(Collectors.toList());
        historiaDTO.setConsultas(consultaDTOS);

        return historiaDTO;
    }

    // Método para convertir de HistoriaClinicaDTO a HistoriaClinica
    private HistoriaClinica convertToEntity(HistoriaClinicaDTO historiaDTO) {
        HistoriaClinica historiaClinica = new HistoriaClinica();
        historiaClinica.setFechaCreacion(LocalDate.parse(historiaDTO.getFechaCreacion()));

        // Asignar paciente
        Optional<Paciente> paciente = pacienteService.findById(historiaDTO.getPacienteId());
        paciente.ifPresent(historiaClinica::setPaciente);

        return historiaClinica;
    }

    // Método para convertir de Consulta a ConsultaDTO
    private ConsultaDTO convertConsultaToDTO(Consulta consulta) {
        ConsultaDTO consultaDTO = new ConsultaDTO();
        consultaDTO.setId(consulta.getId());
        consultaDTO.setNotas(consulta.getNotas()); // Notas de la consulta
        consultaDTO.setFechaConsulta(consulta.getFechaConsulta().toString());
        consultaDTO.setMedicoId(consulta.getMedico().getId());
        consultaDTO.setMedicoNombre(consulta.getMedico().getNombreCompleto());
        return consultaDTO;
    }

    // Obtener todas las historias clínicas
    @GetMapping
    public ResponseEntity<List<HistoriaClinicaDTO>> getAllHistoriasClinicas() {
        List<HistoriaClinica> historias = historiaClinicaService.findAll();
        List<HistoriaClinicaDTO> historiaDTOS = historias.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(historiaDTOS);
    }

    // Obtener una historia clínica por ID
    @GetMapping("/{id}")
    public ResponseEntity<HistoriaClinicaDTO> getHistoriaClinicaById(@PathVariable Long id) {
        Optional<HistoriaClinica> historia = historiaClinicaService.findById(id);
        return historia.map(value -> ResponseEntity.ok(convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva historia clínica
    @PostMapping
    public ResponseEntity<HistoriaClinicaDTO> createHistoriaClinica(@RequestBody HistoriaClinicaDTO historiaDTO) {
        HistoriaClinica historiaClinica = convertToEntity(historiaDTO);
        HistoriaClinica nuevaHistoria = historiaClinicaService.save(historiaClinica);
        HistoriaClinicaDTO nuevaHistoriaDTO = convertToDTO(nuevaHistoria);
        return new ResponseEntity<>(nuevaHistoriaDTO, HttpStatus.CREATED);
    }

    // Actualizar una historia clínica existente
    @PutMapping("/{id}")
    public ResponseEntity<HistoriaClinicaDTO> updateHistoriaClinica(@PathVariable Long id, @RequestBody HistoriaClinicaDTO historiaDTO) {
        Optional<HistoriaClinica> historiaExistente = historiaClinicaService.findById(id);
        if (historiaExistente.isPresent()) {
            HistoriaClinica historiaClinica = convertToEntity(historiaDTO);
            historiaClinica.setId(id); // Establece el ID de la historia clínica a actualizar
            HistoriaClinica historiaActualizada = historiaClinicaService.save(historiaClinica);
            HistoriaClinicaDTO historiaActualizadaDTO = convertToDTO(historiaActualizada);
            return ResponseEntity.ok(historiaActualizadaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una historia clínica por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistoriaClinica(@PathVariable Long id) {
        Optional<HistoriaClinica> historia = historiaClinicaService.findById(id);
        if (historia.isPresent()) {
            historiaClinicaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
