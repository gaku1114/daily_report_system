package com.techacademy.service;

import org.springframework.stereotype.Service;

import com.techacademy.entity.Authentication;
import com.techacademy.repository.AuthenticationRepository;


@Service
public class AuthenticationService {
    private final AuthenticationRepository authenticationRepository;

    public AuthenticationService(AuthenticationRepository repository) {
        this.authenticationRepository = repository;
    }

    public Authentication saveAuthentication(Authentication authentication) {
        return authenticationRepository.save(authentication);
    }
}
