package com.danigu.blog.common;

/**
 * One way adaptor for conversion between DTO's and implementations.
 * @author dani
 */
public interface OneWayAdaptor<From, To> {
    To convert(From from);
}
