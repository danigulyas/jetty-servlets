package com.danigu.blog.common.service;

import com.danigu.blog.common.TwoWayTransformer;
import com.danigu.blog.common.persistence.CommonRepository;
import javassist.NotFoundException;

import java.util.List;
import java.util.stream.Collector;
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
    protected final TwoWayTransformer<E, D> transformer;
    protected final CommonRepository<E> repository;

    public CommonService(CommonRepository<E> repository, TwoWayTransformer<E, D> transformer) {
        checkNotNull(repository);
        checkNotNull(transformer);

        this.repository = repository;
        this.transformer = transformer;
    }

    public List<D> getAll() {
        return repository.getAll().stream().map(transformer::convert).collect(Collectors.toList());
    }

    public D getById(long id) throws IllegalArgumentException {
        if(isNull(id)) throw new IllegalArgumentException("Id can't be null.");

        return transformer.convert(repository.getById(id));
    }

    public void deleteById(long id) throws NotFoundException {
        if(isNull(id)) throw new NotFoundException("No " + getClazz().getName() + " with id 'null'.");

        E entity = repository.getById(id);
        if(isNull(entity)) throw new NotFoundException(getClazz().getName() + " not found.");

        repository.remove(entity);
    }

    protected abstract Class<E> getClazz();
}
