package com.danigu.blog.post;

import com.danigu.blog.CommonRepository;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
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
