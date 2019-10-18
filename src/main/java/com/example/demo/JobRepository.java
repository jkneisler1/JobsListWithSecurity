package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
//import java.util.Iterator;

public interface JobRepository extends CrudRepository<Job, Long> {
    //Iterator<Job> findJobByTitleContaining(String title);
    //Iterator<Job> findById(long id);
    //ArrayList<Job> findJobByTitleContaining(String title);
    //ArrayList<Job> findAllByTitleContaining(String title);
    ArrayList<Job> findJobsByTitleContaining(String title);
    ArrayList<Job> findById(long id);
    ArrayList<Job> findAll();
    ArrayList<Job> findByUser_LastName(String search);
    Job findByUser_Id(long id);
}