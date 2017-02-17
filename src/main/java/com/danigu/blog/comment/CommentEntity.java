package com.danigu.blog.comment;

import com.danigu.blog.post.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

/**
 * @author dani
 */
@Data
@Entity
@Table(name = "comment")
@AllArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String content;
}
