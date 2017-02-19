package com.danigu.blog.post.persistence;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Implementation-detail of the database, represented as {@link com.danigu.blog.post.Post} to the outside world.
 * @author dani
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "post")
@Table(name = "post")
@Data
public class PostEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private String content;
}
