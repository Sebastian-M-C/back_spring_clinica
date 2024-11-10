package com.segundop.clinicasystem.controller;

import com.segundop.clinicasystem.dto.FichaAtencionDTO;
import com.segundop.clinicasystem.dto.TriajeRequest;
import com.segundop.clinicasystem.entity.FichaAtencion;
import com.segundop.clinicasystem.entity.Triaje;
import com.segundop.clinicasystem.service.FichaAtencionService;
import com.segundop.clinicasystem.service.TriajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/triaje")
public class TriajeController {

    @Autowired
    private TriajeService triajeService;

    @Autowired
    private FichaAtencionService fichaAtencionService;

    private FichaAtencionDTO convertToDTO(FichaAtencion fichaAtencion) {
        FichaAtencionDTO fichaDTO = new FichaAtencionDTO();
        fichaDTO.setId(fichaAtencion.getId());
        fichaDTO.setFechaAtencion(fichaAtencion.getFechaAtencion().toString());
        fichaDTO.setPacienteId(fichaAtencion.getPaciente().getId());
        fichaDTO.setPacienteNombre(fichaAtencion.getPaciente().getNombreCompleto());
        fichaDTO.setMedicoId(fichaAtencion.getMedico().getId());
        fichaDTO.setMedicoNombre(fichaAtencion.getMedico().getNombreCompleto());
        fichaDTO.setEspecialidadId(fichaAtencion.getEspecialidad().getId());
        return fichaDTO;
    }

    // Endpoint para obtener todas las fichas de atención para triaje
    @GetMapping
    public ResponseEntity<List<FichaAtencionDTO>> getAllFichasEnTriaje() {
        List<FichaAtencion> fichasEnTriaje = fichaAtencionService.findAll(); // Ajusta esto según tu servicio
        List<FichaAtencionDTO> fichaDTOs = fichasEnTriaje.stream()
                .map(ficha -> {
                    FichaAtencionDTO dto = new FichaAtencionDTO();
                    dto.setId(ficha.getId());
                    dto.setFechaAtencion(ficha.getFechaAtencion().toString());
                    dto.setPacienteId(ficha.getPaciente().getId());
                    dto.setPacienteNombre(ficha.getPaciente().getNombreCompleto());
                    dto.setMedicoId(ficha.getMedico().getId());
                    dto.setMedicoNombre(ficha.getMedico().getNombreCompleto());
                    dto.setEspecialidadId(ficha.getEspecialidad().getId());
                    dto.setHorarioDescripcion(ficha.getHorario().toString());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(fichaDTOs);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Triaje>> getAllTriaje() {
        List<Triaje> triajeList = triajeService.findAll();
        return ResponseEntity.ok(triajeList);
    }



    // Endpoint para crear un nuevo registro de triaje
    @PostMapping
    public ResponseEntity<Triaje> createTriaje(@RequestBody TriajeRequest triajeRequest) {
        FichaAtencion fichaAtencion = fichaAtencionService.findById(triajeRequest.getFichaAtencionId())
                .orElseThrow(() -> new RuntimeException("Ficha de Atención no encontrada"));

        Triaje triaje = new Triaje();
        triaje.setDescripcion(triajeRequest.getDescripcion());
        triaje.setFichaAtencion(fichaAtencion);

        Triaje newTriaje = triajeService.saveTriaje(triaje);
        return new ResponseEntity<>(newTriaje, HttpStatus.CREATED);
    }
}
