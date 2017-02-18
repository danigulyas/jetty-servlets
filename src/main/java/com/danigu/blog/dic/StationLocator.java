package com.danigu.blog.dic;

import com.danigu.blog.post.persistence.PostRepository;
import com.danigu.blog.post.service.PostService;
import com.danigu.blog.post.service.ServiceDTOAdaptor;

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
            postRepository = new PostRepository(getEntityManagerFactory());
        }

        return postRepository;
    }

    public PostService getPostService() {
        if(postService == null) {
            postService = new PostService(getPostRepository(), new ServiceDTOAdaptor());
        }

        return postService;
    }
}
