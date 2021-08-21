package com.programming.techie.springredditclone.controller;

import com.programming.techie.springredditclone.dto.PostResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class AuthController {
    @RequestMapping("/admin/login")
    public String adminLogin() {

        return "loginAdmin";
    }

    @RequestMapping("/login")
    public String userLogin() {

        return "loginUser";
    }
}
