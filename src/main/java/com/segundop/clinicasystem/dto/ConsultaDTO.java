package com.segundop.clinicasystem.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class ConsultaDTO {

    private Long id;
    private String fechaConsulta; // Fecha de la consulta representada como String
    private String notas; // Notas de la consulta
    private Long historiaClinicaId; // ID de la historia clínica
    private Long medicoId; // ID del médico
    private String medicoNombre; // Nombre del médico
    private List<ExamenDTO> examenes; // Lista de exámenes representados como DTO
    private List<RecetaDTO> recetas; // Lista de recetas representadas como DTO
}
