package com.danigu.blog.post.persistence;

import com.danigu.blog.common.TwoWayAdaptor;
import lombok.NoArgsConstructor;

/**
 * TwoWayAdaptor between the DTO {@link PostDTO} and the Entity {@link PostEntity}.
 * @author dani
 */
@NoArgsConstructor
class PersistenceDTOAdaptor implements TwoWayAdaptor<PostEntity, PostDTO> {
    public PostDTO convert(PostEntity entity) {
        return new PostDTO(entity.getId(), entity.getName(), entity.getContent());
    }

    public PostEntity convertFrom(PostDTO post) {
        return new PostEntity(post.getId(), post.getName(), post.getContent());
    }
}
