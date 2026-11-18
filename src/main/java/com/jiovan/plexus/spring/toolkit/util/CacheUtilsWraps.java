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

package com.jiovan.plexus.spring.toolkit.util;


import java.util.Collection;
import java.util.concurrent.Callable;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.BasicOperation;
import org.springframework.cache.interceptor.CacheOperation;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;


/**
 * Utilities for {@link org.springframework.cache.CacheManager} and {@link org.springframework.cache.interceptor.CacheResolver}
 *
 * @author David Hsing
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class CacheUtilsWraps {
    /**
     * Return the named cache from the manager.
     *
     * @param manager The cache manager, may be {@code null}
     * @param name The cache name, may be {@code null}
     *
     * @return The named cache, or {@code null}
     */
    @Nullable
    public static Cache getCache(@Nullable CacheManager manager, @Nullable String name) {
        return (manager == null || StringUtils.isEmpty(name)) ? null : manager.getCache(name);
    }

    /**
     * Return the names of all caches managed by the manager.
     *
     * @param manager The cache manager, may be {@code null}
     *
     * @return The cache names, or {@code null}
     */
    @Nullable
    public static Collection<String> getCacheNames(@Nullable CacheManager manager) {
        return (manager == null) ? null : manager.getCacheNames();
    }

    /**
     * Return the raw value stored under the given cache key.
     *
     * @param manager The cache manager, may be {@code null}
     * @param name The cache name, may be {@code null}
     * @param key The cache key, may be {@code null}
     *
     * @return The cached value wrapper, or {@code null}
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Cache.ValueWrapper getCacheValue(@Nullable CacheManager manager, @Nullable String name, @Nullable String key) {
        if (manager == null || StringUtils.isAnyEmpty(name, key)) {
            return null;
        }
        Cache cache = manager.getCache(name);
        return (cache == null) ? null : cache.get(key);
    }

    /**
     * Return the value stored under the given cache key converted to the expected type.
     *
     * @param manager The cache manager, may be {@code null}
     * @param name The cache name, may be {@code null}
     * @param key The cache key, may be {@code null}
     * @param expectType The expected type, may be {@code null}
     *
     * @return The converted cached value, or {@code null}
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static <T> T getCacheValueAs(@Nullable CacheManager manager, @Nullable String name, @Nullable String key, @Nullable Class<T> expectType) {
        if (ObjectUtils.anyNull(manager, expectType) || StringUtils.isAnyEmpty(name, key)) {
            return null;
        }
        Cache cache = manager.getCache(name);
        return (cache == null) ? null : cache.get(key, expectType);
    }

    /**
     * Return the cached value, computing it with the callable when absent.
     *
     * @param manager The cache manager, may be {@code null}
     * @param name The cache name, may be {@code null}
     * @param key The cache key, may be {@code null}
     * @param callable The fallback value supplier, may be {@code null}
     *
     * @return The cached or computed value, or {@code null}
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static <T> T getCacheValueCallable(@Nullable CacheManager manager, @Nullable String name, @Nullable String key, @Nullable Callable<T> callable) {
        if (ObjectUtils.anyNull(manager, callable) || StringUtils.isAnyEmpty(name, key)) {
            return null;
        }
        Cache cache = manager.getCache(name);
        return (cache == null) ? null : cache.get(key, callable);
    }

    /**
     * Return the cache manager referenced by the cache operation or context.
     *
     * @param factory The bean factory to inspect, may be {@code null}
     * @param context The bean factory or invocation context, may be {@code null}
     *
     * @return The resolved cache manager, or {@code null}
     */
    @Nullable
    public static CacheManager getCacheManager(@Nullable BeanFactory factory, @Nullable CacheOperationInvocationContext<?> context) {
        return ObjectUtils.anyNull(factory, context) ? null : getCacheManager(factory, context.getOperation());
    }

    /**
     * Return the cache manager referenced by the cache operation or context.
     *
     * @param factory The bean factory to inspect, may be {@code null}
     * @param operation The cache operation, may be {@code null}
     *
     * @return The resolved cache manager, or {@code null}
     */
    @Nullable
    public static <T extends BasicOperation> CacheManager getCacheManager(@Nullable BeanFactory factory, @Nullable T operation) {
        if (ObjectUtils.anyNull(factory, operation) || !(operation instanceof CacheOperation alias)) {
            return null;
        }
        return BeanFactoryWraps.getBean(factory, alias.getCacheManager(), CacheManager.class);
    }

    /**
     * Return the cache resolver referenced by the cache operation or context.
     *
     * @param factory The bean factory to inspect, may be {@code null}
     * @param context The bean factory or invocation context, may be {@code null}
     *
     * @return The resolved cache resolver, or {@code null}
     */
    @Nullable
    public static CacheResolver getCacheResolver(@Nullable BeanFactory factory, @Nullable CacheOperationInvocationContext<?> context) {
        return ObjectUtils.anyNull(factory, context) ? null : getCacheResolver(factory, context.getOperation());
    }

    /**
     * Return the cache resolver referenced by the cache operation or context.
     *
     * @param factory The bean factory to inspect, may be {@code null}
     * @param operation The cache operation, may be {@code null}
     *
     * @return The resolved cache resolver, or {@code null}
     */
    @Nullable
    public static <T extends BasicOperation> CacheResolver getCacheResolver(@Nullable BeanFactory factory, @Nullable T operation) {
        if (ObjectUtils.anyNull(factory, operation) || !(operation instanceof CacheOperation alias)) {
            return null;
        }
        return BeanFactoryWraps.getBean(factory, alias.getCacheResolver(), CacheResolver.class);
    }
}
