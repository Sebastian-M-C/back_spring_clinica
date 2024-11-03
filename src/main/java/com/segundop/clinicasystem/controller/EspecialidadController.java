package com.segundop.clinicasystem.controller;

import com.segundop.clinicasystem.dto.EspecialidadDTO;
import com.segundop.clinicasystem.entity.Especialidad;
import com.segundop.clinicasystem.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    // Método para convertir de EspecialidadDTO a Especialidad
    public Especialidad convertToEntity(EspecialidadDTO especialidadDTO) {
        Especialidad especialidad = new Especialidad();
        especialidad.setTipo(especialidadDTO.getTipo());
        especialidad.setDescripcion(especialidadDTO.getDescripcion());
        return especialidad;
    }

    // Método para convertir de Especialidad a EspecialidadDTO
    public EspecialidadDTO convertToDTO(Especialidad especialidad) {
        EspecialidadDTO especialidadDTO = new EspecialidadDTO();
        especialidadDTO.setId(especialidad.getId());
        especialidadDTO.setTipo(especialidad.getTipo());
        especialidadDTO.setDescripcion(especialidad.getDescripcion());
        return especialidadDTO;
    }

    // Obtener todas las especialidades
    @GetMapping
    public ResponseEntity<List<EspecialidadDTO>> getAllEspecialidades() {
        List<Especialidad> especialidades = especialidadService.findAll();
        List<EspecialidadDTO> especialidadDTOS = new ArrayList<>();
        for (Especialidad especialidad : especialidades) {
            especialidadDTOS.add(convertToDTO(especialidad));
        }
        return ResponseEntity.ok(especialidadDTOS);
    }

    // Obtener una especialidad por ID
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadDTO> getEspecialidadById(@PathVariable Long id) {
        Optional<Especialidad> especialidad = especialidadService.findById(id);
        if (especialidad.isPresent()) {
            EspecialidadDTO especialidadDTO = convertToDTO(especialidad.get());
            return ResponseEntity.ok(especialidadDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear una nueva especialidad
    @PostMapping
    public ResponseEntity<EspecialidadDTO> createEspecialidad(@RequestBody EspecialidadDTO especialidadDTO) {
        Especialidad especialidad = convertToEntity(especialidadDTO);
        Especialidad nuevaEspecialidad = especialidadService.save(especialidad);
        EspecialidadDTO nuevaEspecialidadDTO = convertToDTO(nuevaEspecialidad);
        return new ResponseEntity<>(nuevaEspecialidadDTO, HttpStatus.CREATED);
    }

    // Actualizar una especialidad existente
    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadDTO> updateEspecialidad(@PathVariable Long id, @RequestBody EspecialidadDTO especialidadDTO) {
        Optional<Especialidad> especialidadExistente = especialidadService.findById(id);
        if (especialidadExistente.isPresent()) {
            Especialidad especialidad = convertToEntity(especialidadDTO);
            especialidad.setId(id); // Establece el ID de la especialidad a actualizar
            Especialidad especialidadActualizada = especialidadService.save(especialidad);
            EspecialidadDTO especialidadActualizadaDTO = convertToDTO(especialidadActualizada);
            return ResponseEntity.ok(especialidadActualizadaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una especialidad por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEspecialidad(@PathVariable Long id) {
        Optional<Especialidad> especialidad = especialidadService.findById(id);
        if (especialidad.isPresent()) {
            especialidadService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
