package com.programming.techie.springredditclone.controller;

import com.programming.techie.springredditclone.dto.PostRequest;
import com.programming.techie.springredditclone.dto.PostResponse;
import com.programming.techie.springredditclone.model.*;
import com.programming.techie.springredditclone.repository.UserRepository;
import com.programming.techie.springredditclone.service.CustomUserDetailsService;
import com.programming.techie.springredditclone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final UserRepository userRepository;

    @RequestMapping("/")
    public ModelAndView index() {
        List<PostResponse> posts = postService.getAcceptedPosts();
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        modelAndView.setViewName("start.html");
        modelAndView.addObject("posts", posts);
        if(user != null)
            modelAndView.addObject("userRole", user.getRoles());
        return modelAndView;
    }

    @RequestMapping("/user/upload")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ModelAndView upload() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("upload.html");
        return modelAndView;
    }


    @RequestMapping("/admin/dashboard")
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView processImage(@RequestParam Long postId) {
        PostResponse post = postService.getPost(postId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("processimage.html");
        modelAndView.addObject("post", post);
        return modelAndView;
    }

    @PostMapping(value="/user/create-post")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ModelAndView createPost(@ModelAttribute(name = "postRequest") PostRequest postRequest,
                                   @RequestParam("imageFile") MultipartFile imageFile) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            postRequest.setCategory(Category.valueOf(postRequest.getCat().toUpperCase()));
            postRequest.setStatus(Status.PENDING);
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView acceptPost(@RequestParam("postId") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            postService.acceptPost(id);

        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.setViewName("error");
            return modelAndView;
        }
        modelAndView.setViewName("admin");
        return modelAndView;

    }

    @PostMapping(value="/admin/reject-post")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView rejectPost(@RequestParam("postId") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            postService.rejectPost(id);

        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.setViewName("error");
            return modelAndView;
        }
        modelAndView.setViewName("admin");
        return modelAndView;

    }


}
