package com.segundop.clinicasystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCompleto;

    private LocalDate fechaNacimiento;

    //Relacion uno a uno con usuario
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id",nullable = false)
    private Usuario usuario;

    //relacion uno a uno con historiaClinica
    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL,orphanRemoval = true)
    private HistoriaClinica historiaClinica;
}
