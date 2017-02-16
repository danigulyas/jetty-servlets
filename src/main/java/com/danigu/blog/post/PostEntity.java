package com.danigu.blog.post;

import lombok.Data;

import javax.persistence.*;

/**
 * @author dani
 */
@Data
@Entity
@Table(name = "post")
public class PostEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private String content;
}
