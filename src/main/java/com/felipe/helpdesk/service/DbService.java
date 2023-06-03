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
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbService {

    private final TecnicoRepository tecnicoRepository;
    private final ClienteRepository clienteRepository;
    private final ChamadoRepository chamadoRepository;
    private final PasswordEncoder passwordEncoder;

    public void instanciaDb(){
        Tecnico tec1 = new Tecnico(null, "Felipe Bezerra", "73617594085", "felipe@mail.com", passwordEncoder.encode("123456"));
        tec1.setPerfis(Perfil.ADMIN);
        tec1.setPerfis(Perfil.TECNICO);

        Cliente cli1 = new Cliente(null, "Ricardo", "88645874032", "ricardo@mail.com", passwordEncoder.encode("123456"));

        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ABERTO, "Teste", "Chamado de teste", tec1, cli1);

        tecnicoRepository.save(tec1);
        clienteRepository.save(cli1);
        chamadoRepository.save(c1);
    }
}
