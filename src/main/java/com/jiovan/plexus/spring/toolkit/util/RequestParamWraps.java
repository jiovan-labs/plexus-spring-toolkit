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


import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import jakarta.annotation.Nullable;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import com.jiovan.plexus.common.toolkit.constant.CharVariantConst;
import com.jiovan.plexus.common.toolkit.constant.StringVariantConst;
import com.jiovan.plexus.common.toolkit.constant.SymbolVariantConst;
import com.jiovan.plexus.common.toolkit.util.ArrayUtilsWraps;
import com.jiovan.plexus.common.toolkit.util.CollectionPlainWraps;
import com.jiovan.plexus.common.toolkit.util.EnumerationPlainWraps;
import com.jiovan.plexus.common.toolkit.util.IterablePlainWraps;
import com.jiovan.plexus.common.toolkit.util.ListPlainWraps;
import com.jiovan.plexus.common.toolkit.util.MapPlainWraps;
import com.jiovan.plexus.common.toolkit.util.NumberUtilsWraps;
import com.jiovan.plexus.common.toolkit.util.StringUtilsWraps;


/**
 * Utilities for {@link org.springframework.web.bind.ServletRequestUtils}
 *
 * @author David Hsing
 *
 * @see org.springframework.web.bind.ServletRequestUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class RequestParamWraps {
    /**
     * Return the first non-empty cookie value.
     *
     * @param request The HTTP request, may be {@code null}
     * @param names The given names
     *
     * @return The result of the operation
     */
    @Nullable
    public static String firstNonEmptyCookie(@Nullable HttpServletRequest request, @Nullable String... names) {
        return firstNonEmptyCookie(request, ArrayUtilsWraps.asList(names));
    }

    /**
     * Return the first non-empty cookie value.
     *
     * @param request The HTTP request, may be {@code null}
     * @param names The given names
     *
     * @return The result of the operation
     */
    @Nullable
    public static String firstNonEmptyCookie(@Nullable HttpServletRequest request, @Nullable Collection<String> names) {
        if (request == null || CollectionPlainWraps.isEmpty(names)) {
            return null;
        }
        return names.stream().filter(StringUtils::isNotBlank).map(item -> getCookieValue(request, item)).filter(StringUtils::isNotEmpty).findFirst().orElse(null);
    }

    /**
     * Return the first non-blank cookie value.
     *
     * @param request The HTTP request, may be {@code null}
     * @param names The given names
     *
     * @return The result of the operation
     */
    @Nullable
    public static String firstNonBlankCookie(@Nullable HttpServletRequest request, @Nullable String... names) {
        return firstNonBlankCookie(request, ArrayUtilsWraps.asList(names));
    }

    /**
     * Return the first non-blank cookie value.
     *
     * @param request The HTTP request, may be {@code null}
     * @param names The given names
     *
     * @return The result of the operation
     */
    @Nullable
    public static String firstNonBlankCookie(@Nullable HttpServletRequest request, @Nullable Collection<String> names) {
        if (request == null || CollectionPlainWraps.isEmpty(names)) {
            return null;
        }
        return names.stream().filter(StringUtils::isNotBlank).map(item -> getCookieValue(request, item)).filter(StringUtils::isNotBlank).findFirst().orElse(null);
    }

    /**
     * Return the first non-empty header value.
     *
     * @param request The HTTP request, may be {@code null}
     * @param headers The given headers
     *
     * @return The result of the operation
     */
    @Nullable
    public static String firstNonEmptyHeader(@Nullable HttpServletRequest request, @Nullable String... headers) {
        return firstNonEmptyHeader(request, ArrayUtilsWraps.asList(headers));
    }

    /**
     * Return the first non-empty header value.
     *
     * @param request The HTTP request, may be {@code null}
     * @param headers The given headers
     *
     * @return The result of the operation
     */
    @Nullable
    public static String firstNonEmptyHeader(@Nullable HttpServletRequest request, @Nullable Collection<String> headers) {
        if (request == null || CollectionPlainWraps.isEmpty(headers)) {
            return null;
        }
        return headers.stream().filter(StringUtils::isNotBlank).map(request::getHeader).filter(StringUtils::isNotEmpty).findFirst().orElse(null);
    }

    /**
     * Return the first non-blank header value.
     *
     * @param request The HTTP request, may be {@code null}
     * @param headers The given headers
     *
     * @return The result of the operation
     */
    @Nullable
    public static String firstNonBlankHeader(@Nullable HttpServletRequest request, @Nullable String... headers) {
        return firstNonBlankHeader(request, ArrayUtilsWraps.asList(headers));
    }

    /**
     * Return the first non-blank header value.
     *
     * @param request The HTTP request, may be {@code null}
     * @param headers The given headers
     *
     * @return The result of the operation
     */
    @Nullable
    public static String firstNonBlankHeader(@Nullable HttpServletRequest request, @Nullable Collection<String> headers) {
        if (request == null || CollectionPlainWraps.isEmpty(headers)) {
            return null;
        }
        return headers.stream().filter(StringUtils::isNotBlank).map(request::getHeader).filter(StringUtils::isNotBlank).findFirst().orElse(null);
    }

    /**
     * Return the first non-empty parameter value.
     *
     * @param request The HTTP request, may be {@code null}
     * @param params The parameters, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String firstNonEmptyParameter(@Nullable HttpServletRequest request, @Nullable String... params) {
        return firstNonEmptyParameter(request, ArrayUtilsWraps.asList(params));
    }

    /**
     * Return the first non-empty parameter value.
     *
     * @param request The HTTP request, may be {@code null}
     * @param params The parameters, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String firstNonEmptyParameter(@Nullable HttpServletRequest request, @Nullable Collection<String> params) {
        if (request == null || CollectionPlainWraps.isEmpty(params)) {
            return null;
        }
        return params.stream().filter(StringUtils::isNotBlank).map(request::getParameter).filter(StringUtils::isNotEmpty).findFirst().orElse(null);
    }

    /**
     * Return the first non-blank parameter value.
     *
     * @param request The HTTP request, may be {@code null}
     * @param params The parameters, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String firstNonBlankParameter(@Nullable HttpServletRequest request, @Nullable String... params) {
        return firstNonBlankParameter(request, ArrayUtilsWraps.asList(params));
    }

    /**
     * Return the first non-blank parameter value.
     *
     * @param request The HTTP request, may be {@code null}
     * @param params The parameters, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String firstNonBlankParameter(@Nullable HttpServletRequest request, @Nullable Collection<String> params) {
        if (request == null || CollectionPlainWraps.isEmpty(params)) {
            return null;
        }
        return params.stream().filter(StringUtils::isNotBlank).map(request::getParameter).filter(StringUtils::isNotBlank).findFirst().orElse(null);
    }

    /**
     * Return the cookie value as a boolean.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Boolean getBooleanCookie(@Nullable HttpServletRequest request, @Nullable String name) {
        return getBooleanCookie(request, name, null);
    }

    /**
     * Return the cookie value as a boolean.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Boolean getBooleanCookie(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Boolean defaultValue) {
        return ObjectUtils.getIfNull(BooleanUtils.toBooleanObject(getStringCookieTrimming(request, name)), defaultValue);
    }

    /**
     * Return the cookie value as a double.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Double getDoubleCookie(@Nullable HttpServletRequest request, @Nullable String name) {
        return getDoubleCookie(request, name, null);
    }

    /**
     * Return the cookie value as a double.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Double getDoubleCookie(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Double defaultValue) {
        return ObjectUtils.getIfNull(NumberUtilsWraps.toDouble(getStringCookieTrimming(request, name)), defaultValue);
    }

    /**
     * Return the cookie value as a float.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Float getFloatCookie(@Nullable HttpServletRequest request, @Nullable String name) {
        return getFloatCookie(request, name, null);
    }

    /**
     * Return the cookie value as a float.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Float getFloatCookie(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Float defaultValue) {
        return ObjectUtils.getIfNull(NumberUtilsWraps.toFloat(getStringCookieTrimming(request, name)), defaultValue);
    }

    /**
     * Return the cookie value as an integer.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Integer getIntegerCookie(@Nullable HttpServletRequest request, @Nullable String name) {
        return getIntegerCookie(request, name, null);
    }

    /**
     * Return the cookie value as an integer.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Integer getIntegerCookie(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Integer defaultValue) {
        return ObjectUtils.getIfNull(NumberUtilsWraps.toInteger(getStringCookieTrimming(request, name)), defaultValue);
    }

    /**
     * Return the cookie value as a long.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Long getLongCookie(@Nullable HttpServletRequest request, @Nullable String name) {
        return getLongCookie(request, name, null);
    }

    /**
     * Return the cookie value as a long.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Long getLongCookie(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Long defaultValue) {
        return ObjectUtils.getIfNull(NumberUtilsWraps.toLong(getStringCookieTrimming(request, name)), defaultValue);
    }

    /**
     * Return the cookie value as a short.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Short getShortCookie(@Nullable HttpServletRequest request, @Nullable String name) {
        return getShortCookie(request, name, null);
    }

    /**
     * Return the cookie value as a short.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Short getShortCookie(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Short defaultValue) {
        return ObjectUtils.getIfNull(NumberUtilsWraps.toShort(getStringCookieTrimming(request, name)), defaultValue);
    }

    /**
     * Return the cookie value as a string.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getStringCookie(@Nullable HttpServletRequest request, @Nullable String name) {
        return getStringCookie(request, name, null);
    }

    /**
     * Return the cookie value as a string.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getStringCookie(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String defaultValue) {
        String result = (request == null || StringUtils.isBlank(name)) ? null : getCookieValue(request, name);
        return ObjectUtils.getIfNull(result, defaultValue);
    }

    /**
     * Return the cookie value as a string with surrounding whitespace trimmed.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getStringCookieTrimming(@Nullable HttpServletRequest request, @Nullable String name) {
        return getStringCookieTrimming(request, name, false, null);
    }

    /**
     * Return the cookie value as a string with surrounding whitespace trimmed.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getStringCookieTrimming(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String defaultValue) {
        return getStringCookieTrimming(request, name, false, defaultValue);
    }

    /**
     * Return the cookie value as a string with surrounding whitespace trimmed.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getStringCookieTrimming(@Nullable HttpServletRequest request, @Nullable String name, boolean emptyAsNull) {
        return getStringCookieTrimming(request, name, emptyAsNull, null);
    }

    /**
     * Return the cookie value as a string with surrounding whitespace trimmed.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getStringCookieTrimming(@Nullable HttpServletRequest request, @Nullable String name, boolean emptyAsNull, @Nullable String defaultValue) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValue;
        }
        String value = getCookieValue(request, name);
        String result = emptyAsNull ? StringUtils.trimToNull(value) : StringUtils.trim(value);
        return ObjectUtils.getIfNull(result, defaultValue);
    }

    /**
     * Build a map of request cookie names to values.
     *
     * @param request The HTTP request, may be {@code null}
     */
    @Nullable
    public static Map<String, String> getCookieObjectMap(@Nullable HttpServletRequest request) {
        return getCookieObjectMap(request, false, null);
    }

    /**
     * Build a map of request cookie names to values.
     *
     * @param request The HTTP request, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     */
    @Nullable
    public static Map<String, String> getCookieObjectMap(@Nullable HttpServletRequest request, boolean emptyAsNull) {
        return getCookieObjectMap(request, emptyAsNull, null);
    }

    /**
     * Build a map of request cookie names to values.
     *
     * @param request The HTTP request, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     * @param defaultValues The given defaultValues
     */
    @Nullable
    public static Map<String, String> getCookieObjectMap(@Nullable HttpServletRequest request, boolean emptyAsNull, @Nullable Map<String, String> defaultValues) {
        if (request == null) {
            return defaultValues;
        }
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isEmpty(cookies)) {
            return defaultValues;
        }
        Map<String, String> result = new LinkedHashMap<>();
        for (Cookie cookie : cookies) {
            String name = cookie.getName(), value = cookie.getValue();
            result.put(name, emptyAsNull ? StringUtils.trimToNull(value) : value);
        }
        return result.isEmpty() ? defaultValues : result;
    }

    /**
     * Return the header value as a boolean.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Boolean getBooleanHeader(@Nullable HttpServletRequest request, @Nullable String name) {
        return getBooleanHeader(request, name, null);
    }

    /**
     * Return the header value as a boolean.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Boolean getBooleanHeader(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Boolean defaultValue) {
        return ObjectUtils.getIfNull(BooleanUtils.toBooleanObject(getStringHeaderTrimming(request, name)), defaultValue);
    }

    /**
     * Return the headers value as a boolean.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Boolean[] getBooleanHeaders(@Nullable HttpServletRequest request, @Nullable String name) {
        return getBooleanHeaders(request, name, null);
    }

    /**
     * Return the headers value as a boolean.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValues The given defaultValues
     *
     * @return The result of the operation
     */
    @Nullable
    public static Boolean[] getBooleanHeaders(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Boolean[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = getHeaderValues(request, name);
        Boolean[] result = (values == null) ? null : Arrays.stream(values).map(BooleanUtils::toBooleanObject).toArray(Boolean[]::new);
        return ObjectUtils.getIfNull(result, defaultValues);
    }

    /**
     * Return the header value as a double.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Double getDoubleHeader(@Nullable HttpServletRequest request, @Nullable String name) {
        return getDoubleHeader(request, name, null);
    }

    /**
     * Return the header value as a double.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Double getDoubleHeader(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Double defaultValue) {
        return ObjectUtils.getIfNull(NumberUtilsWraps.toDouble(getStringHeaderTrimming(request, name)), defaultValue);
    }

    /**
     * Return the headers value as a double.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Double[] getDoubleHeaders(@Nullable HttpServletRequest request, @Nullable String name) {
        return getDoubleHeaders(request, name, null);
    }

    /**
     * Return the headers value as a double.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValues The given defaultValues
     *
     * @return The result of the operation
     */
    @Nullable
    public static Double[] getDoubleHeaders(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Double[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = getHeaderValues(request, name);
        Double[] result = (values == null) ? null : Arrays.stream(values).map(NumberUtilsWraps::toDouble).toArray(Double[]::new);
        return ObjectUtils.getIfNull(result, defaultValues);
    }

    /**
     * Return the header value as a float.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Float getFloatHeader(@Nullable HttpServletRequest request, @Nullable String name) {
        return getFloatHeader(request, name, null);
    }

    /**
     * Return the header value as a float.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Float getFloatHeader(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Float defaultValue) {
        return ObjectUtils.getIfNull(NumberUtilsWraps.toFloat(getStringHeaderTrimming(request, name)), defaultValue);
    }

    /**
     * Return the headers value as a float.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Float[] getFloatHeaders(@Nullable HttpServletRequest request, @Nullable String name) {
        return getFloatHeaders(request, name, null);
    }

    /**
     * Return the headers value as a float.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValues The given defaultValues
     *
     * @return The result of the operation
     */
    @Nullable
    public static Float[] getFloatHeaders(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Float[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = getHeaderValues(request, name);
        Float[] result = (values == null) ? null : Arrays.stream(values).map(NumberUtilsWraps::toFloat).toArray(Float[]::new);
        return ObjectUtils.getIfNull(result, defaultValues);
    }

    /**
     * Return the header value as an integer.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Integer getIntegerHeader(@Nullable HttpServletRequest request, @Nullable String name) {
        return getIntegerHeader(request, name, null);
    }

    /**
     * Return the header value as an integer.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Integer getIntegerHeader(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Integer defaultValue) {
        return ObjectUtils.getIfNull(NumberUtilsWraps.toInteger(getStringHeaderTrimming(request, name)), defaultValue);
    }

    /**
     * Return the headers value as an integer.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Integer[] getIntegerHeaders(@Nullable HttpServletRequest request, @Nullable String name) {
        return getIntegerHeaders(request, name, null);
    }

    /**
     * Return the headers value as an integer.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValues The given defaultValues
     *
     * @return The result of the operation
     */
    @Nullable
    public static Integer[] getIntegerHeaders(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Integer[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = getHeaderValues(request, name);
        Integer[] result = (values == null) ? null : Arrays.stream(values).map(NumberUtilsWraps::toInteger).toArray(Integer[]::new);
        return ObjectUtils.getIfNull(result, defaultValues);
    }

    /**
     * Return the header value as a long.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Long getLongHeader(@Nullable HttpServletRequest request, @Nullable String name) {
        return getLongHeader(request, name, null);
    }

    /**
     * Return the header value as a long.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Long getLongHeader(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Long defaultValue) {
        return ObjectUtils.getIfNull(NumberUtilsWraps.toLong(getStringHeaderTrimming(request, name)), defaultValue);
    }

    /**
     * Return the headers value as a long.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Long[] getLongHeaders(@Nullable HttpServletRequest request, @Nullable String name) {
        return getLongHeaders(request, name, null);
    }

    /**
     * Return the headers value as a long.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValues The given defaultValues
     *
     * @return The result of the operation
     */
    @Nullable
    public static Long[] getLongHeaders(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Long[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = getHeaderValues(request, name);
        Long[] result = (values == null) ? null : Arrays.stream(values).map(NumberUtilsWraps::toLong).toArray(Long[]::new);
        return ObjectUtils.getIfNull(result, defaultValues);
    }

    /**
     * Return the header value as a short.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Short getShortHeader(@Nullable HttpServletRequest request, @Nullable String name) {
        return getShortHeader(request, name, null);
    }

    /**
     * Return the header value as a short.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Short getShortHeader(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Short defaultValue) {
        return ObjectUtils.getIfNull(NumberUtilsWraps.toShort(getStringHeaderTrimming(request, name)), defaultValue);
    }

    /**
     * Return the headers value as a short.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Short[] getShortHeaders(@Nullable HttpServletRequest request, @Nullable String name) {
        return getShortHeaders(request, name, null);
    }

    /**
     * Return the headers value as a short.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValues The given defaultValues
     *
     * @return The result of the operation
     */
    @Nullable
    public static Short[] getShortHeaders(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Short[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = getHeaderValues(request, name);
        Short[] result = (values == null) ? null : Arrays.stream(values).map(NumberUtilsWraps::toShort).toArray(Short[]::new);
        return ObjectUtils.getIfNull(result, defaultValues);
    }

    /**
     * Return the header value as a string.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getStringHeader(@Nullable HttpServletRequest request, @Nullable String name) {
        return getStringHeader(request, name, null);
    }

    /**
     * Return the header value as a string.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getStringHeader(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String defaultValue) {
        String result = (request == null || StringUtils.isBlank(name)) ? null : request.getHeader(name);
        return ObjectUtils.getIfNull(result, defaultValue);
    }

    /**
     * Return the headers value as a string.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String[] getStringHeaders(@Nullable HttpServletRequest request, @Nullable String name) {
        return getStringHeaders(request, name, null);
    }

    /**
     * Return the headers value as a string.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValues The given defaultValues
     *
     * @return The result of the operation
     */
    @Nullable
    public static String[] getStringHeaders(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String[] defaultValues) {
        String[] result = (request == null || StringUtils.isBlank(name)) ? null : getHeaderValues(request, name);
        return ObjectUtils.getIfNull(result, defaultValues);
    }

    /**
     * Return the header value as a string with surrounding whitespace trimmed.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getStringHeaderTrimming(@Nullable HttpServletRequest request, @Nullable String name) {
        return getStringHeaderTrimming(request, name, false, null);
    }

    /**
     * Return the header value as a string with surrounding whitespace trimmed.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getStringHeaderTrimming(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String defaultValue) {
        return getStringHeaderTrimming(request, name, false, defaultValue);
    }

    /**
     * Return the header value as a string with surrounding whitespace trimmed.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getStringHeaderTrimming(@Nullable HttpServletRequest request, @Nullable String name, boolean emptyAsNull) {
        return getStringHeaderTrimming(request, name, emptyAsNull, null);
    }

    /**
     * Return the header value as a string with surrounding whitespace trimmed.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getStringHeaderTrimming(@Nullable HttpServletRequest request, @Nullable String name, boolean emptyAsNull, @Nullable String defaultValue) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValue;
        }
        String result = emptyAsNull ? StringUtils.trimToNull(request.getHeader(name)) : StringUtils.trim(request.getHeader(name));
        return ObjectUtils.getIfNull(result, defaultValue);
    }

    /**
     * Return the headers value as a string with surrounding whitespace trimmed.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String[] getStringHeadersTrimming(@Nullable HttpServletRequest request, @Nullable String name) {
        return getStringHeadersTrimming(request, name, false, null);
    }

    /**
     * Return the headers value as a string with surrounding whitespace trimmed.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValues The given defaultValues
     *
     * @return The result of the operation
     */
    @Nullable
    public static String[] getStringHeadersTrimming(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String[] defaultValues) {
        return getStringHeadersTrimming(request, name, false, defaultValues);
    }

    /**
     * Return the headers value as a string with surrounding whitespace trimmed.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     *
     * @return The result of the operation
     */
    @Nullable
    public static String[] getStringHeadersTrimming(@Nullable HttpServletRequest request, @Nullable String name, boolean emptyAsNull) {
        return getStringHeadersTrimming(request, name, emptyAsNull, null);
    }

    /**
     * Return the headers value as a string with surrounding whitespace trimmed.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     * @param defaultValues The given defaultValues
     *
     * @return The result of the operation
     */
    @Nullable
    public static String[] getStringHeadersTrimming(@Nullable HttpServletRequest request, @Nullable String name, boolean emptyAsNull, @Nullable String[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        return ObjectUtils.getIfNull(StringUtilsWraps.trimStringArray(emptyAsNull, getHeaderValues(request, name)), defaultValues);
    }

    /**
     * Build a map of request header names to values.
     *
     * @param request The HTTP request, may be {@code null}
     */
    @Nullable
    public static Map<String, Object> getHeaderObjectMap(@Nullable HttpServletRequest request) {
        return getHeaderObjectMap(request, false, null);
    }

    /**
     * Build a map of request header names to values.
     *
     * @param request The HTTP request, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     */
    @Nullable
    public static Map<String, Object> getHeaderObjectMap(@Nullable HttpServletRequest request, boolean emptyAsNull) {
        return getHeaderObjectMap(request, emptyAsNull, null);
    }

    /**
     * Build a map of request header names to values.
     *
     * @param request The HTTP request, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     * @param defaultValues The given defaultValues
     */
    @Nullable
    public static Map<String, Object> getHeaderObjectMap(@Nullable HttpServletRequest request, boolean emptyAsNull, @Nullable Map<String, Object> defaultValues) {
        if (request == null) {
            return defaultValues;
        }
        Set<String> names = EnumerationPlainWraps.toElementSet(request.getHeaderNames());
        Map<String, Object> result = new LinkedHashMap<>();
        IterablePlainWraps.forEach(names, name -> {
            List<String> values = EnumerationPlainWraps.toElementList(request.getHeaders(name));
            if (CollectionPlainWraps.isEmpty(values)) {
                result.put(name, null);
                return;
            }
            if (CollectionPlainWraps.isSingleton(values)) {
                String value = ListPlainWraps.getFirst(values);
                result.put(name, emptyAsNull ? StringUtils.trimToNull(value) : value);
                return;
            }
            StringUtilsWraps.trimStringCollection(emptyAsNull, values);
            result.put(name, values);
        }, StringUtils::isNotBlank);
        return result.isEmpty() ? defaultValues : result;
    }

    /**
     * Return the parameter value as a boolean.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Boolean getBooleanParameter(@Nullable ServletRequest request, @Nullable String name) {
        return getBooleanParameter(request, name, null);
    }

    /**
     * Return the parameter value as a boolean.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Boolean getBooleanParameter(@Nullable ServletRequest request, @Nullable String name, @Nullable Boolean defaultValue) {
        return ObjectUtils.getIfNull(BooleanUtils.toBooleanObject(getStringParameterTrimming(request, name)), defaultValue);
    }

    /**
     * Return the parameters value as a boolean.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Boolean[] getBooleanParameters(@Nullable ServletRequest request, @Nullable String name) {
        return getBooleanParameters(request, name, null);
    }

    /**
     * Return the parameters value as a boolean.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValues The given defaultValues
     *
     * @return The result of the operation
     */
    @Nullable
    public static Boolean[] getBooleanParameters(@Nullable ServletRequest request, @Nullable String name, @Nullable Boolean[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = request.getParameterValues(name);
        Boolean[] result = (values == null) ? null : Arrays.stream(values).map(BooleanUtils::toBooleanObject).toArray(Boolean[]::new);
        return ObjectUtils.getIfNull(result, defaultValues);
    }

    /**
     * Return the parameter value as a double.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Double getDoubleParameter(@Nullable ServletRequest request, @Nullable String name) {
        return getDoubleParameter(request, name, null);
    }

    /**
     * Return the parameter value as a double.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Double getDoubleParameter(@Nullable ServletRequest request, @Nullable String name, @Nullable Double defaultValue) {
        return ObjectUtils.getIfNull(NumberUtilsWraps.toDouble(getStringParameterTrimming(request, name)), defaultValue);
    }

    /**
     * Return the parameters value as a double.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Double[] getDoubleParameters(@Nullable ServletRequest request, @Nullable String name) {
        return getDoubleParameters(request, name, null);
    }

    /**
     * Return the parameters value as a double.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValues The given defaultValues
     *
     * @return The result of the operation
     */
    @Nullable
    public static Double[] getDoubleParameters(@Nullable ServletRequest request, @Nullable String name, @Nullable Double[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = request.getParameterValues(name);
        Double[] result = (values == null) ? null : Arrays.stream(values).map(NumberUtilsWraps::toDouble).toArray(Double[]::new);
        return ObjectUtils.getIfNull(result, defaultValues);
    }

    /**
     * Return the parameter value as a float.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Float getFloatParameter(@Nullable ServletRequest request, @Nullable String name) {
        return getFloatParameter(request, name, null);
    }

    /**
     * Return the parameter value as a float.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Float getFloatParameter(@Nullable ServletRequest request, @Nullable String name, @Nullable Float defaultValue) {
        return ObjectUtils.getIfNull(NumberUtilsWraps.toFloat(getStringParameterTrimming(request, name)), defaultValue);
    }

    /**
     * Return the parameters value as a float.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Float[] getFloatParameters(@Nullable ServletRequest request, @Nullable String name) {
        return getFloatParameters(request, name, null);
    }

    /**
     * Return the parameters value as a float.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValues The given defaultValues
     *
     * @return The result of the operation
     */
    @Nullable
    public static Float[] getFloatParameters(@Nullable ServletRequest request, @Nullable String name, @Nullable Float[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = request.getParameterValues(name);
        Float[] result = (values == null) ? null : Arrays.stream(values).map(NumberUtilsWraps::toFloat).toArray(Float[]::new);
        return ObjectUtils.getIfNull(result, defaultValues);
    }

    /**
     * Return the parameter value as an integer.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Integer getIntegerParameter(@Nullable ServletRequest request, @Nullable String name) {
        return getIntegerParameter(request, name, null);
    }

    /**
     * Return the parameter value as an integer.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Integer getIntegerParameter(@Nullable ServletRequest request, @Nullable String name, @Nullable Integer defaultValue) {
        return ObjectUtils.getIfNull(NumberUtilsWraps.toInteger(getStringParameterTrimming(request, name)), defaultValue);
    }

    /**
     * Return the parameters value as an integer.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Integer[] getIntegerParameters(@Nullable ServletRequest request, @Nullable String name) {
        return getIntegerParameters(request, name, null);
    }

    /**
     * Return the parameters value as an integer.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValues The given defaultValues
     *
     * @return The result of the operation
     */
    @Nullable
    public static Integer[] getIntegerParameters(@Nullable ServletRequest request, @Nullable String name, @Nullable Integer[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = request.getParameterValues(name);
        Integer[] result = (values == null) ? null : Arrays.stream(values).map(NumberUtilsWraps::toInteger).toArray(Integer[]::new);
        return ObjectUtils.getIfNull(result, defaultValues);
    }

    /**
     * Return the parameter value as a long.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Long getLongParameter(@Nullable ServletRequest request, @Nullable String name) {
        return getLongParameter(request, name, null);
    }

    /**
     * Return the parameter value as a long.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Long getLongParameter(@Nullable ServletRequest request, @Nullable String name, @Nullable Long defaultValue) {
        return ObjectUtils.getIfNull(NumberUtilsWraps.toLong(getStringParameterTrimming(request, name)), defaultValue);
    }

    /**
     * Return the parameters value as a long.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Long[] getLongParameters(@Nullable ServletRequest request, @Nullable String name) {
        return getLongParameters(request, name, null);
    }

    /**
     * Return the parameters value as a long.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValues The given defaultValues
     *
     * @return The result of the operation
     */
    @Nullable
    public static Long[] getLongParameters(@Nullable ServletRequest request, @Nullable String name, @Nullable Long[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = request.getParameterValues(name);
        Long[] result = (values == null) ? null : Arrays.stream(values).map(NumberUtilsWraps::toLong).toArray(Long[]::new);
        return ObjectUtils.getIfNull(result, defaultValues);
    }

    /**
     * Return the parameter value as a short.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Short getShortParameter(@Nullable ServletRequest request, @Nullable String name) {
        return getShortParameter(request, name, null);
    }

    /**
     * Return the parameter value as a short.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Short getShortParameter(@Nullable ServletRequest request, @Nullable String name, @Nullable Short defaultValue) {
        return ObjectUtils.getIfNull(NumberUtilsWraps.toShort(getStringParameterTrimming(request, name)), defaultValue);
    }

    /**
     * Return the parameters value as a short.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Short[] getShortParameters(@Nullable ServletRequest request, @Nullable String name) {
        return getShortParameters(request, name, null);
    }

    /**
     * Return the parameters value as a short.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValues The given defaultValues
     *
     * @return The result of the operation
     */
    @Nullable
    public static Short[] getShortParameters(@Nullable ServletRequest request, @Nullable String name, @Nullable Short[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        String[] values = request.getParameterValues(name);
        Short[] result = (values == null) ? null : Arrays.stream(values).map(NumberUtilsWraps::toShort).toArray(Short[]::new);
        return ObjectUtils.getIfNull(result, defaultValues);
    }

    /**
     * Return the parameter value as a string.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getStringParameter(@Nullable ServletRequest request, @Nullable String name) {
        return getStringParameter(request, name, null);
    }

    /**
     * Return the parameter value as a string.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getStringParameter(@Nullable ServletRequest request, @Nullable String name, @Nullable String defaultValue) {
        String result = (request == null || StringUtils.isBlank(name)) ? null : request.getParameter(name);
        return ObjectUtils.getIfNull(result, defaultValue);
    }

    /**
     * Return the parameters value as a string.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String[] getStringParameters(@Nullable ServletRequest request, @Nullable String name) {
        return getStringParameters(request, name, null);
    }

    /**
     * Return the parameters value as a string.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValues The given defaultValues
     *
     * @return The result of the operation
     */
    @Nullable
    public static String[] getStringParameters(@Nullable ServletRequest request, @Nullable String name, @Nullable String[] defaultValues) {
        String[] result = (request == null || StringUtils.isBlank(name)) ? null : request.getParameterValues(name);
        return ObjectUtils.getIfNull(result, defaultValues);
    }

    /**
     * Return the parameter value as a string with surrounding whitespace trimmed.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getStringParameterTrimming(@Nullable ServletRequest request, @Nullable String name) {
        return getStringParameterTrimming(request, name, false, null);
    }

    /**
     * Return the parameter value as a string with surrounding whitespace trimmed.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getStringParameterTrimming(@Nullable ServletRequest request, @Nullable String name, @Nullable String defaultValue) {
        return getStringParameterTrimming(request, name, false, defaultValue);
    }

    /**
     * Return the parameter value as a string with surrounding whitespace trimmed.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getStringParameterTrimming(@Nullable ServletRequest request, @Nullable String name, boolean emptyAsNull) {
        return getStringParameterTrimming(request, name, emptyAsNull, null);
    }

    /**
     * Return the parameter value as a string with surrounding whitespace trimmed.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getStringParameterTrimming(@Nullable ServletRequest request, @Nullable String name, boolean emptyAsNull, @Nullable String defaultValue) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValue;
        }
        String value = request.getParameter(name);
        String result = emptyAsNull ? StringUtils.trimToNull(value) : StringUtils.trim(value);
        return ObjectUtils.getIfNull(result, defaultValue);
    }

    /**
     * Return the parameters value as a string with surrounding whitespace trimmed.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String[] getStringParametersTrimming(@Nullable ServletRequest request, @Nullable String name) {
        return getStringParametersTrimming(request, name, false, null);
    }

    /**
     * Return the parameters value as a string with surrounding whitespace trimmed.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValues The given defaultValues
     *
     * @return The result of the operation
     */
    @Nullable
    public static String[] getStringParametersTrimming(@Nullable ServletRequest request, @Nullable String name, @Nullable String[] defaultValues) {
        return getStringParametersTrimming(request, name, false, defaultValues);
    }

    /**
     * Return the parameters value as a string with surrounding whitespace trimmed.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     *
     * @return The result of the operation
     */
    @Nullable
    public static String[] getStringParametersTrimming(@Nullable ServletRequest request, @Nullable String name, boolean emptyAsNull) {
        return getStringParametersTrimming(request, name, emptyAsNull, null);
    }

    /**
     * Return the parameters value as a string with surrounding whitespace trimmed.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     * @param defaultValues The given defaultValues
     *
     * @return The result of the operation
     */
    @Nullable
    public static String[] getStringParametersTrimming(@Nullable ServletRequest request, @Nullable String name, boolean emptyAsNull, @Nullable String[] defaultValues) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValues;
        }
        return ObjectUtils.getIfNull(StringUtilsWraps.trimStringArray(emptyAsNull, request.getParameterValues(name)), defaultValues);
    }

    /**
     * Build a map of request parameter names to values.
     *
     * @param request The HTTP request, may be {@code null}
     */
    public static Map<String, Object> getParameterObjectMap(@Nullable HttpServletRequest request) {
        return getParameterObjectMap(request, false, false, false);
    }

    /**
     * Build a map of request parameter names to values.
     *
     * @param request The HTTP request, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     */
    public static Map<String, Object> getParameterObjectMap(@Nullable HttpServletRequest request, boolean emptyAsNull) {
        return getParameterObjectMap(request, emptyAsNull, false, false);
    }

    /**
     * Build a map of request parameter names to values.
     *
     * @param request The HTTP request, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     * @param includePayload The given includePayload
     */
    public static Map<String, Object> getParameterObjectMap(@Nullable HttpServletRequest request, boolean emptyAsNull, boolean includePayload) {
        return getParameterObjectMap(request, emptyAsNull, includePayload, false);
    }

    /**
     * Returns a {@link java.util.LinkedHashMap} that contains all the request parameters
     *
     * @param request The http servlet request to inspect
     * @param emptyAsNull Whether returns null if there isn't any parameters or the parameter value is empty
     * @param includePayload Whether includes the payload parameters when the request is an ajax request
     * @param unwrapSingleArray Whether to unwrap single array parameters to plain value, e.g. [1] -> 1. Useful for binding mobile picker values
     *
     * @return a {@link java.util.LinkedHashMap} that contains all the request parameters
     */
    public static Map<String, Object> getParameterObjectMap(@Nullable HttpServletRequest request, boolean emptyAsNull, boolean includePayload, boolean unwrapSingleArray) {
        Map<String, Object> result = new LinkedHashMap<>();
        if (request == null) {
            return emptyAsNull ? null : result;
        }
        MapPlainWraps.forEach(request.getParameterMap(), (key, values) -> {
            if (ArrayUtils.isEmpty(values)) {
                result.put(key, null);
                return;
            }
            if (StringUtilsWraps.endsWith(key, SymbolVariantConst.SQUARE_BRACKETS) || ArrayUtils.getLength(values) > 1) {
                result.put(key, ArrayUtilsWraps.asList(values));
                return;
            }
            result.put(key, emptyAsNull ? StringUtils.trimToNull(values[0]) : values[0]);
        }, (key, values) -> StringUtils.isNotBlank(key));
        // Checks if the request has payload
        if (!includePayload || !WebUtilsWraps.isRestRequest(request)) {
            return emptyAsNull ? MapPlainWraps.emptyAsNull(result) : result;
        }
        // Converts the payload into map and processes it
        String content = WebUtilsWraps.getContentAsStringQuietly(request);
        Map<String, Object> payloads = JsonParserWraps.parseJsonToMap(content, null);
        if (MapPlainWraps.isEmpty(payloads)) {
            return emptyAsNull ? MapPlainWraps.emptyAsNull(result) : result;
        }
        MapPlainWraps.forEach(payloads, (key, value) -> {
            if (value instanceof String instance && emptyAsNull) {
                result.put(key, StringUtils.trimToNull(instance));
            } else if (value instanceof Collection<?> instance) {
                Object[] values = instance.toArray();
                result.put(key, (unwrapSingleArray && ArrayUtils.getLength(values) == 1) ? ArrayUtilsWraps.getFirst(values) : values);
            } else {
                result.put(key, value);
            }
        }, (key, value) -> StringUtils.isNotBlank(key));
        return emptyAsNull ? MapPlainWraps.emptyAsNull(result) : result;
    }

    /**
     * Build a parameter map from the current context request.
     */
    public static Map<String, Object> getContextRequestParameterObjectMap() {
        return getContextRequestParameterObjectMap(true, false, false);
    }

    /**
     * Returns a {@link java.util.LinkedHashMap} that contains all the context request parameters
     *
     * @param emptyAsNull Whether returns null if there isn't any parameters or the parameter value is empty
     * @param includePayload Whether includes the payload parameters when the request is an ajax request
     * @param unwrapSingleArray Whether to unwrap single array parameters to plain value, e.g. [1] -> 1. Useful for binding mobile picker values
     *
     * @return a {@link java.util.LinkedHashMap} that contains all the context request parameters
     */
    public static Map<String, Object> getContextRequestParameterObjectMap(boolean emptyAsNull, boolean includePayload, boolean unwrapSingleArray) {
        return getParameterObjectMap(WebUtilsWraps.getContextServletRequest(), emptyAsNull, includePayload, unwrapSingleArray);
    }

    /**
     * Return the named cookie from the request.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    public static Cookie getCookie(@Nullable HttpServletRequest request, @Nullable String name) {
        return getCookie(request, name, null);
    }

    /**
     * Return the named cookie from the request.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    public static Cookie getCookie(@Nullable HttpServletRequest request, @Nullable String name, @Nullable Cookie defaultValue) {
        if (request == null || StringUtils.isBlank(name)) {
            return defaultValue;
        }
        return Arrays.stream(request.getCookies()).filter(item -> StringUtilsWraps.equals(item.getName(), name)).findFirst().orElse(defaultValue);
    }

    /**
     * Return the value of the named cookie.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    public static String getCookieValue(@Nullable HttpServletRequest request, @Nullable String name) {
        return getCookieValue(request, name, null);
    }

    /**
     * Return the value of the named cookie.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    public static String getCookieValue(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String defaultValue) {
        Cookie cookie = getCookie(request, name);
        return (cookie == null) ? defaultValue : cookie.getValue();
    }

    /**
     * @see org.springframework.http.ResponseCookie
     */
    public static List<Cookie> getCookies(@Nullable HttpServletRequest request) {
        return (request == null) ? null : ArrayUtilsWraps.asList(true, request.getCookies());
    }

    /**
     * Render all cookies as a string.
     *
     * @param request The HTTP request, may be {@code null}
     *
     * @return The result of the operation
     */
    public static String getCookiesAsString(@Nullable HttpServletRequest request) {
        return getCookiesAsString(request, CharUtils.toString(CharVariantConst.EQUAL), SymbolVariantConst.SEMICOLON_SPACE);
    }

    /**
     * Render all cookies as a string.
     *
     * @param request The HTTP request, may be {@code null}
     * @param keyValueDelimiter The given keyValueDelimiter
     * @param groupDelimiter The given groupDelimiter
     *
     * @return The result of the operation
     */
    @SuppressWarnings("DataFlowIssue")
    public static String getCookiesAsString(@Nullable HttpServletRequest request, @Nullable CharSequence keyValueDelimiter, @Nullable CharSequence groupDelimiter) {
        if (request == null || StringUtils.isAnyBlank(keyValueDelimiter, groupDelimiter)) {
            return null;
        }
        List<Cookie> cookies = getCookies(request);
        if (CollectionPlainWraps.isEmpty(cookies)) {
            return null;
        }
        StringJoiner joiner = new StringJoiner(groupDelimiter);
        cookies.forEach(cookie -> joiner.add(StringUtils.join(cookie.getName(), keyValueDelimiter, StringUtils.defaultString(cookie.getValue()))));
        return joiner.toString();
    }

    /**
     * Return the value of the named header.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    public static String getHeaderValue(@Nullable HttpServletRequest request, @Nullable String name) {
        return getHeaderValue(request, name, null);
    }

    /**
     * Return the value of the named header.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    public static String getHeaderValue(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String defaultValue) {
        return (request == null || StringUtils.isBlank(name)) ? defaultValue : Objects.toString(request.getHeader(name), defaultValue);
    }

    /**
     * Return all values of the named header.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     *
     * @return The result of the operation
     */
    public static String[] getHeaderValues(@Nullable HttpServletRequest request, @Nullable String name) {
        return getHeaderValues(request, name, null);
    }

    /**
     * Return all values of the named header.
     *
     * @param request The HTTP request, may be {@code null}
     * @param name The name, may be {@code null}
     * @param defaultValues The given defaultValues
     *
     * @return The result of the operation
     */
    public static String[] getHeaderValues(@Nullable HttpServletRequest request, @Nullable String name, @Nullable String[] defaultValues) {
        return (request == null || StringUtils.isBlank(name)) ? defaultValues : EnumerationPlainWraps.toElementArray(request.getHeaders(name));
    }

    /**
     * Return whether the given path variable value is valid.
     *
     * @param sequence The given sequence
     *
     * @return Whether the condition holds
     */
    public static boolean isValidPathVariable(@Nullable CharSequence sequence) {
        return StringUtils.isNotBlank(sequence) && !StringUtilsWraps.equalsAnyIgnoreCase(sequence, StringVariantConst.NULL, StringVariantConst.UNDEFINED);
    }
}
