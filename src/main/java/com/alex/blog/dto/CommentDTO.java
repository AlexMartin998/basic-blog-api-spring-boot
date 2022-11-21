package com.alex.blog.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CommentDTO {

    private long id;

    @NotEmpty(message = "Name is required!")
    private String name;

    @NotEmpty(message = "Email is required!")
    @Email(message = "Invalid email!")
    private String email;

    @NotEmpty
    @Size(min = 3, message = "Size must be greater than 3 char")
    private String body;

    

    public CommentDTO() {}

    

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }

    
}
