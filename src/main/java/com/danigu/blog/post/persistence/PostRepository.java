package com.danigu.blog.post.persistence;

import com.danigu.blog.common.persistence.CommonRepository;

import javax.persistence.EntityManagerFactory;

/**
 * @author dani
 * DAO for post, it's reponsibilities:
 * - Conversion between the DTO {@link PostDTO} and the Entity of the persistence implementation {@link PostEntity}
 * - Interaction with the database
 */
public class PostRepository extends CommonRepository<PostEntity, PostDTO> {

    public PostRepository(EntityManagerFactory emf) {
        super(emf, new PersistenceDTOAdaptor());
    }

    @Override
    protected Class<PostEntity> getClazz() {
        return PostEntity.class;
    }
}
