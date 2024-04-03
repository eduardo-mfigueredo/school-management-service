package com.schoolmanagement.poc.service;

import com.schoolmanagement.poc.model.request.RefreshTokenRequest;
import com.schoolmanagement.poc.model.request.SignInRequest;
import com.schoolmanagement.poc.model.response.SignInResponse;
import com.schoolmanagement.poc.repository.UserRepository;
import com.schoolmanagement.poc.repository.entities.UserEntity;
import com.schoolmanagement.poc.service.interfaces.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
        );

        UserEntity userEntity = userRepository.findByEmail(signInRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid user or password"));

        String jwt = jwtService.generateToken(userEntity);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), userEntity);

        return SignInResponse.builder()
                .success(true)
                .message("Sign in successful")
                .token(jwt)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public SignInResponse refreshToken(RefreshTokenRequest refreshToken) {
        String username = jwtService.extractUsername(refreshToken.getToken());
        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Invalid user"));

        if (!jwtService.isTokenValid(refreshToken.getToken(), userEntity)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token");
        }

        String jwt = jwtService.generateToken(userEntity);

        return SignInResponse.builder()
                .success(true)
                .message("Token refreshed")
                .token(jwt)
                .refreshToken(refreshToken.getToken())
                .build();
    }

}