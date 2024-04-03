package com.schoolmanagement.poc.service;

import com.schoolmanagement.poc.repository.entities.UserEntity;
import com.schoolmanagement.poc.service.interfaces.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService implements IJwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String generateToken(UserEntity userEntity) {
        return Jwts.builder()
                .subject(userEntity.getEmail())
                .issuedAt(new java.util.Date(System.currentTimeMillis()))
                .expiration(new java.util.Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSigninKey())
                .compact();
    }

    @Override
    public String generateRefreshToken(Map<String, Object> extraClaims, UserEntity userEntity) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userEntity.getEmail())
                .issuedAt(new java.util.Date(System.currentTimeMillis()))
                .expiration(new java.util.Date(System.currentTimeMillis() + 1000 * 60 * 180))
                .signWith(getSigninKey())
                .compact();
    }

    @Override
    public  <T> T extractClaim(String token, Function<Claims, T> claimResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimResolvers.apply(claims);
    }

    @Override
    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public Key getSigninKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    @Override
    public boolean isTokenExpired(String token, UserDetails userDetails) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token, userDetails);
    }
}
