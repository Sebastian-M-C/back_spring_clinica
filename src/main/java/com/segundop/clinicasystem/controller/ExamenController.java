package com.segundop.clinicasystem.controller;

import com.segundop.clinicasystem.dto.ExamenDTO;
import com.segundop.clinicasystem.entity.Consulta;
import com.segundop.clinicasystem.entity.Examen;
import com.segundop.clinicasystem.service.ConsultaService;
import com.segundop.clinicasystem.service.ExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/examenes")
public class ExamenController {

    @Autowired
    private ExamenService examenService;

    @Autowired
    private ConsultaService consultaService;

    // Método para convertir de Examen a ExamenDTO
    private ExamenDTO convertToDTO(Examen examen) {
        ExamenDTO examenDTO = new ExamenDTO();
        examenDTO.setId(examen.getId());
        examenDTO.setTipo(examen.getTipo());
        examenDTO.setResultados(examen.getResultados());
        examenDTO.setConsultaId(examen.getConsulta().getId());
        return examenDTO;
    }

    // Método para convertir de ExamenDTO a Examen
    private Examen convertToEntity(ExamenDTO examenDTO) {
        Examen examen = new Examen();
        examen.setTipo(examenDTO.getTipo());
        examen.setResultados(examenDTO.getResultados());

        // Asignar consulta
        Optional<Consulta> consulta = consultaService.findById(examenDTO.getConsultaId());
        consulta.ifPresent(examen::setConsulta);

        return examen;
    }

    // Obtener todos los exámenes
    @GetMapping
    public ResponseEntity<List<ExamenDTO>> getAllExamenes() {
        List<Examen> examenes = examenService.findAll();
        List<ExamenDTO> examenDTOS = examenes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(examenDTOS);
    }

    // Obtener un examen por ID
    @GetMapping("/{id}")
    public ResponseEntity<ExamenDTO> getExamenById(@PathVariable Long id) {
        Optional<Examen> examen = examenService.findById(id);
        return examen.map(value -> ResponseEntity.ok(convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo examen
    @PostMapping
    public ResponseEntity<ExamenDTO> createExamen(@RequestBody ExamenDTO examenDTO) {
        Examen examen = convertToEntity(examenDTO);
        Examen nuevoExamen = examenService.save(examen);
        ExamenDTO nuevoExamenDTO = convertToDTO(nuevoExamen);
        return new ResponseEntity<>(nuevoExamenDTO, HttpStatus.CREATED);
    }

    // Actualizar un examen existente
    @PutMapping("/{id}")
    public ResponseEntity<ExamenDTO> updateExamen(@PathVariable Long id, @RequestBody ExamenDTO examenDTO) {
        Optional<Examen> examenExistente = examenService.findById(id);
        if (examenExistente.isPresent()) {
            Examen examen = convertToEntity(examenDTO);
            examen.setId(id); // Establece el ID del examen a actualizar
            Examen examenActualizado = examenService.save(examen);
            ExamenDTO examenActualizadoDTO = convertToDTO(examenActualizado);
            return ResponseEntity.ok(examenActualizadoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un examen por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamen(@PathVariable Long id) {
        Optional<Examen> examen = examenService.findById(id);
        if (examen.isPresent()) {
            examenService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
