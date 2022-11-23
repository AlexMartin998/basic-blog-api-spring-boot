package com.alex.blog.services;

import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alex.blog.auth.provider.JwtTokenProvider;
import com.alex.blog.dto.JWTAuthResponseDTO;
import com.alex.blog.dto.LoginDTO;
import com.alex.blog.dto.RegisterDTO;
import com.alex.blog.dto.RegisterResponse;
import com.alex.blog.entities.Role;
import com.alex.blog.entities.Usuario;
import com.alex.blog.exceptions.BlogAppException;
import com.alex.blog.repositories.IRoleRepository;
import com.alex.blog.repositories.IUserRepository;


@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public JWTAuthResponseDTO login(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);


        // gen token
        String jwt = jwtTokenProvider.generateToken(authentication);

        return mapToJwtDTO(jwt);
    }


    @Override
    @Transactional
    public RegisterResponse registerNewUser(RegisterDTO registerDTO) {
        if (userRepository.existsByEmail(registerDTO.getEmail())
                || userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "User already registered!");
        }

        Usuario user = mapToEntity(registerDTO);

        Role roles = roleRepository.findOneByName("ROLE_USER").get();
        user.setAuthorities(Collections.singleton(roles));

        Usuario newUser = userRepository.save(user);

        return mapToDTO(newUser);
    }



    // DTO to entity
    private Usuario mapToEntity(RegisterDTO registerDTO) {
        Usuario newUser = modelMapper.map(registerDTO, Usuario.class);
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        return newUser;
    }

    // entity to DTO
    private RegisterResponse mapToDTO(Usuario usuario) {
        RegisterResponse newUserDTO = modelMapper.map(usuario, RegisterResponse.class);

        return newUserDTO;
    }

    private JWTAuthResponseDTO mapToJwtDTO(String token) {
        JWTAuthResponseDTO jwtAuthResponseDTO = new JWTAuthResponseDTO(token);

        return jwtAuthResponseDTO;
    }

}
