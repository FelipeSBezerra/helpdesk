package com.felipe.helpdesk.service;

import com.felipe.helpdesk.domain.Pessoa;
import com.felipe.helpdesk.domain.Tecnico;
import com.felipe.helpdesk.domain.dto.TecnicoDto;
import com.felipe.helpdesk.repository.PessoaRepository;
import com.felipe.helpdesk.repository.TecnicoRepository;
import com.felipe.helpdesk.service.exception.DataIntegrityViolationException;
import com.felipe.helpdesk.service.exception.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Tecnico findById(Integer id) {
        return tecnicoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Não existe um técnico com o id " + id + " na base de dados"));
    }

    public List<Tecnico> findAll() {
        return tecnicoRepository.findAll();
    }

    public Tecnico save(TecnicoDto tecnicoDto) {
        validaPorCpfEEmail(tecnicoDto);
        tecnicoDto.setId(null);
        Tecnico obj = new Tecnico(tecnicoDto);
        return tecnicoRepository.save(obj);
    }

    public Tecnico update(Integer id, @Valid TecnicoDto tecnicoDto) {
        tecnicoDto.setId(id);
        Tecnico obj = findById(id);
        validaPorCpfEEmail(tecnicoDto);
        Tecnico tecnicoAtualizado = new Tecnico(tecnicoDto);
        tecnicoAtualizado.setDataCriacao(obj.getDataCriacao());
        return tecnicoRepository.save(tecnicoAtualizado);
    }

    private void validaPorCpfEEmail(TecnicoDto tecnicoDto) {
        Optional<Pessoa> objCpf = pessoaRepository.findByCpf(tecnicoDto.getCpf());
        if (objCpf.isPresent() && objCpf.get().getId() != tecnicoDto.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado!");
        }

        Optional<Pessoa> objEmail = pessoaRepository.findByEmail(tecnicoDto.getEmail());
        if (objEmail.isPresent() && objEmail.get().getId() != tecnicoDto.getId()) {
            throw new DataIntegrityViolationException("Email já cadastrado!");
        }
    }

    public void deleteById(Integer id){
        Tecnico obj = findById(id);
        if (obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado!");
        }
        tecnicoRepository.deleteById(id);
    }
}
