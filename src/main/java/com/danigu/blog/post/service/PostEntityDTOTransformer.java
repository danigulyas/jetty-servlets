package com.danigu.blog.post.service;

import com.danigu.blog.common.TwoWayTransformer;
import com.danigu.blog.post.Post;
import com.danigu.blog.post.PostEntity;

/**
 * Converts between the implementation of PostDTO in the service and the DTO of persistence.
 * @author dani
 */
public class PostEntityDTOTransformer implements TwoWayTransformer<PostEntity, Post> {
    public Post convert(PostEntity entity) {
        return new PostDTO(entity.getId(), entity.getName(), entity.getContent());
    }

    public PostEntity convertFrom(Post dto) {
        return new PostEntity(dto.getId(), dto.getName(), dto.getContent());
    }
}
