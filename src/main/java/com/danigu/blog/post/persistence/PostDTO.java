package com.danigu.blog.post.persistence;
import lombok.Data;

/**
 * Only the persistence layer can construct these with id.
 * @see Post
 * @author dani
 */
@Data
public class PostDTO implements Post {
    private long id;
    private String name;
    private String content;

    /**
     * Constructor for a new Post, usable by the outside world.
     * @param name
     * @param content
     */
    public PostDTO(String name, String content) {
        this((Long) null, name, content);
    }

    /**
     * Constructor for the persistence layer.
     * @param id
     * @param name
     * @param content
     */
    PostDTO(Long id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }
}
