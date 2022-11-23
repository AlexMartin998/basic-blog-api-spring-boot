package com.alex.blog.services;

import com.alex.blog.dto.JWTAuthResponseDTO;
import com.alex.blog.dto.LoginDTO;
import com.alex.blog.dto.RegisterDTO;
import com.alex.blog.dto.RegisterResponse;


public interface IAuthService {

    public JWTAuthResponseDTO login(LoginDTO loginDTO);

    public RegisterResponse registerNewUser(RegisterDTO registerDTO);
}
