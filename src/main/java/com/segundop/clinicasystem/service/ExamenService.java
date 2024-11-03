package com.segundop.clinicasystem.service;

import com.segundop.clinicasystem.entity.Examen;
import com.segundop.clinicasystem.repository.ExamenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamenService {

    @Autowired
    private ExamenRepository examenRepository;


    public Examen save(Examen examen) {
        return examenRepository.save(examen);
    }

    public Optional<Examen> findById(Long id) {
        return examenRepository.findById(id);
    }

    public List<Examen> findAll() {
        return examenRepository.findAll();
    }

    public void deleteById(Long id) {
        examenRepository.deleteById(id);
    }
}
