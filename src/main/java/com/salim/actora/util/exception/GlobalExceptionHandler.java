package com.salim.actora.util.exception;

import com.salim.actora.util.ResponseBuilder;
import io.jsonwebtoken.JwtException;
import kotlin.io.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialException(BadCredentialsException e){

       String errorMsg = e.getLocalizedMessage();

       Map<String,String> errorBody = new HashMap<>();

       errorBody.put("error",errorMsg);

       return ResponseBuilder.build(null,errorBody, HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> usernameNotFoundException(UsernameNotFoundException e){
        String errorMsg = e.getLocalizedMessage();

        Map<String,String> errorBody = new HashMap<>();

        errorBody.put("error",errorMsg);

        return ResponseBuilder.build(null,errorBody, HttpStatus.NOT_FOUND, LocalDateTime.now());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> authenticationException(AuthenticationException e){
        String errorMsg = e.getLocalizedMessage();

        Map<String,String> errorBody = new HashMap<>();

        errorBody.put("error",errorMsg);

        return ResponseBuilder.build(null,errorBody, HttpStatus.BAD_REQUEST, LocalDateTime.now());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> jwtException(JwtException e){
        String errorMsg = e.getLocalizedMessage();

        Map<String,String> errorBody = new HashMap<>();

        errorBody.put("error",errorMsg);

        return ResponseBuilder.build(null,errorBody, HttpStatus.UNAUTHORIZED, LocalDateTime.now());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedException(AccessDeniedException e){
        String errorMsg = e.getLocalizedMessage();

        Map<String,String> errorBody = new HashMap<>();

        errorBody.put("error",errorMsg);

        return ResponseBuilder.build(null,errorBody, HttpStatus.FORBIDDEN, LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException e){

        Map<String,String> errorBody = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(fieldError ->
               errorBody.put(fieldError.getField(),fieldError.getDefaultMessage())
                );
        return ResponseBuilder.build(null,errorBody, HttpStatus.FORBIDDEN, LocalDateTime.now());

    }
}
