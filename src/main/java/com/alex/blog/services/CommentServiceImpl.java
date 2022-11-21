package com.alex.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alex.blog.dto.CommentDTO;
import com.alex.blog.entities.Comment;
import com.alex.blog.entities.Publication;
import com.alex.blog.exceptions.BlogAppException;
import com.alex.blog.exceptions.ResourceNotFoundExcepion;
import com.alex.blog.repositories.ICommentRepository;
import com.alex.blog.repositories.IPublicationRepository;


@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private ICommentRepository commentRepository;

    @Autowired
    private IPublicationRepository publicationRepository;



    @Override
    public CommentDTO createComment(long publicationId, CommentDTO commentDTO) {
        Comment comment = maptToEntity(commentDTO);
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new ResourceNotFoundExcepion("Publication", "ID", publicationId));

        comment.setPublication(publication);

        Comment newComment = commentRepository.save(comment);

        return mapToDTO(newComment);
    }


    @Override
    public List<CommentDTO> getAllCommentsByPublicationId(long publicationId) {
        publicationRepository.findById(publicationId)
                .orElseThrow(() -> new ResourceNotFoundExcepion("Publication", "ID", publicationId));

        List<Comment> comments = commentRepository.findByPublicationId(publicationId);

        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }


    @Override
    public CommentDTO getCommentById(long publicationId, long id) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new ResourceNotFoundExcepion("Publication", "ID", publicationId));

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcepion("Comment", "ID", id));

        // verify that the comment belongs to the publication 
        if (!comment.getPublication().getId().equals(publication.getId()))
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Comment does not belong to the publication!");

        
        return mapToDTO(comment);
    }

    
    @Override
    public CommentDTO updateComment(Long publicationId, Long id, CommentDTO commentDTO) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new ResourceNotFoundExcepion("Publication", "ID", publicationId));

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcepion("Comment", "ID", id));

        // verify that the comment belongs to the publication
        if (!comment.getPublication().getId().equals(publication.getId()))
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Comment does not belong to the publication!");

        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());

        // persist in DB
        Comment updatedComment = commentRepository.save(comment);


        return mapToDTO(updatedComment);
    }

    


    // DTO to entity
    private Comment maptToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        
        comment.setId(commentDTO.getId());
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());

        return comment;
    }
    

    // entity to DTO
    private CommentDTO mapToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId(comment.getId());
        commentDTO.setName(comment.getName());
        commentDTO.setEmail(comment.getEmail());
        commentDTO.setBody(comment.getBody());

        return commentDTO;
    }










    
}
