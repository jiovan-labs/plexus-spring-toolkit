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


import java.io.Serializable;
import jakarta.annotation.Nullable;
import org.springframework.util.SerializationUtils;


/**
 * Utilities for {@link org.springframework.util.SerializationUtils}
 *
 * @author David Hsing
 *
 * @see org.springframework.util.SerializationUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class SerializationUtilsWraps {
    /**
     * Serialize the given object into a byte array.
     *
     * @param object The object, may be {@code null}
     *
     * @return The serialized bytes, or {@code null}
     */
    @Nullable
    public static byte[] serialize(@Nullable Object object) {
        try {
            return SerializationUtils.serialize(object);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Create a copy of the given object through serialization.
     *
     * @param object The object, may be {@code null}
     *
     * @return The cloned object, or {@code null}
     */
    @Nullable
    public static <T extends Serializable> T clone(T object) {
        try {
            return SerializationUtils.clone(object);
        } catch (Exception ignored) {
        }
        return null;
    }
}
