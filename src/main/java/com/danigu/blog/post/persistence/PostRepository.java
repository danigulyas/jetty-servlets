package com.danigu.blog.post.persistence;

import com.danigu.blog.base.persistence.BaseRepository;
import com.danigu.blog.post.PostEntity;

import javax.persistence.EntityManagerFactory;

/**
 * DAO for post, it's responsibility to interact with the database.
 * @see BaseRepository
 * @author dani
 */
public class PostRepository extends BaseRepository<PostEntity> {

    public PostRepository(EntityManagerFactory emf) {
        super(emf);
    }
}
