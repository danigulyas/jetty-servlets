package com.danigu.blog.dic;

import com.danigu.blog.comment.persistence.CommentRepository;
import com.danigu.blog.comment.service.CommentTransformer;
import com.danigu.blog.comment.service.CommentService;
import com.danigu.blog.post.persistence.PostRepository;
import com.danigu.blog.post.service.PostService;
import com.danigu.blog.post.service.PostTransformer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author dani
 */
public class ServiceLocator {
    private EntityManagerFactory entityManagerFactory;
    private PostService postService;
    private PostRepository postRepository;

    private CommentService commentService;
    private CommentRepository commentRepository;

    public ServiceLocator() {}

    public EntityManagerFactory getEntityManagerFactory() {
        if(entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("blog");
        }

        return entityManagerFactory;
    }

    public PostRepository getPostRepository() {
        if(postRepository == null) {
            postRepository = new PostRepository(getEntityManagerFactory());
        }

        return postRepository;
    }

    public PostService getPostService() {
        if(postService == null) {
            postService = new PostService(getPostRepository(), new PostTransformer());
        }

        return postService;
    }

    public CommentRepository getCommentRepository() {
        if(commentRepository == null) {
            commentRepository = new CommentRepository(getEntityManagerFactory());
        }

        return commentRepository;
    }

    public CommentService getCommentService() {
        if(commentService == null) {
            CommentTransformer transformer = new CommentTransformer(new PostTransformer());
            commentService = new CommentService(getCommentRepository(), transformer);
        }

        return commentService;
    }
}
