package com.programming.techie.springredditclone.repository;

import com.programming.techie.springredditclone.model.Post;
import com.programming.techie.springredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT post FROM Post post WHERE post.status = 2")
    public List<Post> getPendingPosts();

    @Query("SELECT post FROM Post post WHERE post.status = 0")
    public List<Post> getAcceptedPosts();

    @Modifying
    @Query("Update Post post set post.status = 0 WHERE post.id = ?1")
    public void acceptPost(Long id);

    @Modifying
    @Query("Update Post post set post.status = 1 WHERE post.id = ?1")
    public void rejectPost(Long id);
}
