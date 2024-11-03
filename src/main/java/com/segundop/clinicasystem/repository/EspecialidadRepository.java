package com.segundop.clinicasystem.repository;

import com.segundop.clinicasystem.entity.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {

    Optional<Especialidad> findByTipo(String tipo);
}
