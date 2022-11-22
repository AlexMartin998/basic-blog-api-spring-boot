package com.alex.blog.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.alex.blog.entities.Usuario;



public interface IUserRepository extends CrudRepository<Usuario, Long> {
    
    public Optional<Usuario> findOneByEmail(String email);

    public Optional<Usuario> findOneByUsernameOrEmail(String username, String email);

    public Optional<Usuario> findOneByUsername(String username);

    public Boolean existsByEmail(String email);
    
    public Boolean existsByUsername(String username);

}
