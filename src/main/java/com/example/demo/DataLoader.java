package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    JobRepository jobRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... strings) throws Exception {
        // Create roles
        roleRepository.save(new Role("USER"));                  // User role
        roleRepository.save(new Role("ADMIN"));                 // Administrator role
        roleRepository.save(new Role("SUPER"));                 // Supervisor role-for courses, this would be the Dean
        Role adminRole = roleRepository.findByRole("ADMIN");
        Role userRole = roleRepository.findByRole("USER");
        Role superRole = roleRepository.findByRole("SUPER");

        // Create & Jobs
        Job job;
        Set<Job> sj;

        // Create users & jobs
        User user;
        user = new User("pcwaters@email.com", "password", "Paul C.", "Waters", true, "pcwaters", "301-525-7896");
        user.setRoles(Arrays.asList(userRole));
        job = new Job(LocalDateTime.now(), "Plumber needed", "A backed up sink needs uncloging");
        sj = new HashSet<Job>();
        sj.add(job);
        user.setJobs(sj);
        userRepository.save(user);

        user = new User("tgarten@email.com", "password", "Tony", "Garten", true, "tgarten", "301-555-6789");
        user.setRoles(Arrays.asList(userRole));
        job = new Job(LocalDateTime.now(), "Wanted Landscaper", "Flower beds need to be cleaned up");
        sj = new HashSet<Job>();
        sj.add(job);
        user.setJobs(sj);
        userRepository.save(user);

        user = new User("jwoods@email.com", "password", "Joshua", "Woods", true, "jwoods", "301-555-1234");
        user.setRoles(Arrays.asList(userRole));
        job = new Job(LocalDateTime.now(), "Contractor", "Need an estimate on a kitchen remodel");
        sj = new HashSet<Job>();
        sj.add(job);
        job = new Job(LocalDateTime.now(), "Contractor", "Need an estimate on a bathroom remodel");
        sj.add(job);
        user.setJobs(sj);
        userRepository.save(user);

        user = new User("admin@email.com", "password", "Admin", "User", true, "auser", "111-555-9999");
        user.setRoles(Arrays.asList(adminRole));
        job = new Job(LocalDateTime.now(), "Admin", "Admin");
        sj = new HashSet<Job>();
        sj.add(job);
        user.setJobs(sj);
        userRepository.save(user);

        user = new User("jk@email.com", "password", "John", "Kneisler", true, "jkneisler", "301-555-9999");
        user.setRoles(Arrays.asList(superRole));
        job = new Job(LocalDateTime.now(), "Supervisor", "Supervisor");
        sj = new HashSet<Job>();
        sj.add(job);
        user.setJobs(sj);
        userRepository.save(user);

    }
}


