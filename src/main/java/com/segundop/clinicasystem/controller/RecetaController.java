package com.segundop.clinicasystem.controller;

import com.segundop.clinicasystem.dto.RecetaDTO;
import com.segundop.clinicasystem.entity.Consulta;
import com.segundop.clinicasystem.entity.Receta;
import com.segundop.clinicasystem.service.ConsultaService;
import com.segundop.clinicasystem.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/recetas")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private ConsultaService consultaService;

    // Método para convertir de Receta a RecetaDTO
    private RecetaDTO convertToDTO(Receta receta) {
        RecetaDTO recetaDTO = new RecetaDTO();
        recetaDTO.setId(receta.getId());
        recetaDTO.setDescripcion(receta.getDescripcion());
        recetaDTO.setConsultaId(receta.getConsulta().getId());
        return recetaDTO;
    }

    // Método para convertir de RecetaDTO a Receta
    private Receta convertToEntity(RecetaDTO recetaDTO) {
        Receta receta = new Receta();
        receta.setDescripcion(recetaDTO.getDescripcion());

        // Asignar consulta
        Optional<Consulta> consulta = consultaService.findById(recetaDTO.getConsultaId());
        consulta.ifPresent(receta::setConsulta);

        return receta;
    }

    // Obtener todas las recetas
    @GetMapping
    public ResponseEntity<List<Receta>> getAllRecetas() {
        List<Receta> recetas = recetaService.findAll();
        return ResponseEntity.ok(recetas);
    }


    // Obtener una receta por ID
    @GetMapping("/{id}")
    public ResponseEntity<RecetaDTO> getRecetaById(@PathVariable Long id) {
        Optional<Receta> receta = recetaService.findById(id);
        return receta.map(value -> ResponseEntity.ok(convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva receta
    @PostMapping
    public ResponseEntity<Receta> createReceta(@RequestBody RecetaDTO recetaDTO) {
        Receta receta = new Receta();
        receta.setDescripcion(recetaDTO.getDescripcion()); // Solo guarda la descripción sin asignar consulta

        Receta nuevaReceta = recetaService.save(receta);
        return new ResponseEntity<>(nuevaReceta, HttpStatus.CREATED);
    }


    // Actualizar una receta existente
    @PutMapping("/{id}")
    public ResponseEntity<RecetaDTO> updateReceta(@PathVariable Long id, @RequestBody RecetaDTO recetaDTO) {
        Optional<Receta> recetaExistente = recetaService.findById(id);
        if (recetaExistente.isPresent()) {
            Receta receta = convertToEntity(recetaDTO);
            receta.setId(id); // Establece el ID de la receta a actualizar
            Receta recetaActualizada = recetaService.save(receta);
            RecetaDTO recetaActualizadaDTO = convertToDTO(recetaActualizada);
            return ResponseEntity.ok(recetaActualizadaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una receta por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceta(@PathVariable Long id) {
        Optional<Receta> receta = recetaService.findById(id);
        if (receta.isPresent()) {
            recetaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
