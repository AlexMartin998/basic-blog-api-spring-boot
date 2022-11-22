package com.alex.blog.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.alex.blog.entities.Role;


public interface IRoleRepository extends CrudRepository<Role, Long> {
    
    public Optional<Role> findOneByName(String name);

}

