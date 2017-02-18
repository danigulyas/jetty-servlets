package com.danigu.blog.post.persistence;

/**
 * Interface for representing {@link PostEntity} for the outside world.
 * @author dani
 */
public interface Post {
    /**
     * No setter for id to the outside world, the persistence layer is responsible for handling these.
     */
    long getId();

    String getName();
    void setName(String name);

    String getContent();
    void setContent(String content);
}
