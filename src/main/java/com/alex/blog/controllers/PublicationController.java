package com.alex.blog.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alex.blog.dto.PublicationDTO;
import com.alex.blog.dto.PublicationResponse;
import com.alex.blog.services.IPublicationService;
import static com.alex.blog.utilities.AppConstants.*;


@RestController
@RequestMapping("/api/publications")
public class PublicationController {

    @Autowired
    private IPublicationService publicationService;

    /* SIN paginado    
    @GetMapping
    public List<PublicationDTO> getAllPublications() {
        
        return publicationService.getAll();
    } */


    // Se crea una clase de la respuesta
    @GetMapping
    public PublicationResponse getAllPublications(
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) int size,
            @RequestParam(defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = DEFAULT_SORT_DIR, required = false) String sortDir) {

        return publicationService.getAll(page, size, sortBy, sortDir);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PublicationDTO> getPublicationByID(@PathVariable Long id) {

        return ResponseEntity.ok(publicationService.getByID(id));
    }


    @PostMapping
    public ResponseEntity<PublicationDTO> savePublication(@RequestBody PublicationDTO publicationDTO) {

        return new ResponseEntity<>(publicationService.createPublication(publicationDTO), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PublicationDTO> updatePublication(@RequestBody PublicationDTO publicationDTO,
            @PathVariable Long id) {

        PublicationDTO responsePublication = publicationService.update(publicationDTO, id);

        return new ResponseEntity<>(responsePublication, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePublication(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        publicationService.delete(id);

        response.put("message", "Publication has been successfully deleted!");

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }



}
