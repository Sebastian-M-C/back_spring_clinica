package com.segundop.clinicasystem.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class HorarioDTO {

    private Long id;
    private String fecha;
    private String horaFin;
    private Integer capacidadFichas;
    private Long medicoId; // Este es el ID del médico al que pertenece el horario
}
