package com.felipe.helpdesk.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.felipe.helpdesk.domain.Tecnico;
import com.felipe.helpdesk.domain.enums.Perfil;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class TecnicoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Integer id;
    @NotBlank(message = "O campo NOME é requerido!")
    protected String nome;
    @NotBlank(message = "O campo CPF é requerido!")
    @CPF
    protected String cpf;
    @NotBlank(message = "O campo EMAIL é requerido!")
    protected String email;
    @NotBlank(message = "O campo SENHA é requerido!")
    protected String senha;
    protected Set<Integer> perfis = new HashSet<>();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    protected Instant dataCriacao = Instant.now();

    public TecnicoDto(){
        addPerfis(Perfil.TECNICO);
    }

    public TecnicoDto(Tecnico tecnico) {
        this.id = tecnico.getId();
        this.nome = tecnico.getNome();
        this.cpf = tecnico.getCpf();
        this.email = tecnico.getEmail();
        this.senha = tecnico.getSenha();
        this.perfis = tecnico.getPerfis().stream().map(Perfil::getCodigo).collect(Collectors.toSet());
        this.dataCriacao = tecnico.getDataCriacao();
        addPerfis(Perfil.TECNICO);
    }

    // Método transforma cada Integer da lista de perfis em um tipo enumerado Perfil
    public Set<Perfil> getPerfis() {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public void addPerfis(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }
}
