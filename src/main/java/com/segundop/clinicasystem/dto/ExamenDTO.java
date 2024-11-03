package com.segundop.clinicasystem.dto;

import lombok.Data;

@Data
public class ExamenDTO {

    private Long id; // Asegúrate de que el tipo coincida con el de la entidad (Long en lugar de String)
    private String tipo;
    private String resultados;
    private Long consultaId; // ID de la consulta asociada
}
