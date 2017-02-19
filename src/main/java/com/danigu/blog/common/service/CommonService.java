package com.danigu.blog.common.service;

import com.danigu.blog.common.persistence.CommonRepository;
import javassist.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.isNull;

/**
 * Generic service.
 * @param <E> Entity
 * @param <D> DTO
 */
public abstract class CommonService<E, D> {
    /**
     * Responsible for converting Entities to DTO's.
     */
    protected final Transformer<E, D> transformer;
    protected final CommonRepository<E> repository;

    public CommonService(CommonRepository<E> repository, Transformer<E, D> transformer) {
        checkNotNull(repository);
        checkNotNull(transformer);

        this.repository = repository;
        this.transformer = transformer;
    }

    public List<D> getAll() {
        return repository.getAll().stream().map(transformer::toEntity).collect(Collectors.toList());
    }

    public D getById(Long id) {
        checkNotNull(id);
        return transformer.toEntity(repository.getById(id));
    }

    public void deleteById(Long id) throws NotFoundException {
        if(isNull(id)) throw new NotFoundException("No " + getEntityClazz().getName() + " with id 'null'.");

        E entity = repository.getById(id);
        if(isNull(entity)) throw new NotFoundException(getEntityClazz().getName() + " not found.");

        repository.remove(entity);
    }

    protected abstract Class<E> getEntityClazz();
}
