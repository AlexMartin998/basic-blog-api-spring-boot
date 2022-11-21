package com.alex.blog.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alex.blog.dto.CommentDTO;



@Service
public interface ICommentService {

    public CommentDTO createComment(long publicationId, CommentDTO commentDTO);

    public List<CommentDTO> getAllCommentsByPublicationId(long publicationId);


    public CommentDTO getCommentById(long publicationId, long id);

    public CommentDTO updateComment(Long publicationId, Long id, CommentDTO commentDTO);

}
