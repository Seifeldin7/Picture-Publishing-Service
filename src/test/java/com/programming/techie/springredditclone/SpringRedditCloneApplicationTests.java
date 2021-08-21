package com.programming.techie.springredditclone;

import com.programming.techie.springredditclone.controller.PostController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringRedditCloneApplicationTests {

	@Autowired
	private PostController postController;
	@Test
	public void contextLoads() {
		assertThat(postController).isNotNull();
	}

}
