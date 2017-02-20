package com.danigu.blog.post;


import com.danigu.blog.base.persistence.*;
import com.danigu.blog.post.service.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * Implementation-detail of the database, represented as {@link PostDTO} to the outside world.
 * @author dani
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "post")
@Table(name = "post")
@Data
public class PostEntity implements HasId {
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private String content;

    public PostEntity(String name, String content) {
        this.name = name;
        this.content = content;
    }
}
