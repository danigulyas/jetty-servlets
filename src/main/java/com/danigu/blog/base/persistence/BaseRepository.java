package com.danigu.blog.base.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Generic repository class.
 * @param <E> HasId
 */
public class BaseRepository<E extends HasId> {
    protected final Class<E> clazz;
    protected final EntityManagerFactory emf;

    protected BaseRepository(EntityManagerFactory emf) {
        checkNotNull(emf);

        // Resolve class bound to template parameter in runtime.
        this.clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.emf = emf;
    }

    public E getById(long id) {
        checkNotNull(id);
        return getEntityManager().find(clazz, id);
    }

    public List<E> getAll() {
        EntityManager em = getEntityManager();

        //TODO(dani): this is ugly, clean this up.
        CriteriaQuery<E> cq = em.getCriteriaBuilder().createQuery(clazz);
        cq.select(cq.from(clazz));

        return em.createQuery(cq).getResultList();
    }

    public E save(E entity) {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();

        return entity;
    }

    public void remove(E entity) {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();
        em.remove(em.getReference(clazz, entity.getId()));
        em.getTransaction().commit();
    }

    public void removeAll(List<E> entities) {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();

        entities
                .stream()
                .map(entity -> em.getReference(clazz, entity.getId()))
                .forEach(em::remove);

        em.getTransaction().commit();
    }

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    };
}
