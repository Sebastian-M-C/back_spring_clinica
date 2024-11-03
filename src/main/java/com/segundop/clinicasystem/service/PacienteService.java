package com.segundop.clinicasystem.service;

import com.segundop.clinicasystem.entity.Paciente;
import com.segundop.clinicasystem.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente save(Paciente paciente){
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> findById(Long id){
        return pacienteRepository.findById(id);
    }

    public List<Paciente> findAll(){
        return pacienteRepository.findAll();
    }

    public void deleteById(Long id){
        pacienteRepository.deleteById(id);
    }
}
