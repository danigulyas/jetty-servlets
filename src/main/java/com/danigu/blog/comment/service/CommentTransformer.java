package com.danigu.blog.comment.service;

import com.danigu.blog.comment.Comment;
import com.danigu.blog.comment.CommentEntity;
import com.danigu.blog.base.service.Transformer;
import com.danigu.blog.post.service.PostTransformer;
import lombok.AllArgsConstructor;

/**
 * @author dani
 */
@AllArgsConstructor
public class CommentTransformer implements Transformer<CommentEntity, Comment> {
    private final PostTransformer postTransformer;

    public Comment toEntity(CommentEntity entity) {
        return new CommentDTO(entity.getId(), postTransformer.toEntity(entity.getPost()), entity.getContent());
    }

    public CommentEntity fromEntity(Comment dto) {
        return new CommentEntity(dto.getId(), postTransformer.fromEntity(dto.getPost()), dto.getContent());
    }
}
