package com.felipe.helpdesk.controller;

import com.felipe.helpdesk.domain.dto.ClienteDto;
import com.felipe.helpdesk.service.ClienteService;
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
@RequestMapping(value = "/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENTE', 'ROLE_TECNICO')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDto> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(new ClienteDto(clienteService.findById(id)), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENTE', 'ROLE_TECNICO')")
    @GetMapping
    public ResponseEntity<List<ClienteDto>> findAll() {
        return new ResponseEntity<>(clienteService.findAll().stream().map(ClienteDto::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ClienteDto> create(@Valid @RequestBody ClienteDto clienteDto){
        ClienteDto obj = new ClienteDto(clienteService.save(clienteDto));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDto> update(@PathVariable Integer id, @Valid @RequestBody ClienteDto clienteDto){
        return new ResponseEntity<>(new ClienteDto(clienteService.update(id, clienteDto)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        clienteService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
