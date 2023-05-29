package com.felipe.helpdesk.service;

import com.felipe.helpdesk.domain.Pessoa;
import com.felipe.helpdesk.domain.Cliente;
import com.felipe.helpdesk.domain.dto.ClienteDto;
import com.felipe.helpdesk.repository.PessoaRepository;
import com.felipe.helpdesk.repository.ClienteRepository;
import com.felipe.helpdesk.service.exception.DataIntegrityViolationException;
import com.felipe.helpdesk.service.exception.ObjectNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClienteService {

    private ClienteRepository clienteRepository;
    private PessoaRepository pessoaRepository;

    public Cliente findById(Integer id) {
        return clienteRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Não existe um cliente com o id " + id + " na base de dados"));
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente save(ClienteDto clienteDto) {
        validaPorCpfEEmail(clienteDto);
        clienteDto.setId(null);
        Cliente obj = new Cliente(clienteDto);
        return clienteRepository.save(obj);
    }

    public Cliente update(Integer id, @Valid ClienteDto clienteDto) {
        clienteDto.setId(id);
        Cliente obj = findById(id);
        validaPorCpfEEmail(clienteDto);
        Cliente clienteAtualizado = new Cliente(clienteDto);
        clienteAtualizado.setDataCriacao(obj.getDataCriacao());
        return clienteRepository.save(clienteAtualizado);
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
        Cliente obj = findById(id);
        if (obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
        }
        clienteRepository.deleteById(id);
    }
}
