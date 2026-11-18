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


import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import com.jiovan.plexus.common.toolkit.util.ArrayUtilsWraps;
import com.jiovan.plexus.common.toolkit.util.DurationUtilsWraps;
import com.jiovan.plexus.common.toolkit.util.ObjectUtilsWraps;


/**
 * Utilities for operating {@link org.springframework.data.redis.core.RedisTemplate}
 *
 * @author David Hsing
 *
 * @see org.springframework.data.redis.core.RedisTemplate
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class RedisTemplateWraps {
    /**
     * Count key.
     *
     * @param template The given template
     * @param keys The given keys
     *
     * @return The result of the operation
     */
    @SafeVarargs
    public static <K> long countKey(@Nullable RedisTemplate<K, ?> template, @Nullable K... keys) {
        return countKey(template, ArrayUtilsWraps.asList(keys));
    }

    /**
     * Count key.
     *
     * @param template The given template
     * @param keys The given keys
     *
     * @return The result of the operation
     */
    public static <K> long countKey(@Nullable RedisTemplate<K, ?> template, @Nullable Collection<K> keys) {
        if (template == null || CollectionUtils.isEmpty(keys)) {
            return 0L;
        }
        return keys.stream().filter(item -> Objects.nonNull(item) && template.hasKey(item)).count();
    }

    /**
     * Count pattern.
     *
     * @param template The given template
     * @param patterns The given patterns
     *
     * @return The result of the operation
     */
    @SafeVarargs
    public static <K> long countPattern(@Nullable RedisTemplate<K, ?> template, @Nullable K... patterns) {
        return countPattern(template, ArrayUtilsWraps.asList(patterns));
    }

    /**
     * Returns the number of matched keys which scanned by the given patterns
     *
     * <p>
     * Avoid using {@link org.springframework.data.redis.core.RedisTemplate#keys(Object)} in production environments:
     * The above command traverses all keys and may cause performance issues.
     * It is recommended to use the SCAN command instead.
     *
     * <p>
     * ScanOptions.match(pattern) supports wildcards:
     * <ul>
     *     <li>*: Matches any number of characters;</li>
     *     <li>?: Matches a single character;</li>
     *     <li>[...]: Matches any one character inside the brackets.</li>
     * </ul>
     *
     * @param template The Redis template to interact
     * @param patterns The patterns to match
     *
     * @return the number of matched keys which scanned by the given patterns
     */
    @SuppressWarnings("DuplicatedCode")
    public static <K> long countPattern(@Nullable RedisTemplate<K, ?> template, @Nullable Collection<K> patterns) {
        if (template == null || CollectionUtils.isEmpty(patterns)) {
            return 0L;
        }
        RedisConnection connection = getConnection(template);
        if (connection == null) {
            return 0L;
        }
        long result = 0L;
        for (K pattern : patterns) {
            Set<byte[]> keys = new HashSet<>();
            ScanOptions options = ScanOptions.scanOptions().match(ObjectUtils.getDisplayString(pattern)).build();
            try (Cursor<byte[]> cursor = connection.keyCommands().scan(options)) {
                while (cursor.hasNext()) {
                    keys.add(cursor.next());
                }
            }
            result += keys.size();
        }
        return result;
    }

    /**
     * Delete the value stored under the given key.
     *
     * @param template The given template
     * @param keys The given keys
     *
     * @return The result of the operation
     */
    @SafeVarargs
    public static <K> long deleteKey(@Nullable RedisTemplate<K, ?> template, @Nullable K... keys) {
        return deleteKey(template, ArrayUtilsWraps.asList(keys));
    }

    /**
     * Delete the value stored under the given key.
     *
     * @param template The given template
     * @param keys The given keys
     *
     * @return The result of the operation
     */
    public static <K> long deleteKey(@Nullable RedisTemplate<K, ?> template, @Nullable Collection<K> keys) {
        if (template == null || CollectionUtils.isEmpty(keys)) {
            return 0L;
        }
        long result = 0L;
        for (K key : keys) {
            if (key != null && BooleanUtils.isTrue(template.delete(key))) {
                result++;
            }
        }
        return result;
    }

    /**
     * Delete all keys matching the given pattern.
     *
     * @param template The given template
     * @param patterns The given patterns
     *
     * @return The result of the operation
     */
    @SafeVarargs
    public static <K> long deletePattern(@Nullable RedisTemplate<K, ?> template, @Nullable K... patterns) {
        return deletePattern(template, ArrayUtilsWraps.asList(patterns));
    }

    /**
     * Returns the number of deleted keys which scanned by the given patterns
     *
     * <p>
     * Avoid using {@link org.springframework.data.redis.core.RedisTemplate#keys(Object)} in production environments:
     * The above command traverses all keys and may cause performance issues.
     * It is recommended to use the SCAN command instead.
     *
     * <p>
     * ScanOptions.match(pattern) supports wildcards:
     * <ul>
     *     <li>*: Matches any number of characters;</li>
     *     <li>?: Matches a single character;</li>
     *     <li>[...]: Matches any one character inside the brackets.</li>
     * </ul>
     *
     * @param template The Redis template to interact
     * @param patterns The patterns to match
     *
     * @return the number of deleted keys which scanned by the given patterns
     */
    @SuppressWarnings("DuplicatedCode")
    public static <K> long deletePattern(@Nullable RedisTemplate<K, ?> template, @Nullable Collection<K> patterns) {
        if (template == null || CollectionUtils.isEmpty(patterns)) {
            return 0L;
        }
        RedisConnection connection = getConnection(template);
        if (connection == null) {
            return 0L;
        }
        long result = 0L;
        for (K pattern : patterns) {
            Set<byte[]> keys = new HashSet<>();
            ScanOptions options = ScanOptions.scanOptions().match(ObjectUtils.getDisplayString(pattern)).build();
            try (Cursor<byte[]> cursor = connection.keyCommands().scan(options)) {
                while (cursor.hasNext()) {
                    keys.add(cursor.next());
                }
            }
            if (!keys.isEmpty()) {
                Long deleted = connection.keyCommands().del(keys.toArray(new byte[0][]));
                if (deleted != null && deleted > 0) {
                    result += deleted;
                }
            }
        }
        return result;
    }

    /**
     * Exists key.
     *
     * @param template The given template
     * @param key The key, may be {@code null}
     *
     * @return Whether the condition holds
     */
    @SuppressWarnings("DataFlowIssue")
    public static <K> boolean existsKey(@Nullable RedisTemplate<K, ?> template, @Nullable K key) {
        return ObjectUtilsWraps.allNotNull(template, key) && BooleanUtils.isTrue(template.hasKey(key));
    }

    /**
     * Exists all keys.
     *
     * @param template The given template
     * @param keys The given keys
     *
     * @return Whether the condition holds
     */
    @SafeVarargs
    public static <K> boolean existsAllKeys(@Nullable RedisTemplate<K, ?> template, @Nullable K... keys) {
        return existsAllKeys(template, ArrayUtilsWraps.asList(keys));
    }

    /**
     * Exists all keys.
     *
     * @param template The given template
     * @param keys The given keys
     *
     * @return Whether the condition holds
     */
    public static <K> boolean existsAllKeys(@Nullable RedisTemplate<K, ?> template, @Nullable Collection<K> keys) {
        return template != null && !CollectionUtils.isEmpty(keys) && keys.stream().filter(Objects::nonNull).allMatch(key -> existsKey(template, key));
    }

    /**
     * Exists any keys.
     *
     * @param template The given template
     * @param keys The given keys
     *
     * @return Whether the condition holds
     */
    @SafeVarargs
    public static <K> boolean existsAnyKeys(@Nullable RedisTemplate<K, ?> template, @Nullable K... keys) {
        return existsAnyKeys(template, ArrayUtilsWraps.asList(keys));
    }

    /**
     * Exists any keys.
     *
     * @param template The given template
     * @param keys The given keys
     *
     * @return Whether the condition holds
     */
    public static <K> boolean existsAnyKeys(@Nullable RedisTemplate<K, ?> template, @Nullable Collection<K> keys) {
        return template != null && !CollectionUtils.isEmpty(keys) && keys.stream().filter(Objects::nonNull).anyMatch(key -> existsKey(template, key));
    }

    /**
     * Return the connection.
     *
     * @param template The given template
     *
     * @return The result of the operation
     */
    @Nullable
    public static <K, V> RedisConnection getConnection(@Nullable RedisTemplate<K, V> template) {
        if (template == null) {
            return null;
        }
        RedisConnectionFactory factory = template.getConnectionFactory();
        return (factory == null) ? null : factory.getConnection();
    }

    /**
     * Return the value stored under the given key.
     *
     * @param template The given template
     * @param key The key, may be {@code null}
     *
     * @return The stored value, or {@code null}
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static <K, V> V getValue(@Nullable RedisTemplate<K, V> template, @Nullable K key) {
        return ObjectUtilsWraps.anyNull(template, key) ? null : template.opsForValue().get(key);
    }

    /**
     * Return the stored value converted to the expected type.
     *
     * @param template The given template
     * @param key The key, may be {@code null}
     * @param expectType The expected type, may be {@code null}
     *
     * @return The converted value, or {@code null}
     */
    @Nullable
    public static <K, T> T getValueAs(@Nullable RedisTemplate<K, ?> template, @Nullable K key, @Nullable Class<T> expectType) {
        return ObjectUtilsWraps.castAs(getValue(template, key), expectType);
    }

    /**
     * Return and remove the value stored under the given key.
     *
     * @param template The given template
     * @param key The key, may be {@code null}
     *
     * @return The value and then deleted, or {@code null}
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static <K, V> V getValueAndDelete(@Nullable RedisTemplate<K, V> template, @Nullable K key) {
        return ObjectUtilsWraps.anyNull(template, key) ? null : template.opsForValue().getAndDelete(key);
    }

    /**
     * Return the value and refresh its expiration.
     *
     * @param template The given template
     * @param key The key, may be {@code null}
     * @param timeout The timeout, may be {@code null}
     * @param unit The time unit, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static <K, V> V getValueAndExpire(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable Long timeout, @Nullable ChronoUnit unit) {
        return getValueAndExpire(template, key, DurationUtilsWraps.ofChronoUnit(timeout, unit));
    }

    /**
     * Return the value and refresh its expiration.
     *
     * @param template The given template
     * @param key The key, may be {@code null}
     * @param timeout The timeout, may be {@code null}
     * @param unit The time unit, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static <K, V> V getValueAndExpire(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable Long timeout, @Nullable TimeUnit unit) {
        return getValueAndExpire(template, key, DurationUtilsWraps.ofTimeUnit(timeout, unit));
    }

    /**
     * Return the value and refresh its expiration.
     *
     * @param template The given template
     * @param key The key, may be {@code null}
     * @param timeout The timeout, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static <K, V> V getValueAndExpire(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable Duration timeout) {
        if (ObjectUtilsWraps.anyNull(template, key)) {
            return null;
        }
        return (timeout == null) ? template.opsForValue().get(key) : template.opsForValue().getAndExpire(key, timeout);
    }

    /**
     * Return the value and persist it.
     *
     * @param template The given template
     * @param key The key, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static <K, V> V getValueAndPersist(@Nullable RedisTemplate<K, V> template, @Nullable K key) {
        return ObjectUtilsWraps.anyNull(template, key) ? null : template.opsForValue().getAndPersist(key);
    }

    /**
     * Return the value and update it.
     *
     * @param template The given template
     * @param key The key, may be {@code null}
     * @param value The value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static <K, V> V getValueAndSet(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value) {
        return ObjectUtilsWraps.anyNull(template, key) ? null : template.opsForValue().getAndSet(key, value);
    }

    /**
     * Set the value.
     *
     * @param template The given template
     * @param key The key, may be {@code null}
     * @param value The value, may be {@code null}
     */
    @SuppressWarnings("DataFlowIssue")
    public static <K, V> void setValue(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value) {
        if (ObjectUtilsWraps.allNotNull(template, key)) {
            template.opsForValue().set(key, value);
        }
    }

    /**
     * Set the value.
     *
     * @param template The given template
     * @param key The key, may be {@code null}
     * @param value The value, may be {@code null}
     * @param timeout The timeout, may be {@code null}
     * @param unit The time unit, may be {@code null}
     */
    public static <K, V> void setValue(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value, @Nullable Long timeout, @Nullable ChronoUnit unit) {
        setValue(template, key, value, DurationUtilsWraps.ofChronoUnit(timeout, unit));
    }

    /**
     * Set the value.
     *
     * @param template The given template
     * @param key The key, may be {@code null}
     * @param value The value, may be {@code null}
     * @param timeout The timeout, may be {@code null}
     * @param unit The time unit, may be {@code null}
     */
    public static <K, V> void setValue(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value, @Nullable Long timeout, @Nullable TimeUnit unit) {
        setValue(template, key, value, DurationUtilsWraps.ofTimeUnit(timeout, unit));
    }

    /**
     * Set the value.
     *
     * @param template The given template
     * @param key The key, may be {@code null}
     * @param value The value, may be {@code null}
     * @param timeout The timeout, may be {@code null}
     */
    @SuppressWarnings("DataFlowIssue")
    public static <K, V> void setValue(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value, @Nullable Duration timeout) {
        if (ObjectUtilsWraps.anyNull(template, key)) {
            return;
        }
        if (timeout == null) {
            template.opsForValue().set(key, value);
        } else {
            template.opsForValue().set(key, value, timeout);
        }
    }

    /**
     * Set a value only when the key is absent.
     *
     * @param template The given template
     * @param key The key, may be {@code null}
     * @param value The value, may be {@code null}
     * @param timeout The timeout, may be {@code null}
     * @param unit The time unit, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static <K, V> boolean setIfAbsent(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value, @Nullable Long timeout, @Nullable TimeUnit unit) {
        return setIfAbsent(template, key, value, DurationUtilsWraps.ofTimeUnit(timeout, unit));
    }

    /**
     * Set a value only when the key is absent.
     *
     * @param template The given template
     * @param key The key, may be {@code null}
     * @param value The value, may be {@code null}
     * @param timeout The timeout, may be {@code null}
     * @param unit The time unit, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static <K, V> boolean setIfAbsent(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value, @Nullable Long timeout, @Nullable ChronoUnit unit) {
        return setIfAbsent(template, key, value, DurationUtilsWraps.ofChronoUnit(timeout, unit));
    }

    /**
     * Set a value only when the key is absent.
     *
     * @param template The given template
     * @param key The key, may be {@code null}
     * @param value The value, may be {@code null}
     * @param timeout The timeout, may be {@code null}
     *
     * @return Whether the condition holds
     */
    @SuppressWarnings("DataFlowIssue")
    public static <K, V> boolean setIfAbsent(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value, @Nullable Duration timeout) {
        if (ObjectUtilsWraps.anyNull(template, key)) {
            return false;
        }
        if (timeout == null) {
            return BooleanUtils.isTrue(template.opsForValue().setIfAbsent(key, value));
        } else {
            return BooleanUtils.isTrue(template.opsForValue().setIfAbsent(key, value, timeout));
        }
    }

    /**
     * Set a value only when the key is present.
     *
     * @param template The given template
     * @param key The key, may be {@code null}
     * @param value The value, may be {@code null}
     * @param timeout The timeout, may be {@code null}
     * @param unit The time unit, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static <K, V> boolean setIfPresent(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value, @Nullable Long timeout, @Nullable TimeUnit unit) {
        return setIfPresent(template, key, value, DurationUtilsWraps.ofTimeUnit(timeout, unit));
    }

    /**
     * Set a value only when the key is present.
     *
     * @param template The given template
     * @param key The key, may be {@code null}
     * @param value The value, may be {@code null}
     * @param timeout The timeout, may be {@code null}
     * @param unit The time unit, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static <K, V> boolean setIfPresent(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value, @Nullable Long timeout, @Nullable ChronoUnit unit) {
        return setIfPresent(template, key, value, DurationUtilsWraps.ofChronoUnit(timeout, unit));
    }

    /**
     * Set a value only when the key is present.
     *
     * @param template The given template
     * @param key The key, may be {@code null}
     * @param value The value, may be {@code null}
     * @param timeout The timeout, may be {@code null}
     *
     * @return Whether the condition holds
     */
    @SuppressWarnings("DataFlowIssue")
    public static <K, V> boolean setIfPresent(@Nullable RedisTemplate<K, V> template, @Nullable K key, @Nullable V value, @Nullable Duration timeout) {
        if (ObjectUtilsWraps.anyNull(template, key)) {
            return false;
        }
        if (timeout == null) {
            return BooleanUtils.isTrue(template.opsForValue().setIfPresent(key, value));
        } else {
            return BooleanUtils.isTrue(template.opsForValue().setIfPresent(key, value, timeout));
        }
    }
}
