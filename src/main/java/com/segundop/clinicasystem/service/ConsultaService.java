package com.segundop.clinicasystem.service;

import com.segundop.clinicasystem.entity.Consulta;
import com.segundop.clinicasystem.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    public Consulta save(Consulta consulta) {
        return consultaRepository.save(consulta);
    }

    public Optional<Consulta> findById(Long id) {
        return consultaRepository.findById(id);
    }

    public List<Consulta> findAll() {
        return consultaRepository.findAll();
    }

    public void deleteById(Long id) {
        consultaRepository.deleteById(id);
    }
}
