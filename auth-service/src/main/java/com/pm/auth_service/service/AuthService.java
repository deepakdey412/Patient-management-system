package com.pm.auth_service.service;

import com.pm.auth_service.dto.LoginDtoRequest;
import com.pm.auth_service.model.Users;
import com.pm.auth_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<String> authenticate(LoginDtoRequest loginDtoRequest) {
        Optional<String> token = userRepository.findByEmail(loginDtoRequest.getEmail())
                .filter(u-> passwordEncoder.matches(loginDtoRequest.getPassword(), u.getPassword()))
                        .map(u-> jwtUtil.generateToken(u.getEmail() , u.getRole())));

        return token;
    }
}
