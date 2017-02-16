package com.danigu.blog.post;

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

    protected PostDraft(String name, String content) {
        checkNotNull(name);
        checkNotNull(content);

        this.name = name;
        this.content = content;
    }

    public static class Builder {
        protected String name;
        protected String content;

        Builder() {}

        public Builder(PostDraft draft) {
            this.name = draft.getName();
            this.content = draft.getContent();
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public PostDraft build() {
            return new PostDraft(name, content);
        }
    }
}
