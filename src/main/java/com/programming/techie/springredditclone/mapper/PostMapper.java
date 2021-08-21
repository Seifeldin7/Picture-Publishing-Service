package com.programming.techie.springredditclone.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.programming.techie.springredditclone.dto.PostRequest;
import com.programming.techie.springredditclone.dto.PostResponse;
import com.programming.techie.springredditclone.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "imgName", source = "imgName")
    @Mapping(target = "category", source = "postRequest.category")
    @Mapping(target = "status", source = "postRequest.status")
    public abstract Post map(PostRequest postRequest, String imgName, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponse mapToDto(Post post);

    String getDuration(Post post) {
        return post.getCreatedDate() !=null ? TimeAgo.using(post.getCreatedDate().toEpochMilli()) : null;
    }


}