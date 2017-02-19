package com.danigu.blog.comment.service;

import com.danigu.blog.comment.Comment;
import com.danigu.blog.comment.CommentEntity;
import com.danigu.blog.comment.persistence.CommentRepository;
import com.danigu.blog.base.service.BaseService;
import com.danigu.blog.post.Post;
import com.danigu.blog.post.service.PostDTO;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author dani
 */
public class CommentService extends BaseService<CommentEntity, Comment> {
    public CommentService(CommentRepository repository, CommentTransformer transformer) {
        super(repository, transformer);
    }

    protected Class<CommentEntity> getEntityClazz() {
        return CommentEntity.class;
    }

    public Comment add(Post post, String content) {
        checkNotNull(post);
        checkNotNull(content);

        Comment dto = new CommentDTO(Long.valueOf(null), post, content);
        CommentEntity entity = transformer.fromEntity(dto);

        return transformer.toEntity(repository.save(entity));
    }

    public List<Comment> getComments(Post post) {
        checkNotNull(post);

        return getRepository()
                .getAllWithPostId(post.getId())
                .stream()
                .map(transformer::toEntity)
                .collect(Collectors.toList());
    }

    private CommentRepository getRepository() {
        return (CommentRepository) repository;
    }


}
