package com.segundop.clinicasystem.dto;

import lombok.Data;

@Data
public class RecetaDTO {

    private Long id;
    private String descripcion; // Descripción de la receta
    private Long consultaId; // ID de la consulta asociada
}
