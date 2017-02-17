package com.danigu.blog.comment;

import com.danigu.blog.post.Post;
import lombok.Getter;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author dani
 */
@Getter
public class Comment extends CommentDraft {
    protected final long id;

    Comment(String content, long id) {
        super(content);

        checkNotNull(id);
        this.id = id;
    }

    public Builder draft() {
        return new Builder(this);
    }

    public static Comment fromEntity(CommentEntity entity) {
        if(entity == null) return null;
        return new Comment(entity.getContent(), entity.getId());
    }
}
