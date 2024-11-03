package com.segundop.clinicasystem.service;

import com.segundop.clinicasystem.entity.FichaAtencion;
import com.segundop.clinicasystem.repository.FichaAtencionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FichaAtencionService {

    @Autowired
    private FichaAtencionRepository fichaAtencionRepository;


    public FichaAtencion save(FichaAtencion fichaAtencion) {
        return fichaAtencionRepository.save(fichaAtencion);
    }

    public Optional<FichaAtencion> findById(Long id) {
        return fichaAtencionRepository.findById(id);
    }

    public List<FichaAtencion> findAll() {
        return fichaAtencionRepository.findAll();
    }

    public void deleteById(Long id) {
        fichaAtencionRepository.deleteById(id);
    }
}
