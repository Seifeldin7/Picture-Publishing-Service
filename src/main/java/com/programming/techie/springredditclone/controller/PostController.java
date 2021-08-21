package com.programming.techie.springredditclone.controller;

import com.programming.techie.springredditclone.dto.PostRequest;
import com.programming.techie.springredditclone.dto.PostResponse;
import com.programming.techie.springredditclone.model.Category;
import com.programming.techie.springredditclone.model.Post;
import com.programming.techie.springredditclone.model.Status;
import com.programming.techie.springredditclone.model.User;
import com.programming.techie.springredditclone.repository.UserRepository;
import com.programming.techie.springredditclone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserRepository userRepo;

    @RequestMapping("/dashboard")
    public ModelAndView index() {
        List<PostResponse> posts = postService.getAcceptedPosts();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("start.html");
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

    @RequestMapping("/admin/dashboard")
    public ModelAndView admin() {
        List<PostResponse> posts = postService.getPendingPosts();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin.html");
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

    @RequestMapping("/show-image")
    public ModelAndView showImage(@RequestParam String imgName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("showimage.html");
        modelAndView.addObject("imgName", imgName);
        return modelAndView;
    }

    @RequestMapping("/admin/process-image")
    public ModelAndView processImage(@RequestParam Long postId) {
        PostResponse post = postService.getPost(postId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("processimage.html");
        modelAndView.addObject("post", post);
        return modelAndView;
    }

    @RequestMapping("/signup")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);

        return "register_success";
    }

    @PostMapping(value="/create-post")
    public ModelAndView createPost(@ModelAttribute(name = "postRequest") PostRequest postRequest,
                                   @RequestParam("imageFile") MultipartFile imageFile) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            postRequest.setCategory(Category.valueOf(postRequest.getCat().toUpperCase()));
            postRequest.setStatus(Status.ACCEPTED);
            postService.save(imageFile, postRequest);

        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.setViewName("error");
            return modelAndView;
        }
        modelAndView.setViewName("start");
        return modelAndView;

    }

    @PostMapping(value="/admin/accept-post")
    public ModelAndView acceptPost(@RequestParam("postId") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            postService.acceptPost(id);

        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.setViewName("error");
            return modelAndView;
        }
        modelAndView.setViewName("success");
        return modelAndView;

    }

    @PostMapping(value="/admin/reject-post")
    public ModelAndView rejectPost(@RequestParam("postId") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            postService.rejectPost(id);

        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.setViewName("error");
            return modelAndView;
        }
        modelAndView.setViewName("success");
        return modelAndView;

    }


}
