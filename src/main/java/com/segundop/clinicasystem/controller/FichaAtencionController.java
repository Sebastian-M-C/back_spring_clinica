package com.segundop.clinicasystem.controller;

import com.segundop.clinicasystem.dto.FichaAtencionDTO;
import com.segundop.clinicasystem.entity.FichaAtencion;
import com.segundop.clinicasystem.service.FichaAtencionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/fichas")
public class FichaAtencionController {

    @Autowired
    private FichaAtencionService fichaAtencionService;

    // Crear una nueva ficha de atención
    @PostMapping
    public ResponseEntity<?> createFichaAtencion(@Valid @RequestBody FichaAtencionDTO fichaAtencionDTO) {
        try {
            FichaAtencion ficha = fichaAtencionService.createFichaAtencion(fichaAtencionDTO);
            return ResponseEntity.ok(fichaAtencionService.convertToDTO(ficha));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error inesperado: " + e.getMessage());
        }
    }



    // Obtener ficha por ID
    @GetMapping("/{id}")
    public ResponseEntity<FichaAtencionDTO> getFichaById(@PathVariable Long id) {
        try {
            FichaAtencionDTO ficha = fichaAtencionService.getFichaAtencionById(id);
            return ResponseEntity.ok(ficha);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Listar todas las fichas
    @GetMapping
    public ResponseEntity<List<FichaAtencionDTO>> getAllFichas() {
        try {
            List<FichaAtencionDTO> fichas = fichaAtencionService.getAllFichas();
            return ResponseEntity.ok(fichas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Eliminar una ficha por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFicha(@PathVariable Long id) {
        try {
            fichaAtencionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
