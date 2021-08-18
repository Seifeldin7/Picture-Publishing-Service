package com.programming.techie.springredditclone.controller;

import com.programming.techie.springredditclone.dto.PostRequest;
import com.programming.techie.springredditclone.dto.PostResponse;
import com.programming.techie.springredditclone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@Controller
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping(value="/create-post")
    public ModelAndView createPost(@ModelAttribute(name = "postRequest") PostRequest postRequest,
                                   @RequestParam("imageFile") MultipartFile imageFile) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            postService.save(imageFile, postRequest);

        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.setViewName("error");
            return modelAndView;
        }
        modelAndView.setViewName("success");
        return modelAndView;

    }

    @GetMapping
    public ModelAndView getAllPosts() {
        ModelAndView modelAndView = new ModelAndView();
        List<PostResponse> posts = postService.getAllPosts();
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String name) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(name));
    }

    @RequestMapping("/")
    public String index() {
        return "start";
    }
}
