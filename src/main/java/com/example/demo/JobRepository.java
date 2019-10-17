package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface JobRepository extends CrudRepository<Job, Long> {
    ArrayList<Job> findJobByTitle(String title);
    ArrayList<Job> findJobByAuthor(String author);
    // ArrayList<Job> removeById(Long id);
}