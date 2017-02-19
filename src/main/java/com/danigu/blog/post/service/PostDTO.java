package com.danigu.blog.post.service;

import com.danigu.blog.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Could've been nicer if only {@link PostTransformer}
 * could access the constructor.
 * @see PostDTO
 * @author dani
 */
@Getter
@AllArgsConstructor
public class PostDTO implements Post {
    private final long id;
    private final String name;
    private final String content;
}
