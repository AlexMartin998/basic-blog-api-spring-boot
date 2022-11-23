package com.alex.blog.services;

import java.util.List;

import com.alex.blog.dto.CommentDTO;

public interface ICommentService {

    public CommentDTO createComment(long publicationId, CommentDTO commentDTO);

    public List<CommentDTO> getAllCommentsByPublicationId(long publicationId);


    public CommentDTO getCommentById(long publicationId, long id);

    public CommentDTO updateComment(Long publicationId, Long id, CommentDTO commentDTO);

    public void deleteComment(Long publicationId, Long id);

}
