package com.example.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret:mySecretKeyForJWTTokenGeneration12345678901234567890}")
    private String secret;
    
    @Value("${jwt.expiration:86400000}") // 24 horas
    private Long expiration;
    
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    
    public String generateToken(String correo, String rol) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        
        return Jwts.builder()
                .subject(correo)
                .claim("rol", rol)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }
    
    public String extractCorreo(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    public String extractRol(String token) {
        return extractClaim(token, claims -> claims.get("rol", String.class));
    }
    
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    public Boolean validateToken(String token, String correo) {
        final String tokenCorreo = extractCorreo(token);
        return (tokenCorreo.equals(correo) && !isTokenExpired(token));
    }
}

