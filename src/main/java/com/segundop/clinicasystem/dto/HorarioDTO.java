package com.segundop.clinicasystem.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class HorarioDTO {

    private Long id;

    private LocalDate fecha;

    private LocalTime horaFin;

    private int capacidadFichas;
}
