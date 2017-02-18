package com.danigu.blog.common.persistence;

import com.danigu.blog.common.TwoWayAdaptor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author dani
 */
public abstract class CommonRepository<Entity, DTO> {
    private final TwoWayAdaptor<Entity, DTO> adaptor;
    private final EntityManagerFactory emf;

    protected CommonRepository(EntityManagerFactory emf, TwoWayAdaptor<Entity, DTO> adaptor) {
        checkNotNull(emf);
        checkNotNull(adaptor);
        this.emf = emf;
        this.adaptor = adaptor;
    }

    public DTO getById(long id) {
        checkNotNull(id);
        return adaptor.convert(getEntityManager().find(getClazz(), id));
    }

    public List<DTO> getAll() {
        EntityManager em = getEntityManager();
        List<Entity> result = em.createQuery(em.getCriteriaBuilder().createQuery(getClazz())).getResultList();

        return result.stream().map(adaptor::convert).collect(Collectors.toList());
    }

    public DTO save(DTO entity) {
        return adaptor.convert(getEntityManager().merge(adaptor.convertFrom(entity)));
    }

    public void remove(DTO dto) {
        getEntityManager().remove(adaptor.convertFrom(dto));
    }

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    };

    protected abstract Class<Entity> getClazz();
}
