package com.segundop.clinicasystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "ficha_atencion")
public class FichaAtencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaAtencion;

    //relacion muchos a uno con paciente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    //relacion muchos a uno con medico
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    //relacion muchos a uno con horario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "horario_id", nullable = false)
    private Horario horario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidad_id", nullable = false)
    private Especialidad especialidad;

    @OneToMany(mappedBy = "fichaAtencion", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Triaje> triajes = new ArrayList<>();
}

