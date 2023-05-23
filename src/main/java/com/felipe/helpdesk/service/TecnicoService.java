package com.felipe.helpdesk.service;

import com.felipe.helpdesk.domain.Tecnico;
import com.felipe.helpdesk.repository.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    public Tecnico findById(Integer id){
        return tecnicoRepository.findById(id).orElse(null);
    }
}
