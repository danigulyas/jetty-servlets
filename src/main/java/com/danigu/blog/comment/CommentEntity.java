package com.danigu.blog.comment;

import com.danigu.blog.base.persistence.*;
import com.danigu.blog.post.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * The mapping could've been done with XML for better separation, although this is not tied to the implementation.
 * @author dani
 */
@Entity(name = "Comment")
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity implements HasId {
    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    private PostEntity post;

    @Column
    private String content;
}
