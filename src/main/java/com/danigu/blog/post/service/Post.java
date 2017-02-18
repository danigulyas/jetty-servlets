package com.danigu.blog.post.service;

/**
 * Representation of {@link com.danigu.blog.post.persistence.Post} to the outside world, immutable.
 * @author dani
 */
public interface Post {
    long getId();
    String getName();
    String getContent();
}
