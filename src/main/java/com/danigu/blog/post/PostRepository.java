package com.danigu.blog.post;

import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transaction;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author dani
 */
@AllArgsConstructor
public class PostRepository {
    private final EntityManager em;

    List<PostEntity> getAll() {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        List<PostEntity> result = em.createQuery("FROM PostEntity", PostEntity.class).getResultList();
        tx.commit();
        return result;
    }

    PostEntity getById(long id) {
        checkNotNull(id);
        return em.find(PostEntity.class, id);
    }

    void persist(PostEntity entity) {
        em.persist(entity);
    }

    void remove(PostEntity entity) {
        em.remove(entity);
    }
}
