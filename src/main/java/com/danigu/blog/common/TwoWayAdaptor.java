package com.danigu.blog.common;

/**
 * Adaptor for two-way conversion.
 * @author dani
 */
public interface TwoWayAdaptor<From, To> {
    To convert(From from);
    From convertFrom(To to);
}
