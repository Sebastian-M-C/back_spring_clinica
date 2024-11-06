package com.segundop.clinicasystem.repository;

import com.segundop.clinicasystem.entity.Horario;
import com.segundop.clinicasystem.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Long> {

    List<Horario> findByMedico (Medico medico);
}
