package br.com.seplag.edital.controller;

import br.com.seplag.edital.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @PostMapping("/token")
    public ResponseEntity<String> generateToken() {
        String token = authenticationInterceptor.generateToken();
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}