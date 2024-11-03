package com.segundop.clinicasystem.dto;

import lombok.Data;

@Data
public class FichaAtencionDTO {

    private Long id;
    private String fechaAtencion; // Formato de fecha como String (puedes usar LocalDate si prefieres)
    private Long pacienteId; // ID del paciente
    private String pacienteNombre; // Nombre del paciente
    private Long medicoId; // ID del médico
    private String medicoNombre; // Nombre del médico
    private Long horarioId; // ID del horario
    private String horarioDescripcion; // Descripción del horario (puedes personalizar esto)

}
