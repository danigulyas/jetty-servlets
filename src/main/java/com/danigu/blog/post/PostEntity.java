package com.danigu.blog.post;

import com.danigu.blog.comment.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author dani
 */
@Data
@Entity(name = "post")
@Table(name = "post")
@AllArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private String content;

    @OneToMany(targetEntity = CommentEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<CommentEntity> comments;

    PostEntity(String name, String content, List<CommentEntity> comments) {
        this.name = name;
        this.content = content;
        this.comments = comments;
    }
}
