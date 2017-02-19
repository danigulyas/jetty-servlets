package com.danigu.blog.comment.service;

import com.danigu.blog.comment.Comment;
import com.danigu.blog.comment.CommentEntity;
import com.danigu.blog.common.service.Transformer;
import com.danigu.blog.post.service.PostEntityDTOTransformer;
import lombok.AllArgsConstructor;

/**
 * @author dani
 */
@AllArgsConstructor
public class CommentEntityDTOTransformer implements Transformer<CommentEntity, Comment> {
    private final PostEntityDTOTransformer postTransformer;

    public Comment toEntity(CommentEntity entity) {
        return new CommentDTO(entity.getId(), postTransformer.toEntity(entity.getPost()), entity.getContent());
    }

    public CommentEntity fromEntity(Comment dto) {
        return new CommentEntity(dto.getId(), postTransformer.fromEntity(dto.getPost()), dto.getContent());
    }
}
