package com.danigu.blog.dic;

import com.danigu.blog.post.PostRepository;
import com.danigu.blog.post.PostService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author dani
 */
public class StationLocator {
    private EntityManagerFactory entityManagerFactory;
    private PostService postService;
    private PostRepository postRepository;

    public StationLocator() {}

    public EntityManagerFactory getEntityManagerFactory() {
        if(entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("blog");
        }

        return entityManagerFactory;
    }

    public PostRepository getPostRepository() {
        if(postRepository == null) {
            postRepository = new PostRepository(getEntityManagerFactory().createEntityManager());
        }

        return postRepository;
    }

    public PostService getPostService() {
        if(postService == null) {
            postService = new PostService(getPostRepository());
        }

        return postService;
    }
}
