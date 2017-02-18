package com.danigu.blog.post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Could've been nicer if only {@link com.danigu.blog.post.service.PostEntityDTOTransformer}
 * could access the constructor.
 * @see Post
 * @author dani
 */
@Getter
@AllArgsConstructor
public class Post {
    private final long id;
    private final String name;
    private final String content;
}
