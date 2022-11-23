package com.alex.blog.auth.provider;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtTokenProvider {
    
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;


    public String generateToken(Authentication authentication) {

        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        String jwt = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes())).compact();

        return jwt;
    }


    public String getUsernameFromJwt(String jwt) {

        return getClaims(jwt).getSubject();
    }


    public boolean validateToken(String jwt) {

        try {
            getClaims(jwt);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }

    }


    public Claims getClaims(String jwt) {
        Claims claims = Jwts.parserBuilder().setSigningKey(jwtSecret.getBytes()).build()
                .parseClaimsJws(jwt)
                .getBody();

        return claims;
    }

}
