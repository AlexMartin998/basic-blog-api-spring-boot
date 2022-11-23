package com.alex.blog.auth.provider;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtTokenProvider {
    
    // @Value("${app.jwt-secret}") // get a value of app.properties
    // private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;


    public static final SecretKey SECRET_KEY = Keys
    .hmacShaKeyFor("asjnasdASJ33NA93m1aD121212323123123123123123123".getBytes());



    public String generateToken(Authentication authentication) {

        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        String jwt = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expirationDate)
                .signWith(SECRET_KEY).compact();

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
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(jwt)
                .getBody();

        return claims;
    }



    // private String resolveBearer(String jwt) {
    //     if (jwt != null && jwt.startsWith("Bearer "))
    //         return jwt.replace("Bearer ", "");

    //     return null;
    // }
}
