package com.alex.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alex.blog.dto.CommentDTO;
import com.alex.blog.services.ICommentService;



@RestController
@RequestMapping("/api")
public class CommentController {
    
    @Autowired
    private ICommentService commentService;



    @GetMapping("/publications/{publicationId}/comments")
    public List<CommentDTO> getAllCommentsByPublicationId(@PathVariable Long publicationId) {

        return commentService.getAllCommentsByPublicationId(publicationId);
    }


    @GetMapping("/publications/{publicationId}/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentByPublicationId(@PathVariable Long publicationId, @PathVariable Long id) {

        return ResponseEntity.ok(commentService.getCommentById(publicationId, id));
    }


    @PostMapping("/publications/{publicationId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long publicationId,
            @RequestBody CommentDTO commentDTO) {

        return new ResponseEntity<>(commentService.createComment(publicationId, commentDTO), HttpStatus.CREATED);
    }


    @PutMapping("/publications/{publicationId}/comments/{id}")
    public ResponseEntity<CommentDTO> updateCommentOfPublication(@PathVariable Long publicationId,
            @PathVariable Long id, @RequestBody CommentDTO commentDTO) {

        return new ResponseEntity<>(commentService.updateComment(publicationId, id, commentDTO), HttpStatus.OK);
    }



}