package com.segundop.clinicasystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class MedicoDTO {

    private Long id;
    private String nombreCompleto;
    private Long especialidadId; // Este es el ID de la especialidad
    private String especialidad;
    private String usuarioNombre;
    private List<String> horarios;
}
