package com.felipe.helpdesk.service;

import com.felipe.helpdesk.domain.Tecnico;
import com.felipe.helpdesk.domain.dto.TecnicoDto;
import com.felipe.helpdesk.repository.TecnicoRepository;
import com.felipe.helpdesk.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    public Tecnico findById(Integer id) {
        return tecnicoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Técnico não encontrado: Id=" + id));
    }

    public List<Tecnico> findAll(){
        return tecnicoRepository.findAll();
    }

    public Tecnico save(TecnicoDto tecnicoDto){
        tecnicoDto.setId(null);
        Tecnico obj = new Tecnico(tecnicoDto);
        return tecnicoRepository.save(obj);
    }
}
