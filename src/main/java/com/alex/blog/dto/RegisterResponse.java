package com.alex.blog.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class RegisterResponse {

    @NotEmpty
    private String name;

    @NotEmpty
    private String username;

    @NotEmpty
    @Email
    private String email;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
