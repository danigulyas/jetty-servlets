package com.danigu.blog.post;

import lombok.Getter;
import lombok.ToString;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author dani
 */
@Getter
@ToString(callSuper = true)
public class Post extends PostDraft {
    private final long id;

    Post(String name, String content, long id) {
        super(name, content);

        checkNotNull(id);
        this.id = id;
    }

    public Builder draft() {
        return new Builder(this);
    }

    static Post from(PostEntity entity) {
        if(entity == null) return null;
        return new Post(entity.getName(), entity.getContent(), entity.getId());
    }
}
