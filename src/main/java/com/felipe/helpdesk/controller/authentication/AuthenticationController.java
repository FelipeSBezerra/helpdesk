package com.felipe.helpdesk.controller.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthenticationRespose> login(@RequestBody AuthenticationRequest request) {
        AuthenticationRespose response = authenticationService.login(request);
        return ResponseEntity.ok()
                .header("Authentication", response.getTokenJwt())
                .body(response);
    }
}