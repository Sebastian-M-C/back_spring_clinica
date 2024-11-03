package com.segundop.clinicasystem.repository;

import com.segundop.clinicasystem.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
}
