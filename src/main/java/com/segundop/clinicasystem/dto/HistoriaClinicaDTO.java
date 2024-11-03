package com.segundop.clinicasystem.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class HistoriaClinicaDTO {

    private Long id;
    private String fechaCreacion; // Representada como String para facilidad de formateo
    private Long pacienteId; // ID del paciente
    private String pacienteNombre; // Nombre del paciente
    private List<ConsultaDTO> consultas; // Lista de consultas representadas como DTO

}
