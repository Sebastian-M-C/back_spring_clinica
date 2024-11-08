package com.segundop.clinicasystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "medicos")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCompleto;

    //Relacion muchos a uno con especialidad
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidad_id", nullable = false)
    @ToString.Exclude
    private Especialidad especialidad;

    //Relacion uno a uno con usuarios
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @ToString.Exclude
    private Usuario usuario;

    //Relacion uno a muchos on horarios
    @OneToMany(mappedBy = "medico",cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Horario> horarios = new ArrayList<>();


}
