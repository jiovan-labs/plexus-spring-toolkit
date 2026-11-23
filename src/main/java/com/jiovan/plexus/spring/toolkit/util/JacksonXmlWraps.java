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


import java.util.Locale;
import java.util.TimeZone;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.dataformat.xml.XmlMapper;


/**
 * Utilities for {@link tools.jackson.dataformat.xml.XmlMapper}, kept apart from
 * {@link JacksonJsonWraps} so that the optional {@code jackson-dataformat-xml}
 * dependency is never resolved while loading the core Jackson utilities.
 *
 * @author David Hsing
 *
 * @see tools.jackson.dataformat.xml.XmlMapper
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public abstract class JacksonXmlWraps {
    /**
     * Build a fully configured XML object mapper.
     *
     * @return The configured XML object mapper
     */
    @Nonnull
    public static ObjectMapper xmlObjectMapper() {
        return xmlObjectMapper(null, null, null, null, null);
    }

    /**
     * Build a fully configured XML object mapper.
     *
     * @param timeZone The time zone, may be {@code null}
     * @param locale The locale, may be {@code null}
     *
     * @return The configured XML object mapper
     */
    @Nonnull
    public static ObjectMapper xmlObjectMapper(@Nullable TimeZone timeZone, @Nullable Locale locale) {
        return xmlObjectMapper(null, null, null, timeZone, locale);
    }

    /**
     * Build a fully configured XML object mapper.
     *
     * @param dateFormat The date pattern, may be {@code null}
     * @param timeFormat The time pattern, may be {@code null}
     * @param dateTimeFormat The date-time pattern, may be {@code null}
     * @param timeZone The time zone, may be {@code null}
     * @param locale The locale, may be {@code null}
     *
     * @return The configured XML object mapper
     */
    @Nonnull
    public static ObjectMapper xmlObjectMapper(@Nullable String dateFormat, @Nullable String timeFormat, @Nullable String dateTimeFormat, @Nullable TimeZone timeZone, @Nullable Locale locale) {
        return xmlObjectMapperBuilder(dateFormat, timeFormat, dateTimeFormat, timeZone, locale).build();
    }

    /**
     * Build an XML mapper builder with the toolkit configuration applied.
     *
     * @return The configured XML mapper builder
     */
    @Nonnull
    public static XmlMapper.Builder xmlObjectMapperBuilder() {
        return xmlObjectMapperBuilder(null, null, null, null, null);
    }

    /**
     * Build an XML mapper builder with the toolkit configuration applied.
     *
     * @param timeZone The time zone, may be {@code null}
     * @param locale The locale, may be {@code null}
     *
     * @return The configured XML mapper builder
     */
    @Nonnull
    public static XmlMapper.Builder xmlObjectMapperBuilder(@Nullable TimeZone timeZone, @Nullable Locale locale) {
        return xmlObjectMapperBuilder(null, null, null, timeZone, locale);
    }

    /**
     * Build an XML mapper builder with the toolkit configuration applied.
     *
     * @param dateFormat The date pattern, may be {@code null}
     * @param timeFormat The time pattern, may be {@code null}
     * @param dateTimeFormat The date-time pattern, may be {@code null}
     * @param timeZone The time zone, may be {@code null}
     * @param locale The locale, may be {@code null}
     *
     * @return The configured XML mapper builder
     */
    @Nonnull
    public static XmlMapper.Builder xmlObjectMapperBuilder(@Nullable String dateFormat, @Nullable String timeFormat, @Nullable String dateTimeFormat, @Nullable TimeZone timeZone, @Nullable Locale locale) {
        XmlMapper.Builder builder = XmlMapper.builder();
        JacksonJsonWraps.configureMapper(builder, dateFormat, timeFormat, dateTimeFormat, timeZone, locale);
        return builder;
    }
}
