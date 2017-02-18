package com.danigu.blog.common;

/**
 * Interface for one way transformer.
 * @param <F> from
 * @param <T> to
 */
public interface OneWayTransformer<F, T> {
    T convert(F from);
}
