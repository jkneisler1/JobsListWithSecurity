package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.BindException;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid
                                          @ModelAttribute("user") User user,
                                          BindingResult result,
                                          Model model) {
        model.addAttribute("user", user);
        if (result.hasErrors()) {
            return "registration";
        }
        else {
            userService.saveUser(user);
            model.addAttribute("message", "User Account Created");
        }
        return "index";
    }

    @RequestMapping("/")
    public String listJobs(Model model) {
        model.addAttribute("jobs", jobRepository.findAll());
        return "/list";
    }


    @RequestMapping("/login")
    public String login() { return "login"; }

    /*
    @RequestMapping("/login")
    public String login() {
        return "/processAllJobs";
    }
     */

    @RequestMapping("/manage")
    public String manage() { return "managepwd"; }

    @RequestMapping("/managetest")
    public String managetest(Model model, @RequestParam(name="pwd") String pwd) {
        System.out.println("Plain text: " + pwd);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String input_pwd = passwordEncoder.encode(pwd);
        System.out.println("Encrypted " + input_pwd);
        ArrayList<User> usrs = userRepository.findByPassword(input_pwd);
        //System.out.println(usr.getEmail());
        //if (userRepository.findByPasswordEquals(input_pwd)) {
            model.addAttribute("users", usrs);
            //model.addAttribute("jobs", jobRepository.findByUser_Id(usr.getId()));
            return "manageall";
        //}
        //return "list";
    }

    @RequestMapping("/secure")
    public String secure(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsernameContaining(username));
        return "secure";
    }

    @RequestMapping("/admin")
    public String admin() { return "admin"; }

    @RequestMapping("/super")
    public String supervisor() {
        return "super";
    }

    @GetMapping("/add")
    public String jobForm(Model model) {
        model.addAttribute("job", new Job());
        return "jobform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Job job, BindingResult result) {
        if (result.hasErrors()) {
            return "jobform";
        }
        jobRepository.save(job);
        return "redirect:/";
    }

    @GetMapping("/processAllJobs")
    public String processAllJobsGet(Model model) {
        model.addAttribute("jobs", jobRepository.findAll());
        return "/searchlist";
    }

    @PostMapping("/processAllJobs")
    public String processAllJobsSearch(Model model) {
        model.addAttribute("jobs", jobRepository.findAll());
        return "/searchlist";
    }

    /* The next method process requests from the nav-bar dropdown list to add jobs */
    @GetMapping("/addJob")
    public String processAddJob(Model model) {
        model.addAttribute("job", new Job());
        return "jobform";
    }

    @PostMapping("/processJob")
    public String processCarForm(@Valid Job job, BindingResult result) {
        if (result.hasErrors()) {
            return "jobform";
        }
        jobRepository.save(job);

        return "redirect:/searchlist";
    }

    @PostMapping("/processUser")
    public String processUser(@Valid User user, BindingResult result)  {
        if (result.hasErrors()) {
            return "securityform";
        }
        userRepository.save(user);
        return "redirect:/";
    }

    @PostMapping("/processJobByTitle")
    public String processJobByTitleSearch(Model model, @RequestParam(name="search") String search) {
        // System.out.println(search);
        model.addAttribute("jobs", jobRepository.findJobsByTitleContaining(search));
        return "/list";
    }

    @PostMapping("/processUserName")
    public String processUserNameSearch(Model model, @RequestParam(name="search") String search) {
        // System.out.println(search);
        model.addAttribute("jobs", jobRepository.findByUser_LastName(search));
        return "/list";
    }

    @RequestMapping("/detail/{id}")
    public String showJob(@PathVariable("id") long id, Model model) {
        model.addAttribute("job", jobRepository.findById(id));
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateJob(@PathVariable("id") long id, Model model) {
        model.addAttribute("job", jobRepository.findById(id));
        return "jobform";
    }

    @RequestMapping("/delete/{id}")
    public String delJob(@PathVariable("id") long id) {
        jobRepository.deleteById(id);
        return "redirect:/";
    }
}
