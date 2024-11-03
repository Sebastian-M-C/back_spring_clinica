package com.segundop.clinicasystem.controller;

import com.segundop.clinicasystem.dto.MedicoDTO;
import com.segundop.clinicasystem.entity.Medico;
import com.segundop.clinicasystem.entity.Especialidad;
import com.segundop.clinicasystem.entity.Usuario;
import com.segundop.clinicasystem.service.MedicoService;
import com.segundop.clinicasystem.service.EspecialidadService;
import com.segundop.clinicasystem.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private EspecialidadService especialidadService;

    @Autowired
    private UsuarioService usuarioService;

    // Método para convertir de Medico a MedicoDTO
    private MedicoDTO convertToDTO(Medico medico) {
        MedicoDTO medicoDTO = new MedicoDTO();
        medicoDTO.setId(medico.getId());
        medicoDTO.setNombreCompleto(medico.getNombreCompleto());
        medicoDTO.setEspecialidad(medico.getEspecialidad().getTipo());
        medicoDTO.setUsuarioNombre(medico.getUsuario().getNombreUsuario());

        List<String> horarios = medico.getHorarios().stream()
                .map(horario -> horario.toString()) // Ajusta según lo que quieras mostrar en el DTO
                .collect(Collectors.toList());
        medicoDTO.setHorarios(horarios);

        return medicoDTO;
    }

    //convertir de dto a entity
    private Medico convertToEntity(MedicoDTO medicoDTO) {
        Medico medico = new Medico();
        medico.setNombreCompleto(medicoDTO.getNombreCompleto());

        if (medicoDTO.getEspecialidadId() != null) {
            Optional<Especialidad> especialidad = especialidadService.findById(medicoDTO.getEspecialidadId());
            especialidad.ifPresent(medico::setEspecialidad);
        }

        // Verificar y asignar el usuario
        if (medicoDTO.getUsuarioNombre() != null && !medicoDTO.getUsuarioNombre().isEmpty()) {
            Optional<Usuario> usuario = usuarioService.findByNombreUsuario(medicoDTO.getUsuarioNombre());
            if (usuario.isPresent()) {
                medico.setUsuario(usuario.get());
            } else {
                throw new RuntimeException("Usuario no encontrado: " + medicoDTO.getUsuarioNombre());
            }
        } else {
            throw new RuntimeException("El nombre de usuario es requerido");
        }

        return medico;
    }



    // Obtener todos los médicos
    @GetMapping
    public ResponseEntity<List<MedicoDTO>> getAllMedicos() {
        List<Medico> medicos = medicoService.findAll();
        List<MedicoDTO> medicoDTOS = new ArrayList<>();
        for (Medico medico : medicos) {
            medicoDTOS.add(convertToDTO(medico));
        }
        return ResponseEntity.ok(medicoDTOS);
    }

    // Obtener un médico por ID
    @GetMapping("/{id}")
    public ResponseEntity<MedicoDTO> getMedicoById(@PathVariable Long id) {
        Optional<Medico> medico = medicoService.findById(id);
        if (medico.isPresent()) {
            MedicoDTO medicoDTO = convertToDTO(medico.get());
            return ResponseEntity.ok(medicoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un nuevo médico
    @PostMapping
    public ResponseEntity<MedicoDTO> createMedico(@RequestBody MedicoDTO medicoDTO) {
        Medico medico = convertToEntity(medicoDTO);

        // Verificar que la especialidad esté asignada correctamente
        if (medico.getEspecialidad() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Verificar que el usuario esté asignado correctamente
        if (medico.getUsuario() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Medico nuevoMedico = medicoService.save(medico);
        MedicoDTO nuevoMedicoDTO = convertToDTO(nuevoMedico);
        return new ResponseEntity<>(nuevoMedicoDTO, HttpStatus.CREATED);
    }


    // Actualizar un médico existente
    @PutMapping("/{id}")
    public ResponseEntity<MedicoDTO> updateMedico(@PathVariable Long id, @RequestBody MedicoDTO medicoDTO) {
        Optional<Medico> medicoExistente = medicoService.findById(id);
        if (medicoExistente.isPresent()) {
            Medico medico = convertToEntity(medicoDTO);
            medico.setId(id); // Establece el ID del médico a actualizar
            Medico medicoActualizado = medicoService.save(medico);
            MedicoDTO medicoActualizadoDTO = convertToDTO(medicoActualizado);
            return ResponseEntity.ok(medicoActualizadoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener médicos por especialidad
    @GetMapping("/especialidad/{especialidadId}")
    public ResponseEntity<List<MedicoDTO>> getMedicosByEspecialidad(@PathVariable Long especialidadId) {
        Optional<Especialidad> especialidad = especialidadService.findById(especialidadId);
        if (especialidad.isPresent()) {
            List<Medico> medicos = medicoService.findByEspecialidad(especialidad.get());
            List<MedicoDTO> medicoDTOS = medicos.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(medicoDTOS);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Eliminar un médico por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedico(@PathVariable Long id) {
        Optional<Medico> medico = medicoService.findById(id);
        if (medico.isPresent()) {
            medicoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
