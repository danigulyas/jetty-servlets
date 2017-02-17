package com.danigu.blog.comment;

import com.danigu.blog.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author dani
 */
@Getter
public class CommentDraft {
    protected final String content;

    CommentDraft(String content) {
        checkNotNull(content);

        this.content = content;
    }

    public static class Builder {
        protected String content;

        public Builder(){}

        public Builder(Comment comment) {
            this.content = comment.content;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public CommentDraft build() {
            return new CommentDraft(content);
        }
    }
}
