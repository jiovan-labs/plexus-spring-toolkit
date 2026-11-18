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


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.support.ServletContextResource;
import com.jiovan.plexus.common.toolkit.constant.StringVariantConst;
import com.jiovan.plexus.common.toolkit.constant.SymbolVariantConst;
import com.jiovan.plexus.common.toolkit.exception.UnsupportedResourceException;
import com.jiovan.plexus.common.toolkit.util.InetProtocolWraps;
import com.jiovan.plexus.common.toolkit.util.StringUtilsWraps;


/**
 * Utilities for {@link org.springframework.util.ResourceUtils}
 *
 * @author David Hsing
 *
 * @see org.springframework.util.ResourceUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class ResourceUtilsWraps {
    /**
     * Resolve the given location into a Spring resource.
     *
     * @param location The resource location, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Resource determineResource(@Nullable String location) {
        return determineResource(location, null);
    }

    /**
     * Resolve the given location into a Spring resource.
     *
     * @param location The resource location, may be {@code null}
     * @param loader The class loader, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Resource determineResource(@Nullable String location, @Nullable ClassLoader loader) {
        if (StringUtils.isBlank(location)) {
            return null;
        }
        if (ClassPathWraps.startsWithClasspath(location)) {
            return ofClassPathResource(location, loader);
        }
        if (ClassPathWraps.startsWithClasspathStar(location)) {
            return ArrayUtils.get(resolveClassPathResourcesQuietly(location, new DefaultResourceLoader(loader)), 0);
        }
        if (InetProtocolWraps.startsWithProtocol(location)) {
            return ofUrlResource(location);
        }
        return new FileSystemResource(location);
    }

    /**
     * Load the resource at the given location.
     *
     * @param location The resource location, may be {@code null}
     * @param loader The class loader, may be {@code null}
     *
     * @return The resolved resource, or {@code null}
     */
    @Nullable
    public static Resource getResource(@Nullable String location, @Nullable ResourceLoader loader) {
        return (StringUtils.isBlank(location) || loader == null) ? null : loader.getResource(location);
    }

    /**
     * Return the URL of the given resource.
     *
     * @param location The resource location, may be {@code null}
     *
     * @return The resource URL, or {@code null}
     */
    @Nullable
    public static URL getUrl(@Nullable String location) {
        if (StringUtils.isBlank(location)) {
            return null;
        }
        try {
            return ResourceUtils.getURL(location);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Convert the given location to a URI.
     *
     * @param resourceUrl The given resourceUrl
     *
     * @return The resource URI, or {@code null}
     */
    @Nullable
    public static URI toUri(@Nullable URL resourceUrl) {
        if (resourceUrl == null) {
            return null;
        }
        try {
            return ResourceUtils.toURI(resourceUrl);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Convert the given location to a URI.
     *
     * @param location The resource location, may be {@code null}
     *
     * @return The resource URI, or {@code null}
     */
    @Nullable
    public static URI toUri(@Nullable String location) {
        if (StringUtils.isBlank(location)) {
            return null;
        }
        try {
            return ResourceUtils.toURI(location);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Resolve the given resource as a file.
     *
     * @param fileUri The given fileUri
     *
     * @return The resource as a file, or {@code null}
     */
    @Nullable
    public static File getFile(@Nullable URI fileUri) {
        return getFile(fileUri, StringVariantConst.URI);
    }

    /**
     * Resolve the given resource as a file.
     *
     * @param fileUri The given fileUri
     * @param description The given description
     *
     * @return The resource as a file, or {@code null}
     */
    @Nullable
    public static File getFile(@Nullable URI fileUri, @Nullable String description) {
        if (fileUri == null) {
            return null;
        }
        try {
            return ResourceUtils.getFile(fileUri, StringUtils.defaultString(description));
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Resolve the given resource as a file.
     *
     * @param fileUrl The given fileUrl
     *
     * @return The resource as a file, or {@code null}
     */
    @Nullable
    public static File getFile(@Nullable URL fileUrl) {
        return getFile(fileUrl, StringVariantConst.URL);
    }

    /**
     * Resolve the given resource as a file.
     *
     * @param fileUrl The given fileUrl
     * @param description The given description
     *
     * @return The resource as a file, or {@code null}
     */
    @Nullable
    public static File getFile(@Nullable URL fileUrl, @Nullable String description) {
        if (fileUrl == null) {
            return null;
        }
        try {
            return ResourceUtils.getFile(fileUrl, StringUtils.defaultString(description));
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Resolve the given resource as a file.
     *
     * @param location The resource location, may be {@code null}
     *
     * @return The resource as a file, or {@code null}
     */
    @Nullable
    public static File getFile(@Nullable String location) {
        if (StringUtils.isBlank(location)) {
            return null;
        }
        try {
            return ResourceUtils.getFile(location);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Extract the archive URL from the given JAR URL.
     *
     * @param jarUrl The given jarUrl
     *
     * @return The result of the operation
     */
    @Nullable
    public static URL extractArchiveUrl(@Nullable URL jarUrl) {
        if (jarUrl == null) {
            return null;
        }
        try {
            return ResourceUtils.extractArchiveURL(jarUrl);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Extract the JAR file URL from the given URL.
     *
     * @param jarUrl The given jarUrl
     *
     * @return The result of the operation
     */
    @Nullable
    public static URL extractJarFileUrl(@Nullable URL jarUrl) {
        if (jarUrl == null) {
            return null;
        }
        try {
            return ResourceUtils.extractJarFileURL(jarUrl);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Return the path of the resolved resource.
     *
     * @param resource The resource, may be {@code null}
     *
     * @return The resource path, or {@code null}
     *
     * @throws UnsupportedResourceException If the operation fails
     */
    @Nullable
    public static String getResourcePath(@Nullable Resource resource) throws UnsupportedResourceException {
        if (resource == null) {
            return null;
        }
        if (resource instanceof ClassPathResource alias) {
            return alias.getPath();
        } else if (resource instanceof FileSystemResource alias) {
            return alias.getPath();
        } else if (resource instanceof ServletContextResource alias) {
            return alias.getPath();
        } else if (resource instanceof UrlResource alias) {
            return alias.getURL().toString();
        }
        throw new UnsupportedResourceException();
    }

    /**
     * Return the resource path, or {@code null} on failure.
     *
     * @param resource The resource, may be {@code null}
     *
     * @return The resource path, or {@code null}
     */
    @Nullable
    public static String getResourcePathQuietly(@Nullable Resource resource) {
        try {
            return getResourcePath(resource);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Return whether the location denotes a file system path.
     *
     * @param location The resource location, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isFilePath(@Nullable String location) {
        return StringUtilsWraps.startsWithIgnoreCase(location, StringUtils.join(ResourceUtils.URL_PROTOCOL_FILE, SymbolVariantConst.PROTOCOL_DELIMITER));
    }

    /**
     * Return whether the location denotes a JAR path.
     *
     * @param location The resource location, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isJarPath(@Nullable String location) {
        return StringUtilsWraps.startsWithIgnoreCase(location, StringUtils.join(ResourceUtils.URL_PROTOCOL_JAR, SymbolVariantConst.PROTOCOL_DELIMITER));
    }

    /**
     * Return whether the location denotes a WAR path.
     *
     * @param location The resource location, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isWarPath(@Nullable String location) {
        return StringUtilsWraps.startsWithIgnoreCase(location, StringUtils.join(ResourceUtils.URL_PROTOCOL_WAR, SymbolVariantConst.PROTOCOL_DELIMITER));
    }

    /**
     * Return whether the location denotes a ZIP path.
     *
     * @param location The resource location, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isZipPath(@Nullable String location) {
        return StringUtilsWraps.startsWithIgnoreCase(location, StringUtils.join(ResourceUtils.URL_PROTOCOL_ZIP, SymbolVariantConst.PROTOCOL_DELIMITER));
    }

    /**
     * Return whether the location denotes a WSJAR path.
     *
     * @param location The resource location, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isWsjarPath(@Nullable String location) {
        return StringUtilsWraps.startsWithIgnoreCase(location, StringUtils.join(ResourceUtils.URL_PROTOCOL_WSJAR, SymbolVariantConst.PROTOCOL_DELIMITER));
    }

    /**
     * Return whether the location denotes a VFS path.
     *
     * @param location The resource location, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isVfsPath(@Nullable String location) {
        return StringUtilsWraps.startsWithIgnoreCase(location, StringUtils.join(ResourceUtils.URL_PROTOCOL_VFS, SymbolVariantConst.PROTOCOL_DELIMITER));
    }

    /**
     * Return whether the location denotes a VFS zip path.
     *
     * @param location The resource location, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isVfszipPath(@Nullable String location) {
        return StringUtilsWraps.startsWithIgnoreCase(location, StringUtils.join(ResourceUtils.URL_PROTOCOL_VFSZIP, SymbolVariantConst.PROTOCOL_DELIMITER));
    }

    /**
     * Return whether the location denotes a VFS file path.
     *
     * @param location The resource location, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isVfsfilePath(@Nullable String location) {
        return StringUtilsWraps.startsWithIgnoreCase(location, StringUtils.join(ResourceUtils.URL_PROTOCOL_VFSFILE, SymbolVariantConst.PROTOCOL_DELIMITER));
    }

    /**
     * Return whether the named class-path resource exists.
     *
     * @param location The resource location, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean existsClassPathResource(@Nullable String location) {
        return existsClassPathResource(location, (ClassLoader) null);
    }

    /**
     * Return whether the named class-path resource exists.
     *
     * @param path The path, may be {@code null}
     * @param loader The class loader, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean existsClassPathResource(@Nullable String path, @Nullable ClassLoader loader) {
        ClassPathResource resource = ofClassPathResource(path, loader);
        return resource != null && resource.exists();
    }

    /**
     * Return whether the named class-path resource exists.
     *
     * @param path The path, may be {@code null}
     * @param clazz The class, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean existsClassPathResource(@Nullable String path, @Nullable Class<?> clazz) {
        ClassPathResource resource = ofClassPathResource(path, clazz);
        return resource != null && resource.exists();
    }

    /**
     * Return whether the named URL resource exists.
     *
     * @param location The resource location, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean existsUrlResource(@Nullable String location) {
        UrlResource resource = ofUrlResource(location);
        return resource != null && resource.exists();
    }

    /**
     * Return whether the named URL resource exists.
     *
     * @param protocol The given protocol
     * @param location The resource location, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean existsUrlResource(@Nullable String protocol, @Nullable String location) {
        UrlResource resource = ofUrlResource(protocol, location);
        return resource != null && resource.exists();
    }

    /**
     * Return whether the named URL resource exists.
     *
     * @param protocol The given protocol
     * @param location The resource location, may be {@code null}
     * @param fragment The given fragment
     *
     * @return Whether the condition holds
     */
    public static boolean existsUrlResource(@Nullable String protocol, @Nullable String location, @Nullable String fragment) {
        UrlResource resource = ofUrlResource(protocol, location, fragment);
        return resource != null && resource.exists();
    }

    /**
     * Create a class-path resource for the given path.
     *
     * @param path The path, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static ClassPathResource ofClassPathResource(@Nullable String path) {
        return ofClassPathResource(path, (ClassLoader) null);
    }

    /**
     * Create a class-path resource for the given path.
     *
     * @param path The path, may be {@code null}
     * @param loader The class loader, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static ClassPathResource ofClassPathResource(@Nullable String path, @Nullable ClassLoader loader) {
        String pathToUse = ClassPathWraps.startsWithClasspath(path) ? ClassPathWraps.removeClasspathPrefix(path) : path;
        return StringUtils.isBlank(path) ? null : new ClassPathResource(pathToUse, loader);
    }

    /**
     * Create a class-path resource for the given path.
     *
     * @param path The path, may be {@code null}
     * @param clazz The class, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static ClassPathResource ofClassPathResource(@Nullable String path, @Nullable Class<?> clazz) {
        String pathToUse = ClassPathWraps.startsWithClasspath(path) ? ClassPathWraps.removeClasspathPrefix(path) : path;
        return StringUtils.isBlank(path) ? null : new ClassPathResource(pathToUse, clazz);
    }

    /**
     * Create a URL resource for the given location.
     *
     * @param location The resource location, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static UrlResource ofUrlResource(@Nullable String location) {
        try {
            return StringUtils.isBlank(location) ? null : new UrlResource(location);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Create a URL resource for the given location.
     *
     * @param location The resource location, may be {@code null}
     * @param protocol The given protocol
     *
     * @return The result of the operation
     */
    @Nullable
    public static UrlResource ofUrlResource(@Nullable String location, @Nullable String protocol) {
        return ofUrlResource(location, protocol, null);
    }

    /**
     * Create a URL resource for the given location.
     *
     * @param location The resource location, may be {@code null}
     * @param protocol The given protocol
     * @param fragment The given fragment
     *
     * @return The result of the operation
     */
    @Nullable
    public static UrlResource ofUrlResource(@Nullable String location, @Nullable String protocol, @Nullable String fragment) {
        if (StringUtils.isBlank(location)) {
            return null;
        }
        try {
            return new UrlResource(StringUtils.defaultIfBlank(protocol, ResourceUtils.URL_PROTOCOL_FILE), location, fragment);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Resolve all class-path resources matching the given pattern.
     *
     * @param path The path, may be {@code null}
     *
     * @return The result of the operation
     *
     * @throws IOException If the operation fails
     */
    @Nullable
    public static Resource[] resolveClassPathResources(@Nullable String path) throws IOException {
        return resolveClassPathResources(path, null);
    }

    /**
     * @see "https://www.cnblogs.com/gqymy/p/10450466.html"
     */
    @Nullable
    public static Resource[] resolveClassPathResources(@Nullable String path, @Nullable ResourceLoader loader) throws IOException {
        if (StringUtils.isBlank(path)) {
            return null;
        }
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(loader != null ? loader : new DefaultResourceLoader());
        return resolver.getResources(path);
    }

    /**
     * Resolve class-path resources, returning {@code null} on failure.
     *
     * @param path The path, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Resource[] resolveClassPathResourcesQuietly(@Nullable String path) {
        return resolveClassPathResourcesQuietly(path, null);
    }

    /**
     * Resolve class-path resources, returning {@code null} on failure.
     *
     * @param path The path, may be {@code null}
     * @param loader The class loader, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Resource[] resolveClassPathResourcesQuietly(@Nullable String path, @Nullable ResourceLoader loader) {
        try {
            return resolveClassPathResources(path, loader);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Read the given resource into a byte array.
     *
     * @param resource The resource, may be {@code null}
     *
     * @return The resource bytes, or {@code null}
     *
     * @throws IOException If the operation fails
     */
    @Nullable
    public static byte[] toByteArray(@Nullable Resource resource) throws IOException {
        if (resource == null) {
            return null;
        }
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            FileCopyUtils.copy(resource.getInputStream(), output);
            return output.toByteArray();
        }
    }

    /**
     * Read the resource into a byte array, returning {@code null} on failure.
     *
     * @param resource The resource, may be {@code null}
     *
     * @return The resource bytes, or {@code null}
     */
    @Nullable
    public static byte[] toByteArrayQuietly(@Nullable Resource resource) {
        try {
            return toByteArray(resource);
        } catch (Exception ignored) {
        }
        return null;
    }
}
