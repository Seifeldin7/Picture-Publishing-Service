package com.programming.techie.springredditclone.dto;

import com.programming.techie.springredditclone.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String url;
    private String description;
    private Category category;
    private String userName;
    private String duration;
}
