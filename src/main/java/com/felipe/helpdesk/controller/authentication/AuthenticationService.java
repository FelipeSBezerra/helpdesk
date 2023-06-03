package com.felipe.helpdesk.controller.authentication;

import com.felipe.helpdesk.domain.Pessoa;
import com.felipe.helpdesk.repository.PessoaRepository;
import com.felipe.helpdesk.security.JwtService;
import com.felipe.helpdesk.security.UserSS;
import com.felipe.helpdesk.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PessoaRepository pessoaRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationRespose login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getSenha()

                )
        );
        Pessoa user = pessoaRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));

        UserSS userSS = new UserSS(user.getId(), user.getEmail(), user.getSenha(), user.getPerfis());
        String jwtToken = jwtService.generateToken(userSS);

        return new AuthenticationRespose(jwtToken);
    }
}
