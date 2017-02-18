package com.danigu.blog.post.service;

import com.danigu.blog.common.OneWayTransformer;

/**
 * Converts between the implementation of Post in the service and the DTO of persistence.
 * @author dani
 */
public class ServiceDTOTransformer implements OneWayTransformer<com.danigu.blog.post.persistence.PostDTO, PostDTO> {
    public PostDTO convert(com.danigu.blog.post.persistence.PostDTO dto) {
        return new PostDTO(dto.getId(), dto.getName(), dto.getContent());
    }
}
