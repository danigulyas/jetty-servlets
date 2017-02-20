package com.danigu.blog.post.service;

import com.danigu.blog.base.service.BaseService;
import com.danigu.blog.comment.service.CommentService;
import com.danigu.blog.post.Post;
import com.danigu.blog.post.PostEntity;
import com.danigu.blog.post.persistence.*;
import javassist.NotFoundException;

import javax.persistence.OneToOne;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Service for managing posts.
 * @see BaseService
 */
public class PostService extends BaseService<PostEntity, Post> {
    protected CommentService commentService;

    public PostService(PostRepository repository, PostTransformer transformer, CommentService commentService) {
        super(repository, transformer);
        checkNotNull(commentService);

        this.commentService = commentService;
    }

    /**
     * Creates a new post.
     * @param name of the new post.
     * @param content of the new post.
     * @return the post created.
     * @throws IllegalArgumentException
     */
    public Post add(String name, String content) throws IllegalArgumentException {
        checkArgument(name != null, "Name can't be null.", IllegalArgumentException.class);
        checkArgument(name != null, "Name can't be null.", IllegalArgumentException.class);

        return transformer.toEntity(repository.save(new PostEntity(name, content)));
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
        commentService.deleteCommentsByPostId(id);
        super.deleteById(id);
    }
}
