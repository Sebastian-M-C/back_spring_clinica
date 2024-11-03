package com.segundop.clinicasystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "horarios")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    private LocalTime horaFin;

    private int capacidadFichas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id",nullable = false)
    private Medico medico;
}
