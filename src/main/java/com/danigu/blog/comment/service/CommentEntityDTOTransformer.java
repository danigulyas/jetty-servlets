package com.danigu.blog.comment.service;

import com.danigu.blog.comment.Comment;
import com.danigu.blog.comment.CommentDTO;
import com.danigu.blog.comment.CommentEntity;
import com.danigu.blog.common.TwoWayTransformer;
import com.danigu.blog.post.service.PostEntityDTOTransformer;
import lombok.AllArgsConstructor;

/**
 * @author dani
 */
@AllArgsConstructor
public class CommentEntityDTOTransformer implements TwoWayTransformer<CommentEntity, Comment> {
    private final PostEntityDTOTransformer postTransformer;

    public Comment convert(CommentEntity entity) {
        return new CommentDTO(entity.getId(), postTransformer.convert(entity.getPost()), entity.getContent());
    }

    public CommentEntity convertFrom(Comment dto) {
        return new CommentEntity(dto.getId(), postTransformer.convertFrom(dto.getPost()), dto.getContent());
    }
}
