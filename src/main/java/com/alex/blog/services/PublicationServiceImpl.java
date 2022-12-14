package com.alex.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alex.blog.dto.PublicationDTO;
import com.alex.blog.dto.PublicationResponse;
import com.alex.blog.entities.Publication;
import com.alex.blog.exceptions.ResourceNotFoundExcepion;
import com.alex.blog.repositories.IPublicationRepository;


@Service
public class PublicationServiceImpl implements IPublicationService {

    @Autowired    
    private ModelMapper modelMapper;

    @Autowired  // jpaRepository ya lo hace un bean
    private IPublicationRepository publicatinoRepository;


    @Override
    @Transactional    // escritura en DB
    public PublicationDTO createPublication(PublicationDTO publicationDTO) {
        Publication publication = mapToEntity(publicationDTO);
        
        Publication newPublication = publicatinoRepository.save(publication);

        PublicationDTO publicationResponse = mapToDTO(newPublication);

        return publicationResponse;
    }


/*  // without pagination
    @Override
    @Transactional(readOnly = true)
    public List<PublicationDTO> getAll() {
        List<Publication> publications = publicatinoRepository.findAll();

        // mapeo las entity q traigo de DB a DTO para enviarlas en el body de la response
        return publications.stream().map(p -> mapToDTO(p)).collect(Collectors.toList());
    } */
    
    // pagination and sorting
    @Override
    @Transactional(readOnly = true)
    public PublicationResponse getAll(int page, int size, String sortBy, String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Publication> publications = publicatinoRepository.findAll(pageable);

        List<Publication> publicationsList = publications.getContent();
        List<PublicationDTO> content = publicationsList.stream().map(p -> mapToDTO(p)).collect(Collectors.toList());

        // construimos la response
        PublicationResponse publicationResponse = new PublicationResponse();
        publicationResponse.setContent(content);
        publicationResponse.setPageNumber(publications.getNumber());
        publicationResponse.setSize(publications.getSize());
        publicationResponse.setTotalElements(publications.getTotalElements());
        publicationResponse.setTotalPages(publications.getTotalPages());
        publicationResponse.setLastOne(publications.isLast());

        return publicationResponse;

    }

    

    @Override
    @Transactional(readOnly = true)
    public PublicationDTO getByID(Long id) {
        Publication publication = publicatinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcepion("Publication", "ID", id));

        return mapToDTO(publication);
    }
    


    @Override
    @Transactional
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
    @Transactional
    public void delete(Long id) {
        publicatinoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcepion("Publication", "ID", id));

        publicatinoRepository.deleteById(id);;
    }



    // DTO to entity
    private Publication mapToEntity(PublicationDTO publicationDTO) {
        Publication publication = modelMapper.map(publicationDTO, Publication.class);

        return publication;

        /* Publication publication = new Publication();
        publication.setTitle(publicationDTO.getTitle());
        publication.setDescription(publicationDTO.getDescription());
        publication.setContent(publicationDTO.getContent());
        return publication; */
    }


    // Entidad a DTO
    private PublicationDTO mapToDTO(Publication publication) {
        
        PublicationDTO publicationDTO = modelMapper.map(publication, PublicationDTO.class);

        return publicationDTO;

        /*PublicationDTO publicationDTO = new PublicationDTO();
        publicationDTO.setId(publication.getId());
        publicationDTO.setTitle(publication.getTitle());
        publicationDTO.setDescription(publication.getDescription());
        publicationDTO.setContent(publication.getContent());
        return publicationDTO; */
    }



  


}
