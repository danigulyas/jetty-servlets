package com.danigu.blog.post.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @see Post
 * @author dani
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class PostDTO implements Post {
    private final long id;
    private final String name;
    private final String content;
}
