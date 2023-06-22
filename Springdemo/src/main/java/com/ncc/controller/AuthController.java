package com.ncc.controller;

import com.ncc.configuration.authrequest.LoginRequest;
import com.ncc.configuration.authrequest.SignUpRequest;
import com.ncc.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final IAuthService authService;

    @PostConstruct
    public void init(){
        System.out.println(authService);
        System.out.println(message);
    }

    @Value("${message}")
    private String message;

    @PostMapping("/signup")
    @PreAuthorize("hasA")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest){
        return authService.registerUser(signUpRequest);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        return authService.authenticateUser(loginRequest);
    }
}
// TODO: