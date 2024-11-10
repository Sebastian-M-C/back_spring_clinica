package com.segundop.clinicasystem.service;

import com.segundop.clinicasystem.entity.Triaje;
import com.segundop.clinicasystem.repository.TriajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TriajeService {

    @Autowired
    private TriajeRepository triajeRepository;


    public Triaje saveTriaje(Triaje triaje){
        return triajeRepository.save(triaje);
    }

    public Optional<Triaje> findById(Long id){
        return triajeRepository.findById(id);
    }
}
