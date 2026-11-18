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


import java.io.InputStream;
import java.nio.charset.Charset;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ClassPathUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import com.jiovan.plexus.common.toolkit.constant.StringVariantConst;
import com.jiovan.plexus.common.toolkit.util.IoUtilsWraps;
import com.jiovan.plexus.common.toolkit.util.StringUtilsWraps;


/**
 * Utilities for {@link org.apache.commons.lang3.ClassPathUtils}
 *
 * @author David Hsing
 *
 * @see org.apache.commons.lang3.ClassPathUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class ClassPathWraps {
    /**
     * Return whether the class-path resource exists.
     *
     * @param path The class-path resource path, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean existsResource(@Nullable String path) {
        return existsResource(path, null);
    }

    /**
     * Return whether the class-path resource exists.
     *
     * @param path The class-path resource path, may be {@code null}
     * @param loader The class loader, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean existsResource(@Nullable String path, @Nullable ClassLoader loader) {
        String pathToUse = removeClasspathPrefix(path);
        return StringUtils.isNotBlank(pathToUse) && new ClassPathResource(pathToUse, loader).exists();
    }

    /**
     * Open an input stream for the class-path resource.
     *
     * @param path The class-path resource path, may be {@code null}
     * @param loader The class loader, may be {@code null}
     *
     * @return An input stream for the resource, or {@code null}
     */
    @Nullable
    public static InputStream readResourceToInputStream(@Nullable String path, @Nullable ClassLoader loader) {
        String pathToUse = removeClasspathPrefix(path);
        ClassPathResource resource = new ClassPathResource(pathToUse, loader);
        try {
            return resource.getInputStream();
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Read the class-path resource into a byte array.
     *
     * @param path The class-path resource path, may be {@code null}
     * @param loader The class loader, may be {@code null}
     *
     * @return The resource bytes, or {@code null}
     */
    @Nullable
    public static byte[] readResourceToByteArray(@Nullable String path, @Nullable ClassLoader loader) {
        try (InputStream stream = readResourceToInputStream(path, loader)) {
            return IoUtilsWraps.toByteArray(stream);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Read the class-path resource into a string using the given charset.
     *
     * @param path The class-path resource path, may be {@code null}
     * @param loader The class loader, may be {@code null}
     * @param charset The charset to use, may be {@code null}
     *
     * @return The resource content as a string, or {@code null}
     */
    @Nullable
    public static String readResourceToString(@Nullable String path, @Nullable ClassLoader loader, @Nullable Charset charset) {
        try (InputStream stream = readResourceToInputStream(path, loader)) {
            return IoUtilsWraps.toString(stream, charset);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Return the path content after the prefix of 'classpath:' or 'classpath*:'
     *
     * @param path The absolute path within the class path
     *
     * @return the path content after the prefix of 'classpath:' or 'classpath*:'
     */
    public static String removeClasspathPrefix(@Nullable String path) {
        return StringUtilsWraps.removeStartIgnoreCase(path, StringVariantConst.CLASSPATH_COLON, StringVariantConst.CLASSPATH_STAR_COLON);
    }

    /**
     * Return whether the path starts with any class-path prefix.
     *
     * @param path The class-path resource path, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean startsWithAnyClasspath(@Nullable String path) {
        return startsWithClasspath(path) || startsWithClasspathStar(path);
    }

    /**
     * Return whether the path starts with the {@code classpath:} prefix.
     *
     * @param path The class-path resource path, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean startsWithClasspath(@Nullable String path) {
        return StringUtilsWraps.startsWithIgnoreCase(path, StringVariantConst.CLASSPATH_COLON);
    }

    /**
     * Return whether the path starts with the {@code classpath*:} prefix.
     *
     * @param path The class-path resource path, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean startsWithClasspathStar(@Nullable String path) {
        return StringUtilsWraps.startsWithIgnoreCase(path, StringVariantConst.CLASSPATH_STAR_COLON);
    }

    /**
     * Return the fully qualified name of a resource relative to the context.
     *
     * @param context The bean factory or invocation context, may be {@code null}
     * @param resourceName The resource name, may be {@code null}
     *
     * @return The fully qualified name, or {@code null}
     */
    @Nullable
    public static String toFullyQualifiedName(@Nullable Class<?> context, @Nullable String resourceName) {
        return (context == null || StringUtils.isBlank(resourceName)) ? null : ClassPathUtils.toFullyQualifiedName(context, resourceName);
    }

    /**
     * Return the fully qualified name of a resource relative to the context.
     *
     * @param context The bean factory or invocation context, may be {@code null}
     * @param resourceName The resource name, may be {@code null}
     *
     * @return The fully qualified name, or {@code null}
     */
    @Nullable
    public static String toFullyQualifiedName(@Nullable Package context, @Nullable String resourceName) {
        return (context == null || StringUtils.isBlank(resourceName)) ? null : ClassPathUtils.toFullyQualifiedName(context, resourceName);
    }

    /**
     * Return the fully qualified path of a resource relative to the context.
     *
     * @param context The bean factory or invocation context, may be {@code null}
     * @param resourceName The resource name, may be {@code null}
     *
     * @return The fully qualified path, or {@code null}
     */
    @Nullable
    public static String toFullyQualifiedPath(@Nullable Class<?> context, @Nullable String resourceName) {
        return (context == null || StringUtils.isBlank(resourceName)) ? null : ClassPathUtils.toFullyQualifiedPath(context, resourceName);
    }

    /**
     * Return the fully qualified path of a resource relative to the context.
     *
     * @param context The bean factory or invocation context, may be {@code null}
     * @param resourceName The resource name, may be {@code null}
     *
     * @return The fully qualified path, or {@code null}
     */
    @Nullable
    public static String toFullyQualifiedPath(@Nullable Package context, @Nullable String resourceName) {
        return (context == null || StringUtils.isBlank(resourceName)) ? null : ClassPathUtils.toFullyQualifiedPath(context, resourceName);
    }
}
