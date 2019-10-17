package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    //Role findByRolesContaining(String username);
    ArrayList<Role> findByRolesContaining(String search);
}
