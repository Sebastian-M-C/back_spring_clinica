package com.segundop.clinicasystem.controller;

import com.segundop.clinicasystem.dto.PacienteDTO;
import com.segundop.clinicasystem.entity.Paciente;
import com.segundop.clinicasystem.mapper.PacienteMapper;
import com.segundop.clinicasystem.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {


    @Autowired
    private PacienteService pacienteService;

    //Metodo para convertir de Paciente a PacienteDTO
    private PacienteDTO convertToDTO(Paciente paciente){
        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setId(paciente.getId());
        pacienteDTO.setNombreCompleto(paciente.getNombreCompleto());
        pacienteDTO.setFechaNacimiento(paciente.getFechaNacimiento().toString());
        return pacienteDTO;
    }
     //metodo para convertir de PacienteDTO a Paciente
    private Paciente convertToEntity(PacienteDTO pacienteDTO){
        Paciente paciente = new Paciente();
        paciente.setNombreCompleto(pacienteDTO.getNombreCompleto());
        paciente.setFechaNacimiento(LocalDate.parse(pacienteDTO.getFechaNacimiento()));
        return paciente;
    }

    //Obtener todos los pacientes
    @GetMapping
    public ResponseEntity<List<PacienteDTO>> getAllPacientes(){
        List<Paciente> pacientes = pacienteService.findAll();
        List<PacienteDTO> pacienteDTOS = new ArrayList<>();
        for( Paciente paciente: pacientes){
            pacienteDTOS.add(convertToDTO(paciente));
        }
        return ResponseEntity.ok(pacienteDTOS);
    }

    //Obtener un paciente por ID
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> getPacienteById(@PathVariable Long id){
        Optional<Paciente> paciente = pacienteService.findById(id);
        if(paciente.isPresent()){
            PacienteDTO pacienteDTO = convertToDTO(paciente.get());
            return ResponseEntity.ok(pacienteDTO);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //Crear un nuevo paciente
    @PostMapping
    public ResponseEntity<PacienteDTO> createPaciente(@RequestBody PacienteDTO pacienteDTO){
        Paciente paciente = convertToEntity(pacienteDTO);
        Paciente nuevoPaciente = pacienteService.save(paciente);
        PacienteDTO nuevoPacienteDTO = convertToDTO(nuevoPaciente);
        return new ResponseEntity<>(nuevoPacienteDTO, HttpStatus.CREATED);
    }

    // Actualizar un paciente existente
    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> updatePaciente(@PathVariable Long id, @RequestBody PacienteDTO pacienteDTO) {
        Optional<Paciente> pacienteExistente = pacienteService.findById(id);
        if (pacienteExistente.isPresent()) {
            Paciente paciente = convertToEntity(pacienteDTO);
            paciente.setId(id); // Establece el ID del paciente a actualizar
            Paciente pacienteActualizado = pacienteService.save(paciente);
            PacienteDTO pacienteActualizadoDTO = convertToDTO(pacienteActualizado);
            return ResponseEntity.ok(pacienteActualizadoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un paciente por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        Optional<Paciente> paciente = pacienteService.findById(id);
        if (paciente.isPresent()) {
            pacienteService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
