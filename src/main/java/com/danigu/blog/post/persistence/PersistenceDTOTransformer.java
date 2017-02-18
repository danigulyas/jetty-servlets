package com.danigu.blog.post.persistence;

import com.danigu.blog.common.TwoWayTransformer;
import lombok.NoArgsConstructor;

/**
 * TwoWayTransformer between the DTO {@link PostDTO} and the Entity {@link PostEntity}.
 * @author dani
 */
@NoArgsConstructor
class PersistenceDTOTransformer implements TwoWayTransformer<PostEntity, PostDTO> {
    public PostDTO convert(PostEntity entity) {
        return new PostDTO(entity.getId(), entity.getName(), entity.getContent());
    }

    public PostEntity convertFrom(PostDTO post) {
        return new PostEntity(post.getId(), post.getName(), post.getContent());
    }
}
