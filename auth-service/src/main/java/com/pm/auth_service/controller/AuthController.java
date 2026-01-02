package com.pm.auth_service.controller;

import com.pm.auth_service.dto.LoginDtoRequest;
import com.pm.auth_service.dto.LoginResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDtoRequest loginDtoRequest) {
        Optional<String> tokenOptinal = authService.authenticate(loginDtoRequest);
        if (tokenOptinal.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = tokenOptinal.get();

        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
