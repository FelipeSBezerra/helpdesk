package com.felipe.helpdesk.controller;

import com.felipe.helpdesk.domain.dto.TecnicoDto;
import com.felipe.helpdesk.domain.dto.TecnicoResponseDto;
import com.felipe.helpdesk.service.TecnicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENTE', 'ROLE_TECNICO')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoResponseDto> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(tecnicoService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENTE', 'ROLE_TECNICO')")
    @GetMapping
    public ResponseEntity<List<TecnicoResponseDto>> findAll() {
        return new ResponseEntity<>(tecnicoService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<TecnicoDto> create(@Valid @RequestBody TecnicoDto tecnicoDto){
        TecnicoDto obj = new TecnicoDto(tecnicoService.save(tecnicoDto));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TecnicoResponseDto> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDto tecnicoDto){
        return new ResponseEntity<>(tecnicoService.update(id, tecnicoDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        tecnicoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
