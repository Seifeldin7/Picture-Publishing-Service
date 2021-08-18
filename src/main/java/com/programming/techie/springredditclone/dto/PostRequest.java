package com.programming.techie.springredditclone.dto;

import com.programming.techie.springredditclone.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long postId;
    private Category category;
    private String description;
}
