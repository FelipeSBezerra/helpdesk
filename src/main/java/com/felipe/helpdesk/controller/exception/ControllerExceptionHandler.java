package com.felipe.helpdesk.controller.exception;

import com.felipe.helpdesk.service.exception.DataIntegrityViolationException;
import com.felipe.helpdesk.service.exception.IllegalArgumentException;
import com.felipe.helpdesk.service.exception.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * Trata excecao de objeto nao encontrado
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Object not found";
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(err, status);
    }

    /**
     * Trata excecao de violacao de integridade do banco de dados
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        String error = "Data integrity violation";
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(err, status);
    }

    /**
     * Trata excecao de argumento invalido
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> IillegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Illegal Argument";
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(err, status);
    }

    /**
     * Trata excecao de validacao dos campos marcados com @Valid
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationError(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Method argument not valid";
        ValidationError err = new ValidationError(Instant.now(), status.value(), error, "Erro na validacao dos campos", request.getRequestURI());

        // Metodo para pegar todos os erros de validacao informado pelo MethodArgumentNotValidException e
        // adiciona na lista de errors de ValidationError.
        for (FieldError x : e.getBindingResult().getFieldErrors()) {
            err.addError(x.getField(), x.getDefaultMessage());
        }
        return new ResponseEntity<>(err, status);
    }

    /**
     * Trata excecoes de autorizacao
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<StandardError> accessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        String error = "Forbidden";
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(err, status);
    }

    /**
     * Trata excecao de credenciais invalidas
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StandardError> badCredentialsException(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String error = "Unauthorized";
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(err, status);
    }

    /**
     * Trata excecao de token JWT invalido
     */
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<StandardError> expiredJwtException(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String error = "Unauthorized";
        StandardError err = new StandardError(Instant.now(), status.value(), error, "Acesso negado!", request.getRequestURI());
        return new ResponseEntity<>(err, status);
    }

    /**
     *  Trata qualquer outra excecao nao especificada
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> exception(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String error = "Internal server error";
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(err, status);
    }
}
