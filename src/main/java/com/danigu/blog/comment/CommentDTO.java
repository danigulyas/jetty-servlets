package com.danigu.blog.comment;

import com.danigu.blog.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author dani
 */
@AllArgsConstructor
@Getter
public class CommentDTO implements Comment {
    public long id;
    public Post post;
    public String content;
}
