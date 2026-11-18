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
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.ObjectProvider;
import com.jiovan.plexus.common.toolkit.util.ArrayUtilsWraps;


/**
 * Multitude items for {@link org.springframework.beans.factory.ObjectProvider}
 *
 * @param <T> the type of the items to hold
 *
 * @author David Hsing
 *
 * @see org.springframework.beans.factory.support.StaticListableBeanFactory
 */
@SuppressWarnings("unused")
public class MultitudeObjectProvider<T> implements ObjectProvider<T> {
    /**
     * A shared empty provider instance.
     */
    private static final MultitudeObjectProvider<?> EMPTY = new MultitudeObjectProvider<>();
    /**
     * The values held by this provider.
     */
    private final T[] values;

    /**
     * Create a MultitudeObjectProvider holding the given value.
     *
     * @param values The values to hold
     */
    @SafeVarargs
    private MultitudeObjectProvider(@Nullable T... values) {
        this.values = values;
    }

    /**
     * Returns a shared empty provider instance.
     *
     * @return A shared empty provider
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static <T> MultitudeObjectProvider<T> empty() {
        return (MultitudeObjectProvider<T>) EMPTY;
    }

    /**
     * Create a provider holding the given non-null values.
     *
     * @param values The values to hold
     *
     * @return A provider holding the given values
     */
    @Nonnull
    @SafeVarargs
    public static <T> MultitudeObjectProvider<T> of(@Nonnull T... values) {
        return new MultitudeObjectProvider<>(values);
    }

    /**
     * Create a provider holding the given values, which may include {@code null}.
     *
     * @param values The values to hold
     *
     * @return A provider holding the given values
     */
    @Nonnull
    @SafeVarargs
    public static <T> MultitudeObjectProvider<T> ofNullable(@Nullable T... values) {
        return new MultitudeObjectProvider<>(values);
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
    @SuppressWarnings("DataFlowIssue")
    public T getObject() throws BeansException {
        if (ArrayUtils.isEmpty(values)) {
            throw new FatalBeanException("No inside objects available");
        }
        return ArrayUtilsWraps.getFirst(values);
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
        return ArrayUtilsWraps.getFirst(values);
    }

    /**
     * Returns the held object when it is unique.
     *
     * @return The held object, or {@code null} when not unique
     */
    @Nullable
    @Override
    public T getIfUnique() {
        return (ArrayUtils.getLength(values) == 1) ? ArrayUtilsWraps.getFirst(values) : null;
    }

    /**
     * Returns whether a value is present.
     *
     * @return Whether a value is present
     */
    public boolean isPresent() {
        return ArrayUtils.isNotEmpty(values);
    }

    /**
     * Returns whether no value is present.
     *
     * @return Whether no value is present
     */
    public boolean isNotPresent() {
        return ArrayUtils.isEmpty(values);
    }

    /**
     * Returns a sequential stream over the held values.
     *
     * @return A sequential stream over the held values
     */
    @Nonnull
    @Override
    @SuppressWarnings("DataFlowIssue")
    public Stream<T> stream() {
        return ArrayUtils.isEmpty(values) ? Stream.empty() : Stream.of(values);
    }

    /**
     * Returns a sorted stream over the held values.
     *
     * @return A sorted stream over the held values
     */
    @Nonnull
    @Override
    public Stream<T> orderedStream() {
        return stream().sorted();
    }
}
