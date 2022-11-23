package com.alex.blog.controllers;

import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alex.blog.auth.provider.JwtTokenProvider;
import com.alex.blog.dto.JWTAuthResponseDTO;
import com.alex.blog.dto.LoginDTO;
import com.alex.blog.dto.RegisterDTO;
import com.alex.blog.entities.Role;
import com.alex.blog.entities.Usuario;
import com.alex.blog.repositories.IRoleRepository;
import com.alex.blog.repositories.IUserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;



    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // obtenemos el token del provider
        String jwt = jwtTokenProvider.generateToken(authentication);


        // return new ResponseEntity<>("You have successfully logged in!", HttpStatus.OK);
        return ResponseEntity.ok(new JWTAuthResponseDTO(jwt));
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@Valid @RequestBody RegisterDTO registerDTO) {

        if (userRepository.existsByEmail(registerDTO.getEmail())
                || userRepository.existsByUsername(registerDTO.getUsername())) {
            return new ResponseEntity<>("User already registered!", HttpStatus.BAD_REQUEST);
        }

        Usuario user = new Usuario();
        user.setName(registerDTO.getName());
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Role roles = roleRepository.findOneByName("ROLE_USER").get();
        user.setAuthorities(Collections.singleton(roles));

        userRepository.save(user);


        return new ResponseEntity<>("User has been susccessfully created!", HttpStatus.CREATED);
    }

}
