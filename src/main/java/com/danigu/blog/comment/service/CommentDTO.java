package com.danigu.blog.comment.service;

import com.danigu.blog.comment.Comment;
import com.danigu.blog.post.Post;
import com.danigu.blog.post.service.PostDTO;
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

    public CommentDTO(Post post, String content) {
        this.post = post;
        this.content = content;
    }
}
