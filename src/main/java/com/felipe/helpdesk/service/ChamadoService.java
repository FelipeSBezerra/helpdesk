package com.felipe.helpdesk.service;

import com.felipe.helpdesk.domain.Chamado;
import com.felipe.helpdesk.domain.Cliente;
import com.felipe.helpdesk.domain.Tecnico;
import com.felipe.helpdesk.domain.dto.ChamadoDto;
import com.felipe.helpdesk.domain.enums.Prioridade;
import com.felipe.helpdesk.domain.enums.Status;
import com.felipe.helpdesk.repository.ChamadoRepository;
import com.felipe.helpdesk.service.exception.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final TecnicoService tecnicoService;
    private final ClienteService clienteService;

    public Chamado findById(Integer id) {
        return chamadoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Não existe um chamado com o id " + id + " na base de dados"));
    }

    public List<Chamado> findAll() {
        return chamadoRepository.findAll();
    }

    public Chamado save(ChamadoDto chamadoDto){
        return chamadoRepository.save(newChamado(chamadoDto));
    }

    public Chamado update(Integer id, ChamadoDto chamadoDto){
        Chamado obj = findById(id);
        if (obj.getId() != chamadoDto.getId()){
            chamadoDto.setId(id);
        }
        Chamado chamadoAtualizado = newChamado(chamadoDto);
        chamadoAtualizado.setDataAbertura(obj.getDataAbertura());

        return chamadoRepository.save(chamadoAtualizado);
    }

    // Metodo que transforma um ChamadoDto em Chamado para que seja salvo na base de dados
    private Chamado newChamado(ChamadoDto chamadoDto) {
        Tecnico tecnico = tecnicoService.findById(chamadoDto.getTecnico());
        Cliente cliente = clienteService.findById(chamadoDto.getCliente());

        Chamado chamado = new Chamado();
        if (chamadoDto.getId() != null){
            chamado.setId(chamadoDto.getId());
        }

        // Se o Status do chamado for alterado para fechado, seta a data de fechamento da chamada.
        if (chamadoDto.getStatus().equals(2)){
            chamado.setDataFechamento(Instant.now());
        }

        chamado.setTitulo(chamadoDto.getTitulo());
        chamado.setObservacoes(chamadoDto.getObservacoes());
        chamado.setPrioridade(Prioridade.toEnum(chamadoDto.getPrioridade()));
        chamado.setStatus(Status.toEnum(chamadoDto.getStatus()));
        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);

        return chamado;
    }
}
