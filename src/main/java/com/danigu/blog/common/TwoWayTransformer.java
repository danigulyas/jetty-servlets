package com.danigu.blog.common;

/**
 * Interface for two-way transformer.
 * @param <F> from
 * @param <T> to
 */
public interface TwoWayTransformer<F, T> extends OneWayTransformer<F, T> {
    T convert(F from);
    F convertFrom(T to);
}
