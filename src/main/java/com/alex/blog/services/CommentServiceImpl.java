package com.alex.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ModelMapper modelMapper;

    @Autowired
    private ICommentRepository commentRepository;

    @Autowired
    private IPublicationRepository publicationRepository;



    @Override
    @Transactional
    public CommentDTO createComment(long publicationId, CommentDTO commentDTO) {
        Comment comment = maptToEntity(commentDTO);
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new ResourceNotFoundExcepion("Publication", "ID", publicationId));

        comment.setPublication(publication);

        Comment newComment = commentRepository.save(comment);

        return mapToDTO(newComment);
    }


    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> getAllCommentsByPublicationId(long publicationId) {
        publicationRepository.findById(publicationId)
                .orElseThrow(() -> new ResourceNotFoundExcepion("Publication", "ID", publicationId));

        List<Comment> comments = commentRepository.findByPublicationId(publicationId);

        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
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
    @Transactional
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


    @Override
    @Transactional
    public void deleteComment(Long publicationId, Long id) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new ResourceNotFoundExcepion("Publication", "ID", publicationId));

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcepion("Comment", "ID", id));

        // verify that the comment belongs to the publication
        if (!comment.getPublication().getId().equals(publication.getId()))
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "Comment does not belong to the publication!");
        

        commentRepository.deleteById(id);
    }
    


    // DTO to entity
    private Comment maptToEntity(CommentDTO commentDTO) {
        Comment comment = modelMapper.map(commentDTO, Comment.class);

        return comment;
    }
    

    // entity to DTO
    private CommentDTO mapToDTO(Comment comment) {
        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);

        return commentDTO;
    }

    
}
