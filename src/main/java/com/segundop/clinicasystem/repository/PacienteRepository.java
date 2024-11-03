package com.segundop.clinicasystem.repository;

import com.segundop.clinicasystem.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente,Long> {
}
