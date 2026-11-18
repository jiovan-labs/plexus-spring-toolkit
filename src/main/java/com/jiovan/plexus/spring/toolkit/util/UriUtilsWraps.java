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


import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UrlPathHelper;
import com.jiovan.plexus.common.toolkit.constant.CharVariantConst;
import com.jiovan.plexus.common.toolkit.constant.SymbolVariantConst;
import com.jiovan.plexus.common.toolkit.util.StringUtilsWraps;


/**
 * Utilities for {@link org.springframework.web.util.UriUtils}
 *
 * @author David Hsing
 *
 * @see org.springframework.web.util.UriUtils
 * @see org.springframework.web.util.UrlPathHelper
 * @see org.springframework.web.util.UriComponents
 * @see org.springframework.web.util.UriComponentsBuilder
 * @see org.springframework.security.web.util.UrlUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class UriUtilsWraps {
    /**
     * Decode the given URL-encoded text.
     *
     * @param url The URL, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String decodeUrl(@Nullable String url) {
        return decodeUrl(url, StandardCharsets.UTF_8);
    }

    /**
     * Decode the given URL-encoded text.
     *
     * @param url The URL, may be {@code null}
     * @param charset The charset, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String decodeUrl(@Nullable String url, @Nullable Charset charset) {
        try {
            return StringUtils.isEmpty(url) ? null : URLDecoder.decode(url, ObjectUtils.getIfNull(charset, StandardCharsets.UTF_8));
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Encode the given text for use in a URL.
     *
     * @param url The URL, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String encodeUrl(@Nullable String url) {
        return encodeUrl(url, StandardCharsets.UTF_8);
    }

    /**
     * Encode the given text for use in a URL.
     *
     * @param url The URL, may be {@code null}
     * @param charset The charset, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String encodeUrl(@Nullable String url, @Nullable Charset charset) {
        try {
            return StringUtils.isEmpty(url) ? null : URLEncoder.encode(url, ObjectUtils.getIfNull(charset, StandardCharsets.UTF_8));
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Return the context path of the given request.
     *
     * @param request The HTTP request, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getContextPath(@Nullable HttpServletRequest request) {
        return getContextPath(request, false);
    }

    /**
     * Returns the context path for the given request
     * <p>
     * Without schema, host, port or query string
     *
     * @param request The servlet request
     * @param endSlash Whether to append a slash at the end of path or not
     *
     * @return the context path for the given request
     */
    @Nullable
    public static String getContextPath(@Nullable HttpServletRequest request, boolean endSlash) {
        if (request == null) {
            return null;
        }
        String result = UrlPathHelper.defaultInstance.getContextPath(request);
        return (!endSlash || StringUtilsWraps.endsWith(result, CharUtils.toString(CharVariantConst.SLASH))) ? result : StringUtils.join(result, CharVariantConst.SLASH);
    }

    /**
     * Return the original context path before any wrapper changes.
     *
     * @param request The HTTP request, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getContextPathOriginally(@Nullable HttpServletRequest request) {
        return getContextPathOriginally(request, false);
    }

    /**
     * Return the original context path before any wrapper changes.
     *
     * @param request The HTTP request, may be {@code null}
     * @param endSlash The given endSlash
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getContextPathOriginally(@Nullable HttpServletRequest request, boolean endSlash) {
        if (request == null) {
            return null;
        }
        String result = UrlPathHelper.defaultInstance.getOriginatingContextPath(request);
        return (!endSlash || StringUtilsWraps.endsWith(result, CharUtils.toString(CharVariantConst.SLASH))) ? result : StringUtils.join(result, CharVariantConst.SLASH);
    }

    /**
     * @return schema, host, not including the last slash
     */
    @Nullable
    public static String getSchemaHost(@Nullable HttpServletRequest request) {
        return (request == null) ? null : (request.getScheme() + SymbolVariantConst.PROTOCOL_DELIMITER + request.getServerName());
    }

    /**
     * @return schema, host and port, not including the last slash
     */
    @Nullable
    public static String getSchemaHostPort(@Nullable HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }

    /**
     * @return schema, host, port and context path, not including the last slash
     */
    public static String getSchemaHostPortContext(@Nullable HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(StringUtils.defaultString(request.getContextPath())).toString();
    }

    /**
     * Return a named query parameter from the given URI.
     *
     * @param uri The URI, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getQueryParamAsStringFromUri(@Nullable String uri, @Nullable String name) {
        return StringUtils.isAnyBlank(uri, name) ? null : MultiMapWraps.firstNonNullValue(getQueryParamsFromUri(uri), name);
    }

    /**
     * Return a named query parameter from the given URI.
     *
     * @param uri The URI, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getQueryParamAsStringFromUri(@Nullable URI uri, @Nullable String name) {
        return (uri == null || StringUtils.isBlank(name)) ? null : MultiMapWraps.firstNonNullValue(getQueryParamsFromUri(uri), name);
    }

    /**
     * Return a named query parameter from the given URL.
     *
     * @param url The URL, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getQueryParamAsStringFromUrl(@Nullable String url, @Nullable String name) {
        return StringUtils.isAnyBlank(url, name) ? null : MultiMapWraps.firstNonNullValue(getQueryParamsFromUrl(url), name);
    }

    /**
     * Return all values of a named query parameter from the URI.
     *
     * @param uri The URI, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String[] getQueryParamAsStringsFromUri(@Nullable String uri, @Nullable String name) {
        return StringUtils.isAnyBlank(uri, name) ? null : MultiMapWraps.getValues(getQueryParamsFromUri(uri), name);
    }

    /**
     * Return all values of a named query parameter from the URI.
     *
     * @param uri The URI, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String[] getQueryParamAsStringsFromUri(@Nullable URI uri, @Nullable String name) {
        return (uri == null || StringUtils.isBlank(name)) ? null : MultiMapWraps.getValues(getQueryParamsFromUri(uri), name);
    }

    /**
     * Return all values of a named query parameter from the URL.
     *
     * @param url The URL, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String[] getQueryParamAsStringsFromUrl(@Nullable String url, @Nullable String name) {
        return StringUtils.isAnyBlank(url, name) ? null : MultiMapWraps.getValues(getQueryParamsFromUrl(url), name);
    }

    /**
     * Return all query parameters from the given URI.
     *
     * @param uri The URI, may be {@code null}
     */
    @Nullable
    public static MultiValueMap<String, String> getQueryParamsFromUri(@Nullable String uri) {
        UriComponents components = ofUriComponentsFromUri(uri);
        return (components == null) ? null : components.getQueryParams();
    }

    /**
     * Return all query parameters from the given URI.
     *
     * @param uri The URI, may be {@code null}
     */
    @Nullable
    public static MultiValueMap<String, String> getQueryParamsFromUri(@Nullable URI uri) {
        UriComponents components = ofUriComponentsFromUri(uri);
        return (components == null) ? null : components.getQueryParams();
    }

    /**
     * Return all query parameters from the given URL.
     *
     * @param url The URL, may be {@code null}
     */
    @Nullable
    public static MultiValueMap<String, String> getQueryParamsFromUrl(@Nullable String url) {
        UriComponents components = ofUriComponentsFromUrl(url);
        return (components == null) ? null : components.getQueryParams();
    }

    /**
     * Return the query string of the given request.
     *
     * @param request The HTTP request, may be {@code null}
     *
     * @return The result of the operation
     */
    public static String getQueryString(@Nullable HttpServletRequest request) {
        return (request == null) ? null : request.getQueryString();
    }

    /**
     * Return the original query string before any wrapper changes.
     *
     * @param request The HTTP request, may be {@code null}
     *
     * @return The result of the operation
     */
    public static String getQueryStringOriginally(@Nullable HttpServletRequest request) {
        return (request == null) ? null : UrlPathHelper.defaultInstance.getOriginatingQueryString(request);
    }

    /**
     * Returns the request URI for the given request
     * <p>
     * Without schema, host, port and query string, with context path
     *
     * @param request The servlet request
     *
     * @return the request URI for the given request
     */
    public static String getRequestUri(@Nullable HttpServletRequest request) {
        return (request == null) ? null : UrlPathHelper.defaultInstance.getRequestUri(request);
    }

    /**
     * Return the original request URI before any wrapper changes.
     *
     * @param request The HTTP request, may be {@code null}
     *
     * @return The result of the operation
     */
    public static String getRequestUriOriginally(@Nullable HttpServletRequest request) {
        return (request == null) ? null : UrlPathHelper.defaultInstance.getOriginatingRequestUri(request);
    }

    /**
     * Returns the request URI and query string for the given request
     * <p>
     * Without schema, host, port, with context path and query string
     *
     * @param request The servlet request
     *
     * @return the request URI and query string for the given request
     */
    public static String getRequestUriQueryString(@Nullable HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String uri = UrlPathHelper.defaultInstance.getRequestUri(request), query = request.getQueryString();
        return StringUtils.isBlank(query) ? uri : StringUtils.join(uri, CharVariantConst.QUESTION, query);
    }

    /**
     * Return the original URI and query string.
     *
     * @param request The HTTP request, may be {@code null}
     *
     * @return The result of the operation
     */
    public static String getRequestUriQueryStringOriginally(@Nullable HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String uri = UrlPathHelper.defaultInstance.getRequestUri(request), query = UrlPathHelper.defaultInstance.getOriginatingQueryString(request);
        return StringUtils.isBlank(query) ? uri : StringUtils.join(uri, CharVariantConst.QUESTION, query);
    }

    /**
     * Returns the request URL for the given request
     * <p>
     * With schema, host, port and context path, without query string
     *
     * @param request The servlet request
     *
     * @return the request URL for the given request
     */
    public static String getRequestUrl(@Nullable HttpServletRequest request) {
        return (request == null) ? null : request.getRequestURL().toString();
    }

    /**
     * Returns the request URL and query string for the given request
     * <p>
     * With schema, host, port, context path and query string
     *
     * @param request The servlet request
     *
     * @return the request URL and query string for the given request
     */
    public static String getRequestUrlQueryString(@Nullable HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String url = request.getRequestURL().toString(), query = request.getQueryString();
        return StringUtils.isBlank(query) ? url : StringUtils.join(url, CharVariantConst.QUESTION, query);
    }

    /**
     * Returns the servlet path for the given request
     * <p>
     * Without schema, host, port, context path and query string
     *
     * @param request The servlet request
     *
     * @return the servlet path for the given request
     */
    public static String getServletPath(@Nullable HttpServletRequest request) {
        return (request == null) ? null : UrlPathHelper.defaultInstance.getServletPath(request);
    }

    /**
     * Return the original servlet path before any wrapper changes.
     *
     * @param request The HTTP request, may be {@code null}
     *
     * @return The result of the operation
     */
    public static String getServletPathOriginally(@Nullable HttpServletRequest request) {
        return (request == null) ? null : UrlPathHelper.defaultInstance.getOriginatingServletPath(request);
    }

    /**
     * Returns the servlet path and query string for the given request
     * <p>
     * Without schema, host, port, context path, with query string
     *
     * @param request The servlet request
     *
     * @return the servlet path and query string for the given request
     */
    public static String getServletPathQueryString(@Nullable HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String path = UrlPathHelper.defaultInstance.getServletPath(request), query = request.getQueryString();
        return StringUtils.isBlank(query) ? path : StringUtils.join(path, CharVariantConst.QUESTION, query);
    }

    /**
     * Build a URL path helper with the given configuration.
     *
     * @param defaultEncoding The given defaultEncoding
     *
     * @return The result of the operation
     */
    @Nonnull
    public static UrlPathHelper ofUrlPathHelper(@Nullable Charset defaultEncoding) {
        UrlPathHelper result = new UrlPathHelper();
        if (defaultEncoding != null) {
            result.setDefaultEncoding(defaultEncoding.name());
        }
        return result;
    }

    /**
     * Build a URL path helper with the given configuration.
     *
     * @param defaultEncoding The given defaultEncoding
     *
     * @return The result of the operation
     */
    @Nonnull
    public static UrlPathHelper ofUrlPathHelper(@Nullable String defaultEncoding) {
        UrlPathHelper result = new UrlPathHelper();
        if (StringUtils.isNotBlank(defaultEncoding)) {
            result.setDefaultEncoding(defaultEncoding);
        }
        return result;
    }

    /**
     * Build URI components from the given URI.
     *
     * @param uri The URI, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static UriComponents ofUriComponentsFromUri(@Nullable String uri) {
        return StringUtils.isBlank(uri) ? null : UriComponentsBuilder.fromUriString(uri).build();
    }

    /**
     * Build URI components from the given URI.
     *
     * @param uri The URI, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static UriComponents ofUriComponentsFromUri(@Nullable URI uri) {
        return (uri == null) ? null : UriComponentsBuilder.fromUri(uri).build();
    }

    /**
     * Build URI components from the given URL.
     *
     * @param url The URL, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static UriComponents ofUriComponentsFromUrl(@Nullable String url) {
        return StringUtils.isBlank(url) ? null : UriComponentsBuilder.fromUriString(url).build();
    }

    /**
     * Returns a new URL with the schema and host replaced
     *
     * @return a new URL with the schema and host replaced
     */
    @Nullable
    public static String replaceSchemaHost(@Nullable String originUrl, @Nullable String replaceUrl) {
        return replaceSchemaHost(originUrl, UriComponentsBuilder.ParserType.RFC, replaceUrl, UriComponentsBuilder.ParserType.RFC);
    }

    /**
     * Returns a new URL with the schema and host replaced
     *
     * @return a new URL with the schema and host replaced
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "DuplicatedCode"})
    public static String replaceSchemaHost(@Nullable String originUrl, @Nullable UriComponentsBuilder.ParserType originParserType, @Nullable String replaceUrl, @Nullable UriComponentsBuilder.ParserType replaceParserType) {
        if (StringUtils.isAnyBlank(originUrl, replaceUrl)) {
            return originUrl;
        }
        try {
            UriComponents originComp = UriComponentsBuilder.fromUriString(originUrl, ObjectUtils.getIfNull(originParserType, UriComponentsBuilder.ParserType.RFC)).build();
            UriComponents replaceComp = UriComponentsBuilder.fromUriString(replaceUrl, ObjectUtils.getIfNull(originParserType, UriComponentsBuilder.ParserType.RFC)).build();
            return UriComponentsBuilder.newInstance().scheme(replaceComp.getScheme()).host(replaceComp.getHost()).port(originComp.getPort()).path(originComp.getPath()).query(originComp.getQuery()).fragment(originComp.getFragment()).build().toUriString();
        } catch (Exception ignored) {
        }
        return originUrl;
    }

    /**
     * Returns a new URL with the schema, host and port replaced
     *
     * @return a new URL with the schema, host and port replaced
     */
    @Nullable
    public static String replaceSchemaHostPort(@Nullable String originUrl, @Nullable String replaceUrl) {
        return replaceSchemaHostPort(originUrl, UriComponentsBuilder.ParserType.RFC, replaceUrl, UriComponentsBuilder.ParserType.RFC);
    }

    /**
     * Returns a new URL with the schema, host and port replaced
     *
     * @return a new URL with the schema, host and port replaced
     */
    @Nullable
    @SuppressWarnings({"DataFlowIssue", "DuplicatedCode"})
    public static String replaceSchemaHostPort(@Nullable String originUrl, @Nullable UriComponentsBuilder.ParserType originParserType, @Nullable String replaceUrl, @Nullable UriComponentsBuilder.ParserType replaceParserType) {
        if (StringUtils.isAnyBlank(originUrl, replaceUrl)) {
            return originUrl;
        }
        try {
            UriComponents originComp = UriComponentsBuilder.fromUriString(originUrl, ObjectUtils.getIfNull(originParserType, UriComponentsBuilder.ParserType.RFC)).build();
            UriComponents replaceComp = UriComponentsBuilder.fromUriString(replaceUrl, ObjectUtils.getIfNull(originParserType, UriComponentsBuilder.ParserType.RFC)).build();
            return UriComponentsBuilder.newInstance().scheme(replaceComp.getScheme()).host(replaceComp.getHost()).port(replaceComp.getPort()).path(originComp.getPath()).query(originComp.getQuery()).fragment(originComp.getFragment()).build().toUriString();
        } catch (Exception ignored) {
        }
        return originUrl;
    }
}
