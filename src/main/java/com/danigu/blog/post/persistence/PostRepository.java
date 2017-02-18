package com.danigu.blog.post.persistence;

import com.danigu.blog.common.persistence.CommonRepository;

import javax.persistence.EntityManagerFactory;

/**
 * DAO for post, it's reponsibilities:
 * - Conversion between the DTO {@link PostDTO} and the Entity of the persistence implementation {@link PostEntity}
 * - Interaction with the database
 * @see CommonRepository
 * @author dani
 */
public class PostRepository extends CommonRepository<PostEntity, PostDTO> {

    public PostRepository(EntityManagerFactory emf) {
        super(emf, new PersistenceDTOTransformer());
    }

    @Override
    protected Class<PostEntity> getClazz() {
        return PostEntity.class;
    }
}
