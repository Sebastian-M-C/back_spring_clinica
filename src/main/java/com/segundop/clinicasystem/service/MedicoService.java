package com.segundop.clinicasystem.service;

import com.segundop.clinicasystem.entity.Especialidad;
import com.segundop.clinicasystem.entity.Medico;
import com.segundop.clinicasystem.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    public Optional<Medico> findById(Long id){
        return medicoRepository.findById(id);
    }

    public List<Medico> findAll(){
        return medicoRepository.findAll();
    }

    public Medico save(Medico medico){
        return medicoRepository.save(medico);
    }

    public void deleteById(Long id){
        medicoRepository.deleteById(id);
    }

    public List<Medico> findByEspecialidad(Especialidad especialidad) {
        return medicoRepository.findByEspecialidad(especialidad);
    }
}
