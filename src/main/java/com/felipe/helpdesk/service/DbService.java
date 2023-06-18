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

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class DbService {

    private final TecnicoRepository tecnicoRepository;
    private final ClienteRepository clienteRepository;
    private final ChamadoRepository chamadoRepository;
    private final PasswordEncoder passwordEncoder;

    public void instanciaDb(){
        Tecnico tec1 = new Tecnico(null, "Felipe Bezerra", "680.637.540-50", "felipe@mail.com", passwordEncoder.encode("123456"));
        tec1.setPerfis(Perfil.ADMIN);
        tec1.setPerfis(Perfil.TECNICO);
        Tecnico tec2 = new Tecnico(null, "Gabriel Lima", "926.593.400-05", "gabriel@mail.com", passwordEncoder.encode("123456"));
        tec2.setPerfis(Perfil.TECNICO);
        Tecnico tec3 = new Tecnico(null, "Thiago Lima", "213.601.710-10", "thiago@mail.com", passwordEncoder.encode("123456"));
        tec3.setPerfis(Perfil.TECNICO);
        Tecnico tec4 = new Tecnico(null, "Ricardo Amorim", "065.316.590-09", "ricardo@mail.com", passwordEncoder.encode("123456"));
        tec4.setPerfis(Perfil.TECNICO);
        Tecnico tec5 = new Tecnico(null, "Gefferson Andrade", "102.727.580-00", "gefferson@mail.com", passwordEncoder.encode("123456"));
        tec5.setPerfis(Perfil.TECNICO);
        Tecnico tec6 = new Tecnico(null, "Rafael Cunha", "470.862.190-61", "rafaelcunha@mail.com", passwordEncoder.encode("123456"));
        tec6.setPerfis(Perfil.TECNICO);

        Cliente cli1 = new Cliente(null, "Andresa Oliveira", "380.326.610-69", "andresa@mail.com", passwordEncoder.encode("123456"));
        Cliente cli2 = new Cliente(null, "Jaylane Kalyne", "692.612.100-15", "kalyne@mail.com", passwordEncoder.encode("123456"));
        Cliente cli3 = new Cliente(null, "Nelciane Gomes", "447.086.770-57", "nelciane@mail.com", passwordEncoder.encode("123456"));
        Cliente cli4 = new Cliente(null, "Jocivam Dias", "789.964.190-01", "jocivam@mail.com", passwordEncoder.encode("123456"));
        Cliente cli5 = new Cliente(null, "Vinicius Macial", "290.531.080-41", "vinicius@mail.com", passwordEncoder.encode("123456"));
        Cliente cli6 = new Cliente(null, "Rafael Macial", "975.096.110-24", "rafaelmaciel@mail.com", passwordEncoder.encode("123456"));

        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ABERTO, "Teste", "Chamado de teste", tec1, cli1);
        Chamado c2 = new Chamado(null, Prioridade.BAIXA, Status.ANDAMENTO, "Teste2", "Chamado de teste 2", tec2, cli5);
        Chamado c3 = new Chamado(null, Prioridade.BAIXA, Status.ABERTO, "Teste3", "Chamado de teste 3", tec3, cli2);



        tecnicoRepository.saveAll(Arrays.asList(tec1, tec2, tec3, tec4, tec5, tec6));
        clienteRepository.saveAll(Arrays.asList(cli1, cli2, cli3, cli4, cli5, cli6));
        chamadoRepository.saveAll(Arrays.asList(c1, c2, c3));
    }
}
