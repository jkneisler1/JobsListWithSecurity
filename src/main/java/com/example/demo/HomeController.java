package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.BindException;
import java.security.Principal;

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
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
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
        System.out.println(search);
        model.addAttribute("jobs", jobRepository.findJobsByTitleContaining(search));
        return "/list";
    }

    @PostMapping("/processUserName")
    public String processCourseByTitleSearch(Model model, @RequestParam(name="search") String search) {
        model.addAttribute("users", userRepository.findByUsernameContaining(search));
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
