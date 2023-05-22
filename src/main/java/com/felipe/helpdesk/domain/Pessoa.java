package com.felipe.helpdesk.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.felipe.helpdesk.domain.enums.Perfil;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
public abstract class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    protected String nome;
    @Column(unique = true)
    protected String cpf;
    @Column(unique = true)
    protected String email;
    protected String senha;
    @ElementCollection(fetch = FetchType.EAGER) // garante que a lista de perfis seja enviada imediatamente junto com o Usuario
    @CollectionTable(name = "PERFIS")
    protected Set<Integer> perfis = new HashSet<>();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss'Z", timezone = "GMT")
    protected Instant dataCriacao = Instant.now();

    public Pessoa(){
        setPerfis(Perfil.CLIENTE);
    }

    public Pessoa(Integer id, String nome, String cpf, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        setPerfis(Perfil.CLIENTE);
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
