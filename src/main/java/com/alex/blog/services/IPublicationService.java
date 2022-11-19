package com.alex.blog.services;

import java.util.List;

import com.alex.blog.dto.PublicationDTO;

public interface IPublicationService {
    
    public PublicationDTO createPublication(PublicationDTO publicationDTO);

    // public List<PublicationDTO> getAll();

    // paginado
    public List<PublicationDTO> getAll(int page, int size);

    public PublicationDTO getByID(Long id);

    // usa el save del jparepository
    public PublicationDTO update(PublicationDTO publicationDTO, Long id);

    public void delete(Long id);

}
