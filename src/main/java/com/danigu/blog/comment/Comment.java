package com.danigu.blog.comment;

import com.danigu.blog.post.Post;

/**
 * @author dani
 */
public interface Comment {
    long getId();
    String getContent();
    Post getPost();
}
