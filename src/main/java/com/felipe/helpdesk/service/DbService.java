package com.felipe.helpdesk.service;

import com.felipe.helpdesk.domain.Chamado;
import com.felipe.helpdesk.domain.Cliente;
import com.felipe.helpdesk.domain.Tecnico;
import com.felipe.helpdesk.domain.enums.Perfil;
import com.felipe.helpdesk.domain.enums.Prioridade;
import com.felipe.helpdesk.domain.enums.Status;
import com.felipe.helpdesk.repository.ChamadoRepository;
import com.felipe.helpdesk.repository.ClienteRepository;
import com.felipe.helpdesk.repository.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbService {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ChamadoRepository chamadoRepository;

    public void instanciaDb(){
        Tecnico tec1 = new Tecnico(null, "Felipe Bezerra", "73617594085", "felipe@mail.com", "123456");
        tec1.setPerfis(Perfil.ADMIN);

        Cliente cli1 = new Cliente(null, "Ricardo", "88645874032", "ricardo@mail.com", "654321");

        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ABERTO, "Teste", "Chamado de teste", tec1, cli1);

        tecnicoRepository.save(tec1);
        clienteRepository.save(cli1);
        chamadoRepository.save(c1);
    }
}
