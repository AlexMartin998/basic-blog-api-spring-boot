package com.alex.blog.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alex.blog.dto.JWTAuthResponseDTO;
import com.alex.blog.dto.LoginDTO;
import com.alex.blog.dto.RegisterDTO;
import com.alex.blog.dto.RegisterResponse;
import com.alex.blog.services.IAuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IAuthService authServiceImpl;


    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {
        
        return new ResponseEntity<>(authServiceImpl.login(loginDTO), HttpStatus.CREATED);
    }


    @PostMapping("/signup")
    public ResponseEntity<RegisterResponse> signupUser(@Valid @RequestBody RegisterDTO registerDTO) {

        return new ResponseEntity<>(authServiceImpl.registerNewUser(registerDTO), HttpStatus.CREATED);
    }

}
