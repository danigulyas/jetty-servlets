package com.danigu.blog.comment.persistence;

import com.danigu.blog.post.persistence.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;

/**
 * @author dani
 */
@Entity(name = "Comment")
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {
    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    private PostEntity post;

    @Column
    private String content;
}
