package com.danigu.blog.post.service;

import com.danigu.blog.common.service.Transformer;
import com.danigu.blog.post.Post;
import com.danigu.blog.post.PostEntity;

/**
 * Converts between the implementation of PostDTO in the service and the DTO of persistence.
 * @author dani
 */
public class PostTransformer implements Transformer<PostEntity, Post> {
    public Post toEntity(PostEntity entity) {
        return new PostDTO(entity.getId(), entity.getName(), entity.getContent());
    }

    public PostEntity fromEntity(Post dto) {
        return new PostEntity(dto.getId(), dto.getName(), dto.getContent());
    }
}
