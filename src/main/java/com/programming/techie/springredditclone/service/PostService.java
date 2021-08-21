package com.programming.techie.springredditclone.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.programming.techie.springredditclone.dto.PostRequest;
import com.programming.techie.springredditclone.dto.PostResponse;
import com.programming.techie.springredditclone.exceptions.PostNotFoundException;
import com.programming.techie.springredditclone.mapper.PostMapper;
import com.programming.techie.springredditclone.model.Post;
import com.programming.techie.springredditclone.model.User;
import com.programming.techie.springredditclone.repository.PostRepository;
import com.programming.techie.springredditclone.repository.UserRepository;
import com.programming.techie.springredditclone.util.FileUploadUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.StandardCopyOption;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    public void save(MultipartFile imageFile, PostRequest postRequest) throws Exception {
        Path currentPath = Paths.get(".");
        Path absolutePath = currentPath.toAbsolutePath();
        String uploadDir = absolutePath + "/src/main/resources/static/images/";
        String imgName = StringUtils.cleanPath(imageFile.getOriginalFilename());
        FileUploadUtil.saveFile(uploadDir, imgName, imageFile);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getPrincipal().toString());
        postRepository.save(postMapper.map(postRequest, imgName, user));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAcceptedPosts() {
        return postRepository.getAcceptedPosts()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPendingPosts() {
        return postRepository.getPendingPosts()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    public void acceptPost(Long id) {
        postRepository.acceptPost(id);
    }

    public void rejectPost(Long id) {
        postRepository.rejectPost(id);
        PostResponse p = getPost(id);
        Path currentPath = Paths.get(".");
        Path absolutePath = currentPath.toAbsolutePath();
        String uploadDir = absolutePath + "/src/main/resources/static/images/";
        File fileToDelete = new File(uploadDir + p.getImgName());
        boolean success = fileToDelete.delete();
    }

}
