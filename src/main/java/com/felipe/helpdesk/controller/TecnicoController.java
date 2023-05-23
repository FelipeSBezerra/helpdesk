package com.felipe.helpdesk.controller;

import com.felipe.helpdesk.domain.Tecnico;
import com.felipe.helpdesk.service.TecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoController {

    @Autowired
    private TecnicoService tecnicoService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Tecnico> findById(@PathVariable Integer id){
        return new ResponseEntity<>(tecnicoService.findById(id), HttpStatus.OK);
    }
}
