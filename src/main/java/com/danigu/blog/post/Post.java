package com.danigu.blog.post;

import com.danigu.blog.comment.Comment;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author dani
 */
@Getter
@ToString(callSuper = true)
public class Post extends PostDraft {
    private final long id;

    Post(String name, String content, ImmutableList<Comment> comments, long id) {
        super(name, content, comments);

        checkNotNull(id);
        this.id = id;
    }

    public Builder draft() {
        return new Builder(this);
    }

    public static Post fromEntity(PostEntity entity) {
        if(entity == null) return null;
        List<Comment> comments = entity.getComments().stream().map(Comment::fromEntity).collect(Collectors.toList());
        ImmutableList<Comment> immutableComments = ImmutableList.copyOf(comments);

        return new Post(entity.getName(), entity.getContent(), immutableComments, entity.getId());
    }
}
