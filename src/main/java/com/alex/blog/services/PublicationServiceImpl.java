package com.alex.blog.services;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alex.blog.dto.PublicationDTO;
import com.alex.blog.entities.Publication;
import com.alex.blog.exceptions.ResourceNotFoundExcepion;
import com.alex.blog.repositories.IPublicationRepository;


@Service
public class PublicationServiceImpl implements IPublicationService {

    @Autowired  // jpaRepository ya lo hace un bean
    private IPublicationRepository publicatinoRepository;


    @Override
    @Transactional    // escritura en DB
    public PublicationDTO createPublication(PublicationDTO publicationDTO) {
        // DTO to Entity <- Recibimos JSON en la request y lo convertimos a entidad de java
        Publication publication = mapToEntity(publicationDTO);
        
        // persistimos la entidad - usamos el JpaRepository
        Publication newPublication = publicatinoRepository.save(publication);


        // Entity to DTO  -  lo q persistimos en DB lo transformanos a JSON para enviarlo como response a la request
        PublicationDTO publicationResponse = mapToDTO(newPublication);


        return publicationResponse;
    }


/*  // SIN paginado   
    @Override
    @Transactional(readOnly = true)
    public List<PublicationDTO> getAll() {
        List<Publication> publications = publicatinoRepository.findAll();

        // mapeo las entity q traigo de DB a DTO para enviarlas en el body de la response
        return publications.stream().map(p -> mapToDTO(p)).collect(Collectors.toList());
    } */
    
    // Paginado
    @Override
    public List<PublicationDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Publication> publications = publicatinoRepository.findAll(pageable);

        List<Publication> publicationsList = publications.getContent();

        return publicationsList.stream().map(p -> mapToDTO(p)).collect(Collectors.toList());
    }

    

    @Override
    public PublicationDTO getByID(Long id) {
        Publication publication = publicatinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcepion("Publication", "ID", id));

        return mapToDTO(publication);
    }
    


    @Override
    public PublicationDTO update(PublicationDTO publicationDTO, Long id) {
        Publication publication = publicatinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcepion("Publication", "ID", id));
        
        // si encuentra el id en DB lo actualizamos con el dto
        publication.setTitle(publicationDTO.getTitle());
        publication.setDescription(publicationDTO.getDescription());
        publication.setContent(publicationDTO.getContent());
        
        // persisto en db
        Publication updatedPublication = publicatinoRepository.save(publication);


        return mapToDTO(updatedPublication);
    }


    @Override
    public void delete(Long id) {
        publicatinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcepion("Publication", "ID", id));

        publicatinoRepository.deleteById(id);;
    }



    // DTO to entity
    private Publication mapToEntity(PublicationDTO publicationDTO) {
        Publication publication = new Publication();

        publication.setTitle(publicationDTO.getTitle());
        publication.setDescription(publicationDTO.getDescription());
        publication.setContent(publicationDTO.getContent());
        
        return publication;
    }


    // Entidad a DTO
    private PublicationDTO mapToDTO(Publication publication) {
        PublicationDTO publicationDTO = new PublicationDTO();

        publicationDTO.setId(publication.getId());
        publicationDTO.setTitle(publication.getTitle());
        publicationDTO.setDescription(publication.getDescription());
        publicationDTO.setContent(publication.getContent());

        return publicationDTO;
    }



  


}
