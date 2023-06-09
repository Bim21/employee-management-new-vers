package com.ncc.service;

import com.ncc.configuration.authrequest.LoginRequest;
import com.ncc.configuration.authrequest.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<?> registerUser(SignUpRequest signUpRequest);
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
}
