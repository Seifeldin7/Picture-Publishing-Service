package com.programming.techie.springredditclone.repository;

import com.programming.techie.springredditclone.model.Category;
import com.programming.techie.springredditclone.model.Post;
import com.programming.techie.springredditclone.model.Status;
import com.programming.techie.springredditclone.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void canGetPendingPosts() throws Exception {
        Post post1 = new Post(3L, Category.LIVING_THINGS,
                Status.PENDING, "image", "description1", null, null);
        Post post2 = new Post(4L, Category.LIVING_THINGS,
                Status.ACCEPTED, "image", "description2", null, null);
        postRepository.save(post1);
        postRepository.save(post2);
        System.out.println(post1.getPostId());
        List<Post> posts = (List<Post>) postRepository.getPendingPosts();
        System.out.println(posts.size());
        assertEquals(posts.size(), 1);
    }
}
