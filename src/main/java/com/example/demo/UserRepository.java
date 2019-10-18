package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsernameContaining(String username);
    User findByEmail(String email);
    Long countByEmail(String email);
    Long countByUsername(String username);
    User findByUsername(String username);
    ArrayList<Role> findByRolesContaining(String search);
    //Role findByRolesContaining(String username);
}
