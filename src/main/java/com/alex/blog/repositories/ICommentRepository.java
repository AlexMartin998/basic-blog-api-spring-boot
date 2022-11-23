package com.alex.blog.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.alex.blog.entities.Comment;


public interface ICommentRepository extends CrudRepository<Comment, Long> {
    
    public List<Comment> findByPublicationId(long publicationId);

}
