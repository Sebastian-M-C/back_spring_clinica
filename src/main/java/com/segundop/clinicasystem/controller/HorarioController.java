package com.segundop.clinicasystem.controller;

import com.segundop.clinicasystem.dto.HorarioDTO;
import com.segundop.clinicasystem.entity.Horario;
import com.segundop.clinicasystem.entity.Medico;
import com.segundop.clinicasystem.service.HorarioService;
import com.segundop.clinicasystem.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private MedicoService medicoService;

    // Convertir de Horario a HorarioDTO
    private HorarioDTO convertToDTO(Horario horario) {
        HorarioDTO horarioDTO = new HorarioDTO();
        horarioDTO.setId(horario.getId());
        horarioDTO.setFecha(horario.getFecha().toString());
        horarioDTO.setHoraFin(horario.getHoraFin().toString());
        horarioDTO.setCapacidadFichas(horario.getCapacidadFichas());
        horarioDTO.setMedicoId(horario.getMedico().getId());
        return horarioDTO;
    }

    // Convertir de HorarioDTO a Horario
    private Horario convertToEntity(HorarioDTO horarioDTO) {
        Horario horario = new Horario();

        if (horarioDTO.getFecha() == null || horarioDTO.getFecha().isEmpty()) {
            throw new RuntimeException("La fecha es requerida");
        }
        if (horarioDTO.getHoraFin() == null || horarioDTO.getHoraFin().isEmpty()) {
            throw new RuntimeException("La hora de finalización es requerida");
        }

        horario.setFecha(LocalDate.parse(horarioDTO.getFecha()));
        horario.setHoraFin(LocalTime.parse(horarioDTO.getHoraFin()));
        horario.setCapacidadFichas(horarioDTO.getCapacidadFichas());

        Optional<Medico> medico = medicoService.findById(horarioDTO.getMedicoId());
        medico.ifPresent(horario::setMedico);

        return horario;
    }

    // Crear un nuevo horario
    @PostMapping
    public ResponseEntity<HorarioDTO> createHorario(@RequestBody HorarioDTO horarioDTO) {
        if (horarioDTO.getMedicoId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            Horario horario = convertToEntity(horarioDTO);
            Horario nuevoHorario = horarioService.save(horario);
            HorarioDTO nuevoHorarioDTO = convertToDTO(nuevoHorario);
            return new ResponseEntity<>(nuevoHorarioDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Obtener todos los horarios de un médico
    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<HorarioDTO>> getHorariosByMedico(@PathVariable Long medicoId) {
        Optional<Medico> medico = medicoService.findById(medicoId);
        if (medico.isPresent()) {
            List<Horario> horarios = horarioService.findByMedico(medico.get());
            List<HorarioDTO> horarioDTOS = horarios.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(horarioDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un horario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHorario(@PathVariable Long id) {
        Optional<Horario> horario = horarioService.findById(id);
        if (horario.isPresent()) {
            horarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
