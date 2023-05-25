package com.felipe.helpdesk.controller;

import com.felipe.helpdesk.domain.dto.ChamadoDto;
import com.felipe.helpdesk.service.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoController {

    @Autowired
    ChamadoService chamadoService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ChamadoDto> findById(@PathVariable Integer id){
        return new ResponseEntity<>(new ChamadoDto(chamadoService.findById(id)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ChamadoDto>> findAll(){
        return new ResponseEntity<>(chamadoService.findAll().stream().map(ChamadoDto::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ChamadoDto> create(@Valid @RequestBody ChamadoDto chamadoDto){
        ChamadoDto obj = new ChamadoDto(chamadoService.save(chamadoDto));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ChamadoDto> update(@PathVariable Integer id, @Valid @RequestBody ChamadoDto chamadoDto){
        return new ResponseEntity<>(new ChamadoDto(chamadoService.update(id, chamadoDto)), HttpStatus.OK);
    }
}
