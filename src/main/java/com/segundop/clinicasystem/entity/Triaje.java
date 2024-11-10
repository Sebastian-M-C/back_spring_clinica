package com.segundop.clinicasystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "triaje")
public class Triaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ficha_atencion_id")
    @JsonBackReference
    private FichaAtencion fichaAtencion;


}
