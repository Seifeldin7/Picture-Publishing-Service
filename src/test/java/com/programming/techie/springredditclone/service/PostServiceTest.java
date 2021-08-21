package com.programming.techie.springredditclone.service;

import com.programming.techie.springredditclone.repository.PostRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Test
    public void testAdminAcceptImage() throws Exception {

    }

}