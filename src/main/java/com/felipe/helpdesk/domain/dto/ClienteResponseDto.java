package com.felipe.helpdesk.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.felipe.helpdesk.domain.Cliente;
import com.felipe.helpdesk.domain.enums.Perfil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ClienteResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Integer id;
    protected String nome;
    protected String cpf;
    protected String email;
    protected Set<Integer> perfis = new HashSet<>();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    protected Instant dataCriacao = Instant.now();

    public ClienteResponseDto(){
        addPerfis(Perfil.CLIENTE);
    }

    public ClienteResponseDto(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.email = cliente.getEmail();
        this.perfis = cliente.getPerfis().stream().map(Perfil::getCodigo).collect(Collectors.toSet());
        this.dataCriacao = cliente.getDataCriacao();
        addPerfis(Perfil.CLIENTE);
    }

    // Método transforma cada Integer da lista de perfis em um tipo enumerado Perfil
    public Set<Perfil> getPerfis() {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public void addPerfis(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }
}
