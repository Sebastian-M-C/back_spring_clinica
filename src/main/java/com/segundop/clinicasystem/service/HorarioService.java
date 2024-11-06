package com.segundop.clinicasystem.service;

import com.segundop.clinicasystem.entity.Horario;
import com.segundop.clinicasystem.entity.Medico;
import com.segundop.clinicasystem.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    public Horario save(Horario horario){
        return horarioRepository.save(horario);
    }

    public Optional<Horario> findById(Long id){
        return horarioRepository.findById(id);
    }

    public List<Horario> findAll(){
        return horarioRepository.findAll();
    }

    public void deleteById(Long id){
        horarioRepository.deleteById(id);
    }

    // Buscar horarios por médico
    public List<Horario> findByMedico(Medico medico) {
        return horarioRepository.findByMedico(medico);
    }

}
