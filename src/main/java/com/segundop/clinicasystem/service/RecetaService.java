package com.segundop.clinicasystem.service;

import com.segundop.clinicasystem.entity.Receta;
import com.segundop.clinicasystem.repository.RecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    public Receta save(Receta receta) {
        return recetaRepository.save(receta);
    }

    public Optional<Receta> findById(Long id) {
        return recetaRepository.findById(id);
    }

    public List<Receta> findAll() {
        return recetaRepository.findAll();
    }

    public void deleteById(Long id) {
        recetaRepository.deleteById(id);
    }
}
