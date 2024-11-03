package com.segundop.clinicasystem.dto;

import lombok.Data;

@Data
public class PacienteDTO {

    private Long id;

    private String nombreCompleto;

    private String fechaNacimiento;
}
