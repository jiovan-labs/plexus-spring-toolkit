/*
 * Copyright (c) 2026 Jiovan Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jiovan.plexus.spring.toolkit.support;


import java.util.stream.Stream;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.ObjectProvider;


/**
 * Singleton item for {@link org.springframework.beans.factory.ObjectProvider}
 *
 * @param <T> the type of the item to hold
 *
 * @author David Hsing
 *
 * @see org.springframework.util.function.SingletonSupplier
 * @see org.springframework.beans.factory.support.StaticListableBeanFactory
 */
@SuppressWarnings("unused")
public class SingletonObjectProvider<T> implements ObjectProvider<T> {
    /**
     * A shared empty provider instance.
     */
    private static final SingletonObjectProvider<?> EMPTY = new SingletonObjectProvider<>(null);
    /**
     * The value held by this provider.
     */
    private final T value;

    /**
     * Create a SingletonObjectProvider holding the given value.
     *
     * @param value The value to hold
     */
    private SingletonObjectProvider(@Nullable T value) {
        this.value = value;
    }

    /**
     * Returns a shared empty provider instance.
     *
     * @return A shared empty provider
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static <T> SingletonObjectProvider<T> empty() {
        return (SingletonObjectProvider<T>) EMPTY;
    }

    /**
     * Create a provider holding the given non-null values.
     *
     * @param value The value to hold
     *
     * @return A provider holding the given values
     */
    @Nonnull
    public static <T> SingletonObjectProvider<T> of(@Nonnull T value) {
        return new SingletonObjectProvider<>(value);
    }

    /**
     * Create a provider holding the given values, which may include {@code null}.
     *
     * @param value The value to hold
     *
     * @return A provider holding the given values
     */
    @Nonnull
    public static <T> SingletonObjectProvider<T> ofNullable(@Nullable T value) {
        return new SingletonObjectProvider<>(value);
    }

    /**
     * Returns the held object, throwing an exception when none is available.
     *
     * @return The held object
     *
     * @throws BeansException If the operation fails
     */
    @Nonnull
    @Override
    public T getObject() throws BeansException {
        if (value == null) {
            throw new FatalBeanException("No inside object available");
        }
        return value;
    }

    /**
     * Returns the held object, throwing an exception when none is available.
     *
     * @param args Optional arguments
     *
     * @return The held object
     *
     * @throws BeansException If the operation fails
     */
    @Nonnull
    @Override
    public T getObject(@Nullable Object... args) throws BeansException {
        return getObject();
    }

    /**
     * Returns the held object if one is available.
     *
     * @return The held object, or {@code null} when unavailable
     */
    @Nullable
    @Override
    public T getIfAvailable() {
        return value;
    }

    /**
     * Returns the held object when it is unique.
     *
     * @return The held object, or {@code null} when not unique
     */
    @Nullable
    @Override
    public T getIfUnique() {
        return value;
    }

    /**
     * Returns whether a value is present.
     *
     * @return Whether a value is present
     */
    public boolean isPresent() {
        return value != null;
    }

    /**
     * Returns whether no value is present.
     *
     * @return Whether no value is present
     */
    public boolean isNotPresent() {
        return !isPresent();
    }

    /**
     * Returns a sequential stream over the held values.
     *
     * @return A sequential stream over the held values
     */
    @Nonnull
    @Override
    public Stream<T> stream() {
        return (value == null) ? Stream.empty() : Stream.of(value);
    }

    /**
     * Returns a sorted stream over the held values.
     *
     * @return A sorted stream over the held values
     */
    @Nonnull
    @Override
    public Stream<T> orderedStream() {
        return stream();
    }
}
