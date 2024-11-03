package com.segundop.clinicasystem.service;

import com.segundop.clinicasystem.entity.Especialidad;
import com.segundop.clinicasystem.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    public List<Especialidad> findAll(){
        return especialidadRepository.findAll();
    }

    public Optional<Especialidad> findById(Long id){
        return especialidadRepository.findById(id);
    }

    public Especialidad save(Especialidad especialidad){
        return especialidadRepository.save(especialidad);
    }

    public void deleteById(Long id){
        especialidadRepository.deleteById(id);
    }

    public Optional<Especialidad> findByTipo(String especialidad) {
        return especialidadRepository.findByTipo(especialidad);
    }
}
