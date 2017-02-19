package com.danigu.blog.post.persistence;

import com.danigu.blog.common.persistence.CommonRepository;
import com.danigu.blog.post.PostEntity;

import javax.persistence.EntityManagerFactory;

/**
 * DAO for post, it's responsibility to interact with the database.
 * @see CommonRepository
 * @author dani
 */
public class PostRepository extends CommonRepository<PostEntity> {

    public PostRepository(EntityManagerFactory emf) {
        super(emf);
    }

    @Override
    protected Class<PostEntity> getClazz() {
        return PostEntity.class;
    }
}
