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
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import com.jiovan.plexus.common.toolkit.constant.RegexVariantConst;
import com.jiovan.plexus.common.toolkit.util.ArrayUtilsWraps;
import com.jiovan.plexus.common.toolkit.util.CollectionPlainWraps;
import com.jiovan.plexus.common.toolkit.util.EnumerationPlainWraps;
import com.jiovan.plexus.common.toolkit.util.RegexUtilsWraps;
import com.jiovan.plexus.common.toolkit.util.StringUtilsWraps;


/**
 * Utilities for {@link org.springframework.http.HttpHeaders}
 *
 * @author David Hsing
 *
 * @see org.springframework.http.HttpHeaders
 * @see org.springframework.web.util.WebUtils
 * @see org.springframework.util.MimeTypeUtils
 * @see "org.thymeleaf.util.ContentTypeUtils"
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class HttpHeaderWraps {
    public static boolean existsHeader(@Nullable HttpServletRequest request, @Nullable String name) {
        return request != null && StringUtils.isNotBlank(name) && request.getHeader(name) != null;
    }

    public static boolean existsHeaderValue(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String value) {
        return request != null && StringUtils.isNotBlank(name) && StringUtilsWraps.equals(request.getHeader(name), value);
    }

    public static boolean existsHeaderValueIgnoreCase(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String value) {
        return request != null && StringUtils.isNotBlank(name) && StringUtilsWraps.equalsIgnoreCase(request.getHeader(name), value);
    }

    public static boolean existsHeader(@Nullable HttpServletResponse response, @Nullable String name) {
        return response != null && StringUtils.isNotBlank(name) && response.getHeader(name) != null;
    }

    public static boolean existsHeaderValue(@Nullable HttpServletResponse response, @Nullable String name, @Nullable String value) {
        return response != null && StringUtils.isNotBlank(name) && StringUtilsWraps.equals(response.getHeader(name), value);
    }

    public static boolean existsHeaderValueIgnoreCase(@Nullable HttpServletResponse response, @Nullable String name, @Nullable String value) {
        return response != null && StringUtils.isNotBlank(name) && StringUtilsWraps.equalsIgnoreCase(response.getHeader(name), value);
    }

    public static String getAcceptLanguage(@Nullable HttpServletRequest request) {
        return getAcceptLanguage(request, null);
    }

    public static String getAcceptLanguage(@Nullable HttpServletRequest request, @Nullable String defaultValue) {
        return getHeader(request, HttpHeaders.ACCEPT_LANGUAGE, defaultValue);
    }

    public static String getReferer(@Nullable HttpServletRequest request) {
        return getReferer(request, null);
    }

    public static String getReferer(@Nullable HttpServletRequest request, @Nullable String defaultValue) {
        return getHeader(request, HttpHeaders.REFERER, defaultValue);
    }

    public static String getUserAgent(@Nullable HttpServletRequest request) {
        return getUserAgent(request, null);
    }

    public static String getUserAgent(@Nullable HttpServletRequest request, @Nullable String defaultValue) {
        return getHeader(request, HttpHeaders.USER_AGENT, defaultValue);
    }

    public static String getHeader(@Nullable HttpServletRequest request, @Nullable String name) {
        return getHeader(request, name, null);
    }

    public static String getHeader(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String defaultValue) {
        return (request == null || StringUtils.isBlank(name)) ? defaultValue : StringUtils.defaultIfBlank(request.getHeader(name), defaultValue);
    }

    public static String getHeader(@Nullable HttpServletResponse response, @Nullable String name) {
        return getHeader(response, name, null);
    }

    public static String getHeader(@Nullable HttpServletResponse response, @Nullable String name, @Nullable String defaultValue) {
        return (response == null || StringUtils.isBlank(name)) ? defaultValue : StringUtils.defaultIfBlank(response.getHeader(name), defaultValue);
    }

    public static HttpHeaders getHeaders(@Nullable HttpServletRequest request) {
        return getHeadersExcluding(request, (Collection<String>) null);
    }

    public static HttpHeaders getHeaders(@Nullable HttpServletResponse response) {
        return getHeadersExcluding(response, (Collection<String>) null);
    }

    public static HttpHeaders getHeadersIncluding(@Nullable HttpServletRequest request, @Nullable String... headers) {
        return getHeadersIncluding(request, ArrayUtilsWraps.asList(headers));
    }

    public static HttpHeaders getHeadersIncluding(@Nullable HttpServletRequest request, @Nullable Collection<String> headers) {
        if (request == null || CollectionUtils.isEmpty(headers)) {
            return null;
        }
        HttpHeaders result = new HttpHeaders();
        EnumerationPlainWraps.forEach(request.getHeaderNames(), name -> result.add(name, request.getHeader(name)), name -> StringUtilsWraps.equalsAny(name, headers));
        return result.isEmpty() ? null : result;
    }

    public static HttpHeaders getHeadersIncluding(@Nullable HttpServletResponse response, @Nullable String... headers) {
        return getHeadersIncluding(response, ArrayUtilsWraps.asList(headers));
    }

    public static HttpHeaders getHeadersIncluding(@Nullable HttpServletResponse response, @Nullable Collection<String> headers) {
        if (response == null || CollectionUtils.isEmpty(headers)) {
            return null;
        }
        HttpHeaders result = new HttpHeaders();
        CollectionPlainWraps.forEach(response.getHeaderNames(), name -> result.add(name, response.getHeader(name)), name -> StringUtilsWraps.equalsAny(name, headers));
        return result.isEmpty() ? null : result;
    }

    public static HttpHeaders getHeadersIncludingIgnoreCase(@Nullable HttpServletRequest request, @Nullable String... headers) {
        return getHeadersIncludingIgnoreCase(request, ArrayUtilsWraps.asList(headers));
    }

    public static HttpHeaders getHeadersIncludingIgnoreCase(@Nullable HttpServletRequest request, @Nullable Collection<String> headers) {
        if (request == null || CollectionUtils.isEmpty(headers)) {
            return null;
        }
        HttpHeaders result = new HttpHeaders();
        EnumerationPlainWraps.forEach(request.getHeaderNames(), name -> result.add(name, request.getHeader(name)), name -> StringUtilsWraps.equalsAnyIgnoreCase(name, headers));
        return result.isEmpty() ? null : result;
    }

    public static HttpHeaders getHeadersIncludingIgnoreCase(@Nullable HttpServletResponse response, @Nullable String... headers) {
        return getHeadersIncludingIgnoreCase(response, ArrayUtilsWraps.asList(headers));
    }

    public static HttpHeaders getHeadersIncludingIgnoreCase(@Nullable HttpServletResponse response, @Nullable Collection<String> headers) {
        if (response == null || CollectionUtils.isEmpty(headers)) {
            return null;
        }
        HttpHeaders result = new HttpHeaders();
        CollectionPlainWraps.forEach(response.getHeaderNames(), name -> result.add(name, response.getHeader(name)), name -> StringUtilsWraps.equalsAnyIgnoreCase(name, headers));
        return result.isEmpty() ? null : result;
    }

    public static HttpHeaders getHeadersExcluding(@Nullable HttpServletRequest request, @Nullable String... headers) {
        return getHeadersExcluding(request, ArrayUtilsWraps.asList(headers));
    }

    public static HttpHeaders getHeadersExcluding(@Nullable HttpServletRequest request, @Nullable Collection<String> headers) {
        if (request == null) {
            return null;
        }
        HttpHeaders result = new HttpHeaders();
        EnumerationPlainWraps.forEach(request.getHeaderNames(), name -> result.add(name, request.getHeader(name)), name -> !StringUtilsWraps.equalsAny(name, headers));
        return result.isEmpty() ? null : result;
    }

    public static HttpHeaders getHeadersExcluding(@Nullable HttpServletResponse response, @Nullable String... headers) {
        return getHeadersExcluding(response, ArrayUtilsWraps.asList(headers));
    }

    public static HttpHeaders getHeadersExcluding(@Nullable HttpServletResponse response, @Nullable Collection<String> headers) {
        if (response == null) {
            return null;
        }
        HttpHeaders result = new HttpHeaders();
        CollectionPlainWraps.forEach(response.getHeaderNames(), name -> result.add(name, response.getHeader(name)), name -> !StringUtilsWraps.equalsAny(name, headers));
        return result.isEmpty() ? null : result;
    }

    public static HttpHeaders getHeadersExcludingIgnoreCase(@Nullable HttpServletRequest request, @Nullable String... headers) {
        return getHeadersExcludingIgnoreCase(request, ArrayUtilsWraps.asList(headers));
    }

    public static HttpHeaders getHeadersExcludingIgnoreCase(@Nullable HttpServletRequest request, @Nullable Collection<String> headers) {
        if (request == null) {
            return null;
        }
        HttpHeaders result = new HttpHeaders();
        EnumerationPlainWraps.forEach(request.getHeaderNames(), name -> result.add(name, request.getHeader(name)), name -> !StringUtilsWraps.equalsAnyIgnoreCase(name, headers));
        return result.isEmpty() ? null : result;
    }

    public static HttpHeaders getHeadersExcludingIgnoreCase(@Nullable HttpServletResponse response, @Nullable String... headers) {
        return getHeadersExcludingIgnoreCase(response, ArrayUtilsWraps.asList(headers));
    }

    public static HttpHeaders getHeadersExcludingIgnoreCase(@Nullable HttpServletResponse response, @Nullable Collection<String> headers) {
        if (response == null) {
            return null;
        }
        HttpHeaders result = new HttpHeaders();
        CollectionPlainWraps.forEach(response.getHeaderNames(), name -> result.add(name, response.getHeader(name)), name -> !StringUtilsWraps.equalsAnyIgnoreCase(name, headers));
        return result.isEmpty() ? null : result;
    }

    public static boolean isAccept(@Nullable HttpServletRequest request, @Nullable String accept) {
        return request != null && StringUtils.isNotBlank(accept) && StringUtilsWraps.containsIgnoreCase(request.getHeader(HttpHeaders.ACCEPT), accept);
    }

    public static boolean isAcceptAll(@Nullable HttpServletRequest request) {
        return request != null && StringUtilsWraps.contains(request.getHeader(HttpHeaders.ACCEPT), MediaType.ALL_VALUE);
    }

    public static boolean isAcceptApplicationJson(@Nullable HttpServletRequest request) {
        return request != null && RegexUtilsWraps.findIgnoreCase(request.getHeader(HttpHeaders.ACCEPT), RegexVariantConst.APPLICATION_JSON);
    }

    public static boolean isAcceptApplicationXml(@Nullable HttpServletRequest request) {
        return request != null && RegexUtilsWraps.findIgnoreCase(request.getHeader(HttpHeaders.ACCEPT), RegexVariantConst.APPLICATION_XML);
    }

    public static boolean isAcceptTextHtml(@Nullable HttpServletRequest request) {
        return request != null && StringUtilsWraps.containsIgnoreCase(request.getHeader(HttpHeaders.ACCEPT), MediaType.TEXT_HTML_VALUE);
    }

    public static boolean isAcceptTextPlain(@Nullable HttpServletRequest request) {
        return request != null && StringUtilsWraps.containsIgnoreCase(request.getHeader(HttpHeaders.ACCEPT), MediaType.TEXT_PLAIN_VALUE);
    }

    public static boolean isContentType(@Nullable HttpServletRequest request, @Nullable String mediaType) {
        return request != null && StringUtils.isNotBlank(mediaType) && StringUtilsWraps.containsIgnoreCase(request.getContentType(), mediaType);
    }

    public static boolean isContentType(@Nullable HttpServletResponse response, @Nullable String mediaType) {
        return response != null && StringUtils.isNotBlank(mediaType) && StringUtilsWraps.containsIgnoreCase(response.getContentType(), mediaType);
    }

    public static boolean isContentTypeApplicationJson(@Nullable HttpServletRequest request) {
        return request != null && RegexUtilsWraps.findIgnoreCase(request.getContentType(), RegexVariantConst.APPLICATION_JSON);
    }

    public static boolean isContentTypeApplicationJson(@Nullable HttpServletResponse response) {
        return response != null && RegexUtilsWraps.findIgnoreCase(response.getContentType(), RegexVariantConst.APPLICATION_JSON);
    }

    public static boolean isContentTypeApplicationXml(@Nullable HttpServletRequest request) {
        return request != null && RegexUtilsWraps.findIgnoreCase(request.getContentType(), RegexVariantConst.APPLICATION_XML);
    }

    public static boolean isContentTypeApplicationXml(@Nullable HttpServletResponse response) {
        return response != null && RegexUtilsWraps.findIgnoreCase(response.getContentType(), RegexVariantConst.APPLICATION_XML);
    }

    public static boolean isContentTypeFormUrlencoded(@Nullable HttpServletRequest request) {
        return isContentType(request, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
    }

    public static boolean isContentTypeFormUrlencoded(@Nullable HttpServletResponse response) {
        return isContentType(response, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
    }

    public static boolean isContentTypeMultipart(@Nullable HttpServletRequest request) {
        return matchAnyContentTypes(request, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.MULTIPART_MIXED_VALUE, MediaType.MULTIPART_RELATED_VALUE);
    }

    public static boolean isContentTypeMultipart(@Nullable HttpServletResponse response) {
        return matchAnyContentTypes(response, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.MULTIPART_MIXED_VALUE, MediaType.MULTIPART_RELATED_VALUE);
    }

    public static boolean isContentTypeTextHtml(@Nullable HttpServletRequest request) {
        return isContentType(request, MediaType.TEXT_HTML_VALUE);
    }

    public static boolean isContentTypeTextHtml(@Nullable HttpServletResponse response) {
        return isContentType(response, MediaType.TEXT_HTML_VALUE);
    }

    public static boolean isContentTypeTextPlain(@Nullable HttpServletRequest request) {
        return isContentType(request, MediaType.TEXT_PLAIN_VALUE);
    }

    public static boolean isContentTypeTextPlain(@Nullable HttpServletResponse response) {
        return isContentType(response, MediaType.TEXT_PLAIN_VALUE);
    }

    public static boolean matchAnyAccepts(@Nullable HttpServletRequest request, @Nullable String... accepts) {
        return matchAnyAccepts(request, ArrayUtilsWraps.asList(accepts));
    }

    public static boolean matchAnyAccepts(@Nullable HttpServletRequest request, @Nullable Collection<String> accepts) {
        return request != null && StringUtilsWraps.containsAnyIgnoreCase(request.getHeader(HttpHeaders.ACCEPT), accepts);
    }

    public static boolean matchAnyContentTypes(@Nullable HttpServletRequest request, @Nullable String... mediaTypes) {
        return matchAnyContentTypes(request, ArrayUtilsWraps.asList(mediaTypes));
    }

    public static boolean matchAnyContentTypes(@Nullable HttpServletRequest request, @Nullable Collection<String> mediaTypes) {
        return request != null && StringUtilsWraps.containsAnyIgnoreCase(request.getContentType(), mediaTypes);
    }

    public static boolean matchAnyContentTypes(@Nullable HttpServletResponse response, @Nullable String... mediaTypes) {
        return matchAnyContentTypes(response, ArrayUtilsWraps.asList(mediaTypes));
    }

    public static boolean matchAnyContentTypes(@Nullable HttpServletResponse response, @Nullable Collection<String> mediaTypes) {
        return response != null && StringUtilsWraps.containsAnyIgnoreCase(response.getContentType(), mediaTypes);
    }

    public static void disableCache(@Nullable HttpServletResponse response) {
        if (response == null || response.isCommitted()) {
            return;
        }
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");    // $NON-NLS-1$
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");    // $NON-NLS-1$
        response.setDateHeader(HttpHeaders.EXPIRES, -1L);
    }

    /**
     * @see "https://www.mnot.net/cache_docs/#CACHE-CONTROL"
     */
    public static void setCacheControl(@Nullable HttpServletResponse response, @Nullable String value, @Nullable Long expires) {
        if (response == null || response.isCommitted()) {
            return;
        }
        if (StringUtils.isNotEmpty(value)) {
            response.setHeader(HttpHeaders.CACHE_CONTROL, value);    // $NON-NLS-1$
        }
        if (expires != null) {
            response.setDateHeader(HttpHeaders.EXPIRES, expires);
        }
    }

    /**
     * @see org.springframework.http.CacheControl
     */
    public static void setCacheControl(@Nullable HttpServletResponse response, @Nullable CacheControl control) {
        if (response == null || response.isCommitted() || control == null) {
            return;
        }
        response.setHeader(HttpHeaders.CACHE_CONTROL, control.getHeaderValue());
    }
}
