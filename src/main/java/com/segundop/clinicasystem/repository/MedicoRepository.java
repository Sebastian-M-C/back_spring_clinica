package com.segundop.clinicasystem.repository;
import java.util.*;

import com.segundop.clinicasystem.entity.Especialidad;
import com.segundop.clinicasystem.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    List<Medico> findByEspecialidad(Especialidad especialidad);
}
