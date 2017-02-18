package com.danigu.blog.common.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Generic repository class.
 * @param <E> Entity
 */
public abstract class CommonRepository<E> {
    private final EntityManagerFactory emf;

    protected CommonRepository(EntityManagerFactory emf) {
        checkNotNull(emf);
        this.emf = emf;
    }

    public E getById(long id) {
        checkNotNull(id);
        return getEntityManager().find(getClazz(), id);
    }

    public List<E> getAll() {
        EntityManager em = getEntityManager();

        //TODO(dani): this is ugly, clean this up.
        CriteriaQuery<E> cq = em.getCriteriaBuilder().createQuery(getClazz());
        cq.select(cq.from(getClazz()));

        return em.createQuery(cq).getResultList();
    }

    public E save(E entity) {
        return getEntityManager().merge(entity);
    }

    public void remove(E entity) {
        getEntityManager().remove(entity);
    }

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    };

    protected abstract Class<E> getClazz();
}
