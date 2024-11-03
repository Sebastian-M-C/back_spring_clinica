package com.segundop.clinicasystem.service;

import com.segundop.clinicasystem.entity.HistoriaClinica;
import com.segundop.clinicasystem.repository.HistoriaClinicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoriaClinicaService {

    @Autowired
    private HistoriaClinicaRepository historiaClinicaRepository;

    public HistoriaClinica save(HistoriaClinica historiaClinica){
        return historiaClinicaRepository.save(historiaClinica);
    }

    public Optional<HistoriaClinica> findById(Long id){
        return historiaClinicaRepository.findById(id);
    }

    public List<HistoriaClinica> findAll(){
        return historiaClinicaRepository.findAll();
    }

    public void deleteById(Long id){
        historiaClinicaRepository.deleteById(id);
    }
}
