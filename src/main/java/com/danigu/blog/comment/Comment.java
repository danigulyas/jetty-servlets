package com.danigu.blog.comment;

import com.danigu.blog.post.Post;
import com.danigu.blog.post.service.PostDTO;

/**
 * @author dani
 */
public interface Comment {
    long getId();
    String getContent();
    Post getPost();
}
