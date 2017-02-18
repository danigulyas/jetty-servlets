package com.danigu.blog.common.persistence;

import com.danigu.blog.common.TwoWayTransformer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Generic repository class.
 * @param <E> Entity
 * @param <D> DTO
 */
public abstract class CommonRepository<E, D> {
    private final TwoWayTransformer<E, D> adaptor;
    private final EntityManagerFactory emf;

    protected CommonRepository(EntityManagerFactory emf, TwoWayTransformer<E, D> adaptor) {
        checkNotNull(emf);
        checkNotNull(adaptor);
        this.emf = emf;
        this.adaptor = adaptor;
    }

    public D getById(long id) {
        checkNotNull(id);
        return adaptor.convert(getEntityManager().find(getClazz(), id));
    }

    public List<D> getAll() {
        EntityManager em = getEntityManager();

        //TODO(dani): this is ugly, clean this up.
        CriteriaQuery<E> cq = em.getCriteriaBuilder().createQuery(getClazz());
        cq.select(cq.from(getClazz()));
        List<E> result = em.createQuery(cq).getResultList();

        return result.stream().map(adaptor::convert).collect(Collectors.toList());
    }

    public D save(D entity) {
        return adaptor.convert(getEntityManager().merge(adaptor.convertFrom(entity)));
    }

    public void remove(D dto) {
        getEntityManager().remove(adaptor.convertFrom(dto));
    }

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    };

    protected abstract Class<E> getClazz();
}
