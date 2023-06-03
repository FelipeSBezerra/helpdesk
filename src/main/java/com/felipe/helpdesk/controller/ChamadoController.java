package com.felipe.helpdesk.controller;

import com.felipe.helpdesk.domain.dto.ChamadoDto;
import com.felipe.helpdesk.service.ChamadoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/chamados")
public class ChamadoController {

    private final ChamadoService chamadoService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENTE', 'ROLE_TECNICO')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ChamadoDto> findById(@PathVariable Integer id){
        return new ResponseEntity<>(new ChamadoDto(chamadoService.findById(id)), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENTE', 'ROLE_TECNICO')")
    @GetMapping
    public ResponseEntity<List<ChamadoDto>> findAll(){
        return new ResponseEntity<>(chamadoService.findAll().stream().map(ChamadoDto::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TECNICO')")
    @PostMapping
    public ResponseEntity<ChamadoDto> create(@Valid @RequestBody ChamadoDto chamadoDto){
        ChamadoDto obj = new ChamadoDto(chamadoService.save(chamadoDto));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TECNICO')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ChamadoDto> update(@PathVariable Integer id, @Valid @RequestBody ChamadoDto chamadoDto){
        return new ResponseEntity<>(new ChamadoDto(chamadoService.update(id, chamadoDto)), HttpStatus.OK);
    }
}
