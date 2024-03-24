package com.felipe.helpdesk.service;

import com.felipe.helpdesk.domain.Pessoa;
import com.felipe.helpdesk.domain.Tecnico;
import com.felipe.helpdesk.domain.dto.TecnicoDto;
import com.felipe.helpdesk.domain.dto.TecnicoResponseDto;
import com.felipe.helpdesk.repository.PessoaRepository;
import com.felipe.helpdesk.repository.TecnicoRepository;
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
public class TecnicoService {

    private final TecnicoRepository tecnicoRepository;
    private final PessoaRepository pessoaRepository;
    private final PasswordEncoder passwordEncoder;

    public TecnicoResponseDto findById(Integer id) {
        Tecnico tecnico = findByIdReturnEntity(id);
        return new TecnicoResponseDto(tecnico);
    }

    public Tecnico findByIdReturnEntity(Integer id) {
        return tecnicoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Não existe um técnico com o id " + id + " na base de dados"));
    }

    public List<TecnicoResponseDto> findAll() {
        return tecnicoRepository.findAll().stream().map(TecnicoResponseDto::new).toList();
    }

    public Tecnico save(TecnicoDto tecnicoDto) {
        validaPorCpfEEmail(tecnicoDto);
        tecnicoDto.setId(null);
        tecnicoDto.setSenha(passwordEncoder.encode(tecnicoDto.getSenha()));
        tecnicoDto.setDataCriacao(Instant.now());
        Tecnico obj = new Tecnico(tecnicoDto);
        return tecnicoRepository.save(obj);
    }

    public TecnicoResponseDto update(Integer id, @Valid TecnicoDto tecnicoDto) {
        tecnicoDto.setId(id);
        Tecnico obj = findByIdReturnEntity(id);
        validaPorCpfEEmail(tecnicoDto);
        Tecnico tecnicoAtualizado = new Tecnico(tecnicoDto);
        tecnicoAtualizado.setSenha(passwordEncoder.encode(tecnicoAtualizado.getSenha()));
        tecnicoAtualizado.setDataCriacao(obj.getDataCriacao());
        return new TecnicoResponseDto(tecnicoRepository.save(tecnicoAtualizado));
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
        Tecnico obj = findByIdReturnEntity(id);
        if (obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado!");
        }
        tecnicoRepository.deleteById(id);
    }
}
