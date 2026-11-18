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


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.boot.jackson.autoconfigure.JacksonProperties;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import tools.jackson.databind.DefaultTyping;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;


/**
 * Utilities for jackson redis
 *
 * @author David Hsing
 */
@SuppressWarnings("unused")
public abstract class JacksonRedisWraps {
    /**
     * Create a JSON Redis serializer with polymorphic type handling.
     *
     * @return The configured JSON Redis serializer
     */
    @Nonnull
    public static JacksonJsonRedisSerializer<Object> jsonObjectSerializer() {
        return jsonObjectSerializer(null);
    }

    /**
     * Create a JSON Redis serializer with polymorphic type handling.
     *
     * @param properties The Jackson properties, may be {@code null}
     *
     * @return The configured JSON Redis serializer
     */
    @Nonnull
    public static JacksonJsonRedisSerializer<Object> jsonObjectSerializer(@Nullable JacksonProperties properties) {
        ObjectMapper mapper = ((JsonMapper) JacksonConfigWraps.jsonObjectMapper(properties)).rebuild()
                .changeDefaultVisibility(visibility -> visibility.withVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY))
                .activateDefaultTyping(BasicPolymorphicTypeValidator.builder().allowIfBaseType(Object.class).build(), DefaultTyping.NON_FINAL)
                .build();
        return new JacksonJsonRedisSerializer<>(mapper, Object.class);
    }

    /**
     * Create a Redis template using generic JSON serializers.
     *
     * @param factory The Redis connection factory, may be {@code null}
     */
    public static RedisTemplate<Object, Object> genericObjectRedisTemplate(@Nullable RedisConnectionFactory factory) {
        RedisSerializer<Object> serializer = RedisSerializer.json();
        return objectObjectRedisTemplate(factory, serializer, serializer);
    }

    /**
     * Create a Redis template with object key and value serializers.
     *
     * @param factory The Redis connection factory, may be {@code null}
     */
    public static RedisTemplate<Object, Object> objectObjectRedisTemplate(@Nullable RedisConnectionFactory factory) {
        return objectObjectRedisTemplate(factory, null);
    }

    /**
     * Create a Redis template with object key and value serializers.
     *
     * @param factory The Redis connection factory, may be {@code null}
     * @param properties The Jackson properties, may be {@code null}
     */
    public static RedisTemplate<Object, Object> objectObjectRedisTemplate(@Nullable RedisConnectionFactory factory, @Nullable JacksonProperties properties) {
        RedisSerializer<Object> serializer = jsonObjectSerializer(properties);
        return objectObjectRedisTemplate(factory, serializer, serializer);
    }

    /**
     * Create a Redis template with object key and value serializers.
     *
     * @param factory The Redis connection factory, may be {@code null}
     * @param keySerializer The key serializer, may be {@code null}
     * @param valueSerializer The value serializer, may be {@code null}
     */
    @Nullable
    public static RedisTemplate<Object, Object> objectObjectRedisTemplate(@Nullable RedisConnectionFactory factory, @Nullable RedisSerializer<Object> keySerializer, @Nullable RedisSerializer<Object> valueSerializer) {
        return RedisConfigWraps.redisTemplate(factory, keySerializer, valueSerializer);
    }

    /**
     * Create a Redis template with string keys and object values.
     *
     * @param factory The Redis connection factory, may be {@code null}
     */
    public static RedisTemplate<String, Object> stringObjectRedisTemplate(@Nullable RedisConnectionFactory factory) {
        return stringObjectRedisTemplate(factory, null);
    }

    /**
     * Create a Redis template with string keys and object values.
     *
     * @param factory The Redis connection factory, may be {@code null}
     * @param properties The Jackson properties, may be {@code null}
     */
    public static RedisTemplate<String, Object> stringObjectRedisTemplate(@Nullable RedisConnectionFactory factory, @Nullable JacksonProperties properties) {
        RedisSerializer<String> keySerializer = RedisSerializer.string();
        RedisSerializer<Object> valueSerializer = jsonObjectSerializer(properties);
        return stringObjectRedisTemplate(factory, keySerializer, valueSerializer);
    }

    /**
     * Create a Redis template with string keys and object values.
     *
     * @param factory The Redis connection factory, may be {@code null}
     * @param keySerializer The key serializer, may be {@code null}
     * @param valueSerializer The value serializer, may be {@code null}
     */
    @Nullable
    public static RedisTemplate<String, Object> stringObjectRedisTemplate(@Nullable RedisConnectionFactory factory, @Nullable RedisSerializer<String> keySerializer, @Nullable RedisSerializer<Object> valueSerializer) {
        return RedisConfigWraps.redisTemplate(factory, keySerializer, valueSerializer);
    }
}
