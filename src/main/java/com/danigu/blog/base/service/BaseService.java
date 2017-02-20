package com.danigu.blog.base.service;

import com.danigu.blog.base.persistence.BaseRepository;
import javassist.NotFoundException;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.isNull;

/**
 * Generic service.
 * @param <E> Entity
 * @param <D> DTO
 */
public class BaseService<E, D> {
    /**
     * Responsible for converting Entities to DTO's.
     */
    protected final Transformer<E, D> transformer;
    protected final BaseRepository<E> repository;
    protected final Class<E> clazz;
    protected final Class<D> dtoClazz;

    public BaseService(BaseRepository<E> repository, Transformer<E, D> transformer) {
        checkNotNull(repository);
        checkNotNull(transformer);

        // Resolve class bound to template parameter in runtime.
        this.clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.dtoClazz = (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
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
        if(isNull(id)) throw new NotFoundException("No " + dtoClazz.getName() + " with id 'null'.");

        E entity = repository.getById(id);
        if(isNull(entity)) throw new NotFoundException(dtoClazz.getName() + " not found.");

        repository.remove(entity);
    }
}
