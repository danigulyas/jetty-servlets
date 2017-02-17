package com.danigu.blog;

import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author dani
 */
public abstract class CommonRepository<T> {
    private final EntityManagerFactory emf;

    protected CommonRepository(EntityManagerFactory emf) {
        checkNotNull(emf);
        this.emf = emf;
    }

    public T getById(long id) {
        checkNotNull(id);
        return getEntityManager().find(getClazz(), id);
    }

    public List<T> getAll() {
        EntityManager em = getEntityManager();
        return em.createQuery(em.getCriteriaBuilder().createQuery(getClazz())).getResultList();
    }

    public void persist(T entity) {
        getEntityManager().persist(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(entity);
    }

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    };

    protected abstract Class<T> getClazz();
}
