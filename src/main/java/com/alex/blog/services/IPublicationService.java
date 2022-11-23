package com.alex.blog.services;

import com.alex.blog.dto.PublicationDTO;
import com.alex.blog.dto.PublicationResponse;

public interface IPublicationService {

    public PublicationDTO createPublication(PublicationDTO publicationDTO);

    // public List<PublicationDTO> getAll();
    // pagination
    public PublicationResponse getAll(int page, int size, String sortBy, String sortDir);

    public PublicationDTO getByID(Long id);

    // usa el save del jparepository
    public PublicationDTO update(PublicationDTO publicationDTO, Long id);

    public void delete(Long id);

}
