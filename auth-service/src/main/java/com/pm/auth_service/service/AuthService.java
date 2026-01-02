package com.pm.auth_service.service;

import com.pm.auth_service.dto.LoginDtoRequest;
import com.pm.auth_service.model.Users;
import com.pm.auth_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<String> authenticate(LoginDtoRequest loginDtoRequest) {
        Optional<Users> foundUser = userRepository.findByEmail(loginDtoRequest.getEmail());


    }
}
