package com.felipe.helpdesk.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.felipe.helpdesk.domain.Tecnico;
import com.felipe.helpdesk.domain.enums.Perfil;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class TecnicoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Integer id;
    protected String nome;
    protected String cpf;
    protected String email;
    protected String senha;
    protected Set<Integer> perfis = new HashSet<>();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    protected Instant dataCriacao = Instant.now();

    public TecnicoDto(Tecnico tecnico) {
        this.id = tecnico.getId();
        this.nome = tecnico.getNome();
        this.cpf = tecnico.getCpf();
        this.email = tecnico.getEmail();
        this.senha = tecnico.getSenha();
        this.perfis = tecnico.getPerfis().stream().map(Perfil::getCodigo).collect(Collectors.toSet());
        this.dataCriacao = tecnico.getDataCriacao();
    }

    // Método transforma cada Integer da lista de perfis em um tipo enumerado Perfil
    public Set<Perfil> getPerfis() {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    // Por causa do Lombok, o método permaneceu como 'setPerfis()', mas o que ele faz é
    // adicionar um tipo Perfil na lista de perfis do usuario, a nomeclatura mais
    // aproriada seria 'addPerfil()'
    public void setPerfis(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }
}
