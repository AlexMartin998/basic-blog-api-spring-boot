package com.alex.blog.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.alex.blog.entities.Comment;

public class PublicationDTO {

    private Long id;

    @NotEmpty(message = "Some custome message - not empty!")
    @Size(min = 3, message = "Some custome message")
    private String title;

    @NotEmpty
    @Size(min = 3, message = "Size must be greater than 3 char!")
    private String description;

    @NotEmpty
    private String content;

    private Set<Comment> comments;



    public PublicationDTO() {}

    public PublicationDTO(Long id, String title, String description, String content) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

}
