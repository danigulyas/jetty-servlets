package com.danigu.blog.post;

import com.danigu.blog.comment.Comment;
import com.google.common.collect.ImmutableList;
import lombok.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author dani
 */
@Getter
@ToString
public class PostDraft {
    protected final String name;
    protected final String content;
    protected final ImmutableList<Comment> comments;

    protected PostDraft(String name, String content, ImmutableList<Comment> comments) {
        checkNotNull(name);
        checkNotNull(content);
        checkNotNull(comments);

        this.name = name;
        this.content = content;
        this.comments = comments;
    }

    public static class Builder {
        protected String name;
        protected String content;
        protected ImmutableList<Comment> comments;

        Builder() {}

        public Builder(PostDraft draft) {
            this.name = draft.getName();
            this.content = draft.getContent();
            this.comments = draft.getComments();
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder comments(ImmutableList<Comment> comments) {
            this.comments = comments;
            return this;
        }

        public PostDraft build() {
            return new PostDraft(name, content, comments);
        }
    }
}
