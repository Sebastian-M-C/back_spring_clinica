package com.segundop.clinicasystem.dto;

import lombok.Data;

@Data
public class FichaAtencionDTO {

    private Long id;
    private String fechaAtencion;
    private Long pacienteId;
    private String pacienteNombre;
    private Long medicoId;
    private String medicoNombre;
    private Long horarioId;
    private String horarioDescripcion;
    private Long especialidadId;

}
