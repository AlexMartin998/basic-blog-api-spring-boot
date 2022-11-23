package com.alex.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alex.blog.entities.Publication;



public interface IPublicationRepository extends JpaRepository<Publication, Long> {

}
