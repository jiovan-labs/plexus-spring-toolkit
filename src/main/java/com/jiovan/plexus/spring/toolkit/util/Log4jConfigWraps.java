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


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ClassUtils;


/**
 * Utilities for configuring {@link org.apache.logging.log4j.core.LoggerContext}
 *
 * @author David Hsing
 *
 * @see org.apache.logging.log4j.core.LoggerContext
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class Log4jConfigWraps {
    /**
     * Initialize a Log4j context from an XML configuration on the class path.
     *
     * @param xmlPath The path to the XML configuration, may be {@code null}
     *
     * @return The initialized logger context, or {@code null}
     *
     * @throws IOException If the operation fails
     */
    @Nullable
    public static LoggerContext initContextByClassPath(@Nullable String xmlPath) throws IOException {
        return initContextByClassPath(xmlPath, null);
    }

    /**
     * Initialize a Log4j context from an XML configuration on the class path.
     *
     * @param xmlPath The path to the XML configuration, may be {@code null}
     * @param loader The class loader, may be {@code null}
     *
     * @return The initialized logger context, or {@code null}
     *
     * @throws IOException If the operation fails
     */
    @Nullable
    public static LoggerContext initContextByClassPath(@Nullable String xmlPath, @Nullable ClassLoader loader) throws IOException {
        if (StringUtils.isBlank(xmlPath)) {
            return null;
        }
        ClassPathResource resource = new ClassPathResource(xmlPath);
        if (resource.exists()) {
            ConfigurationSource source = new ConfigurationSource(resource.getInputStream());
            return Configurator.initialize(ObjectUtils.getIfNull(loader, ClassUtils.getDefaultClassLoader()), source);
        }
        return null;
    }

    /**
     * Initialize a Log4j context from the class path, returning {@code null} on failure.
     *
     * @param xmlPath The path to the XML configuration, may be {@code null}
     *
     * @return The initialized logger context, or {@code null}
     */
    @Nullable
    public static LoggerContext initContextByClassPathQuietly(@Nullable String xmlPath) {
        return initContextByClassPathQuietly(xmlPath, null);
    }

    /**
     * Initialize a Log4j context from the class path, returning {@code null} on failure.
     *
     * @param xmlPath The path to the XML configuration, may be {@code null}
     * @param loader The class loader, may be {@code null}
     *
     * @return The initialized logger context, or {@code null}
     */
    @Nullable
    public static LoggerContext initContextByClassPathQuietly(@Nullable String xmlPath, @Nullable ClassLoader loader) {
        try {
            return initContextByClassPath(xmlPath, loader);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Initialize a Log4j context from an XML configuration file.
     *
     * @param xmlFile The XML configuration file, may be {@code null}
     *
     * @return The initialized logger context, or {@code null}
     *
     * @throws IOException If the operation fails
     */
    @Nullable
    public static LoggerContext initContextByFile(@Nullable File xmlFile) throws IOException {
        return initContextByFile(xmlFile, null);
    }

    /**
     * Initialize a Log4j context from an XML configuration file.
     *
     * @param xmlFile The XML configuration file, may be {@code null}
     * @param loader The class loader, may be {@code null}
     *
     * @return The initialized logger context, or {@code null}
     *
     * @throws IOException If the operation fails
     */
    @Nullable
    public static LoggerContext initContextByFile(@Nullable File xmlFile, @Nullable ClassLoader loader) throws IOException {
        if (xmlFile == null || !xmlFile.exists() || !xmlFile.isFile() || xmlFile.canRead()) {
            return null;
        }
        try (InputStream stream = Files.newInputStream(xmlFile.toPath())) {
            return initContextByStream(stream, loader);
        }
    }

    /**
     * Initialize a Log4j context from an XML configuration file.
     *
     * @param xmlPath The path to the XML configuration, may be {@code null}
     *
     * @return The initialized logger context, or {@code null}
     *
     * @throws IOException If the operation fails
     */
    public static LoggerContext initContextByFile(@Nullable String xmlPath) throws IOException {
        return initContextByFile(xmlPath, null);
    }

    /**
     * @see "http://blog.csdn.net/yucaifu1989/article/details/48178281"
     */
    @Nullable
    public static LoggerContext initContextByFile(@Nullable String xmlPath, @Nullable ClassLoader loader) throws IOException {
        if (StringUtils.isBlank(xmlPath)) {
            return null;
        }
        try (InputStream stream = Files.newInputStream(Paths.get(xmlPath))) {
            return initContextByStream(stream, loader);
        }
    }

    /**
     * Initialize a Log4j context from a file, returning {@code null} on failure.
     *
     * @param xmlFile The XML configuration file, may be {@code null}
     *
     * @return The initialized logger context, or {@code null}
     */
    @Nullable
    public static LoggerContext initContextByFileQuietly(@Nullable File xmlFile) {
        return initContextByFileQuietly(xmlFile, null);
    }

    /**
     * Initialize a Log4j context from a file, returning {@code null} on failure.
     *
     * @param xmlFile The XML configuration file, may be {@code null}
     * @param loader The class loader, may be {@code null}
     *
     * @return The initialized logger context, or {@code null}
     */
    @Nullable
    public static LoggerContext initContextByFileQuietly(@Nullable File xmlFile, @Nullable ClassLoader loader) {
        try {
            return initContextByFile(xmlFile, loader);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Initialize a Log4j context from a file, returning {@code null} on failure.
     *
     * @param xmlPath The path to the XML configuration, may be {@code null}
     *
     * @return The initialized logger context, or {@code null}
     */
    @Nullable
    public static LoggerContext initContextByFileQuietly(@Nullable String xmlPath) {
        return initContextByFileQuietly(xmlPath, null);
    }

    /**
     * Initialize a Log4j context from a file, returning {@code null} on failure.
     *
     * @param xmlPath The path to the XML configuration, may be {@code null}
     * @param loader The class loader, may be {@code null}
     *
     * @return The initialized logger context, or {@code null}
     */
    @Nullable
    public static LoggerContext initContextByFileQuietly(@Nullable String xmlPath, @Nullable ClassLoader loader) {
        try {
            return initContextByFile(xmlPath, loader);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Initialize a Log4j context from an XML configuration stream.
     *
     * @param stream The XML configuration stream, may be {@code null}
     *
     * @return The initialized logger context, or {@code null}
     *
     * @throws IOException If the operation fails
     */
    @Nullable
    public static LoggerContext initContextByStream(@Nullable InputStream stream) throws IOException {
        return initContextByStream(stream, null);
    }

    /**
     * Initialize a Log4j context from an XML configuration stream.
     *
     * @param stream The XML configuration stream, may be {@code null}
     * @param loader The class loader, may be {@code null}
     *
     * @return The initialized logger context, or {@code null}
     *
     * @throws IOException If the operation fails
     */
    @Nullable
    public static LoggerContext initContextByStream(@Nullable InputStream stream, @Nullable ClassLoader loader) throws IOException {
        if (stream == null) {
            return null;
        }
        ConfigurationSource source = new ConfigurationSource(stream);
        return Configurator.initialize(ObjectUtils.getIfNull(loader, ClassUtils.getDefaultClassLoader()), source);
    }

    /**
     * Initialize a Log4j context from a stream, returning {@code null} on failure.
     *
     * @param stream The XML configuration stream, may be {@code null}
     *
     * @return The initialized logger context, or {@code null}
     */
    @Nullable
    public static LoggerContext initContextByStreamQuietly(@Nullable InputStream stream) {
        return initContextByStreamQuietly(stream, null);
    }

    /**
     * Initialize a Log4j context from a stream, returning {@code null} on failure.
     *
     * @param stream The XML configuration stream, may be {@code null}
     * @param loader The class loader, may be {@code null}
     *
     * @return The initialized logger context, or {@code null}
     */
    @Nullable
    public static LoggerContext initContextByStreamQuietly(@Nullable InputStream stream, @Nullable ClassLoader loader) {
        try {
            return initContextByStream(stream, loader);
        } catch (Exception ignored) {
        }
        return null;
    }
}
