package com.programming.techie.springredditclone.controller;

import com.programming.techie.springredditclone.dto.PostResponse;
import com.programming.techie.springredditclone.model.User;
import com.programming.techie.springredditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@AllArgsConstructor
public class AuthController {

    private final UserRepository userRepo;

    @RequestMapping("/admin/login")
    public String adminLogin() {

        return "loginAdmin";
    }

    @RequestMapping("/login")
    public String userLogin() {

        return "loginUser";
    }

    @RequestMapping("/signup")
    public String showRegistrationForm(Model model) {
        return "signup";
    }

    @PostMapping("/process_register")
    public String processRegister(@ModelAttribute(name = "user") User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        user.setRoles("ADMIN");
        userRepo.save(user);

        return "upload.html";
    }
}
