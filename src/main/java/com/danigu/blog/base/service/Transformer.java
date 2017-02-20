package com.danigu.blog.base.service;

/**
 * Interface for two-way transformer.
 * @param <E> HasId
 * @param <D> DTO
 */
public interface Transformer<E, D> {
    E fromEntity(D from);
    D toEntity(E from);
}
