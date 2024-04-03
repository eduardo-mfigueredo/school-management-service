package com.schoolmanagement.poc.service.interfaces;

import com.schoolmanagement.poc.repository.entities.UserEntity;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Map;
import java.util.function.Function;

public interface IJwtService {

    String generateToken(UserEntity userEntity);
    String generateRefreshToken(Map<String, Object> extraClaims, UserEntity userEntity);
    <T> T extractClaim(String token, Function<Claims, T> claimResolvers);
    String extractUsername(String token);
    Claims extractAllClaims(String token);
    Key getSigninKey();
    boolean isTokenExpired(String token, UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);

}