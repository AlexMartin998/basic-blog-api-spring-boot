package com.alex.blog.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    
    // Errores de un user NO auth   -   error de q NO esta auth
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
                
        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());

        // // build the response Body
        Map<String, Object> body = new HashMap<>();
        body.put("message", authException.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        
    }
    
}
