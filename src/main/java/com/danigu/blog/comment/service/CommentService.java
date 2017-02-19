package com.danigu.blog.comment.service;

import com.danigu.blog.comment.Comment;
import com.danigu.blog.comment.CommentEntity;
import com.danigu.blog.comment.persistence.CommentRepository;
import com.danigu.blog.common.service.CommonService;
import com.danigu.blog.post.Post;
import com.danigu.blog.post.service.PostDTO;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author dani
 */
public class CommentService extends CommonService<CommentEntity, Comment> {
    public CommentService(CommentRepository repository, CommentEntityDTOTransformer transformer) {
        super(repository, transformer);
    }

    protected Class<CommentEntity> getEntityClazz() {
        return CommentEntity.class;
    }

    public Comment add(Post post, String content) {
        checkNotNull(post);
        checkNotNull(content);


        /**
         * This is fishy, we don't want to depend on PostService / PostWhatever, but we wan't to make sure we're not
         * persisting any PostDTO, since it's not guaranteed that {@link PostDTO} can only be created by the PostService
         * we might be persisting PostDTO's here (as a cascade) which is out of scope.
         *
         * This would be avoidable by depending on an instance of {@link com.danigu.blog.post.service.PostService},
         * but then we're tightly coupled to that. :(
         */
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
