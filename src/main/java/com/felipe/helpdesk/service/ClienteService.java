package com.felipe.helpdesk.service;

import com.felipe.helpdesk.domain.Cliente;
import com.felipe.helpdesk.domain.Pessoa;
import com.felipe.helpdesk.domain.dto.ClienteDto;
import com.felipe.helpdesk.domain.dto.ClienteResponseDto;
import com.felipe.helpdesk.repository.ClienteRepository;
import com.felipe.helpdesk.repository.PessoaRepository;
import com.felipe.helpdesk.service.exception.DataIntegrityViolationException;
import com.felipe.helpdesk.service.exception.ObjectNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PessoaRepository pessoaRepository;
    private final PasswordEncoder passwordEncoder;


    public ClienteResponseDto findById(Integer id) {
        Cliente cliente = findByIdReturnEntity(id);
        return new ClienteResponseDto(cliente);
    }

    public Cliente findByIdReturnEntity(Integer id) {
        return clienteRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Não existe um cliente com o id " + id + " na base de dados"));
    }

    public List<ClienteResponseDto> findAll() {
        return clienteRepository.findAll().stream().map(ClienteResponseDto::new).toList();
    }

    public Cliente save(ClienteDto clienteDto) {
        validaPorCpfEEmail(clienteDto);
        clienteDto.setId(null);
        clienteDto.setSenha(passwordEncoder.encode(clienteDto.getSenha()));
        clienteDto.setDataCriacao(Instant.now());
        Cliente obj = new Cliente(clienteDto);
        return clienteRepository.save(obj);
    }

    public ClienteResponseDto update(Integer id, @Valid ClienteDto clienteDto) {
        clienteDto.setId(id);
        Cliente obj = findByIdReturnEntity(id);
        validaPorCpfEEmail(clienteDto);
        Cliente clienteAtualizado = new Cliente(clienteDto);
        clienteAtualizado.setDataCriacao(obj.getDataCriacao());
        clienteAtualizado.setSenha(passwordEncoder.encode(clienteAtualizado.getSenha()));
        return new ClienteResponseDto(clienteRepository.save(clienteAtualizado));
    }

    private void validaPorCpfEEmail(ClienteDto clienteDto) {
        Optional<Pessoa> objCpf = pessoaRepository.findByCpf(clienteDto.getCpf());
        if (objCpf.isPresent() && objCpf.get().getId() != clienteDto.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado!");
        }

        Optional<Pessoa> objEmail = pessoaRepository.findByEmail(clienteDto.getEmail());
        if (objEmail.isPresent() && objEmail.get().getId() != clienteDto.getId()) {
            throw new DataIntegrityViolationException("Email já cadastrado!");
        }
    }

    public void deleteById(Integer id){
        Cliente obj = findByIdReturnEntity(id);
        if (obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
        }
        clienteRepository.deleteById(id);
    }
}
