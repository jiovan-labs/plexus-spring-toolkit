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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.boot.jackson.autoconfigure.JacksonProperties;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.util.ClassUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiovan.plexus.spring.toolkit.enumeration.JacksonTemporalType;
import com.jiovan.plexus.spring.toolkit.jackson.module.JdkDateModule;
import com.jiovan.plexus.spring.toolkit.jackson.module.JodaTimeModule;
import com.jiovan.plexus.spring.toolkit.jackson.module.Jsr310Module;
import tools.jackson.databind.DeserializationConfig;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationConfig;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.cfg.MapperBuilder;
import tools.jackson.databind.json.JsonMapper;


/**
 * Utilities for {@link tools.jackson.databind.ObjectMapper}
 *
 * @author David Hsing
 *
 * @see tools.jackson.databind.ObjectMapper
 * @see org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration
 * @see org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer
 * @see tools.jackson.databind.json.JsonMapper
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class JacksonJsonWraps {
    /**
     * Create a mapper customizer that registers temporal modules and a default time zone.
     *
     * @param dateFormat The date pattern, may be {@code null}
     * @param timeFormat The time pattern, may be {@code null}
     * @param dateTimeFormat The date-time pattern, may be {@code null}
     * @param timeZone The time zone, may be {@code null}
     *
     * @return The configured mapper builder customizer
     */
    @Nonnull
    public static JsonMapperBuilderCustomizer dateTimeCustomizer(@Nullable String dateFormat, @Nullable String timeFormat, @Nullable String dateTimeFormat, @Nullable TimeZone timeZone) {
        JacksonModule[] modules = temporalModules(dateFormat, timeFormat, dateTimeFormat);
        return builder -> {
            builder.addModules(modules);
            builder.defaultTimeZone(ObjectUtils.getIfNull(timeZone, TimeZone.getDefault()));
        };
    }

    /**
     * Build a fully configured JSON object mapper.
     *
     * @return The configured JSON object mapper
     */
    @Nonnull
    public static ObjectMapper jsonObjectMapper() {
        return jsonObjectMapper(null, null, null, null, null);
    }

    /**
     * Build a fully configured JSON object mapper.
     *
     * @param timeZone The time zone, may be {@code null}
     * @param locale The locale, may be {@code null}
     *
     * @return The configured JSON object mapper
     */
    @Nonnull
    public static ObjectMapper jsonObjectMapper(@Nullable TimeZone timeZone, @Nullable Locale locale) {
        return jsonObjectMapper(null, null, null, timeZone, locale);
    }

    /**
     * Build a fully configured JSON object mapper.
     *
     * @param dateFormat The date pattern, may be {@code null}
     * @param timeFormat The time pattern, may be {@code null}
     * @param dateTimeFormat The date-time pattern, may be {@code null}
     * @param timeZone The time zone, may be {@code null}
     * @param locale The locale, may be {@code null}
     *
     * @return The configured JSON object mapper
     */
    @Nonnull
    public static ObjectMapper jsonObjectMapper(@Nullable String dateFormat, @Nullable String timeFormat, @Nullable String dateTimeFormat, @Nullable TimeZone timeZone, @Nullable Locale locale) {
        return jsonObjectMapperBuilder(dateFormat, timeFormat, dateTimeFormat, timeZone, locale).build();
    }

    /**
     * Build a fully configured JSON object mapper.
     *
     * @param properties The Jackson properties, may be {@code null}
     *
     * @return The configured JSON object mapper
     */
    @Nonnull
    public static ObjectMapper jsonObjectMapper(@Nullable JacksonProperties properties) {
        return (properties == null) ? jsonObjectMapper() : jsonObjectMapper(properties.getDateFormat(), null, null, properties.getTimeZone(), properties.getLocale());
    }

    /**
     * Build a JSON mapper builder with the toolkit configuration applied.
     *
     * @return The configured JSON mapper builder
     */
    @Nonnull
    public static JsonMapper.Builder jsonObjectMapperBuilder() {
        return jsonObjectMapperBuilder(null, null, null, null, null);
    }

    /**
     * Build a JSON mapper builder with the toolkit configuration applied.
     *
     * @param timeZone The time zone, may be {@code null}
     * @param locale The locale, may be {@code null}
     *
     * @return The configured JSON mapper builder
     */
    @Nonnull
    public static JsonMapper.Builder jsonObjectMapperBuilder(@Nullable TimeZone timeZone, @Nullable Locale locale) {
        return jsonObjectMapperBuilder(null, null, null, timeZone, locale);
    }

    /**
     * Build a JSON mapper builder with the toolkit configuration applied.
     *
     * @param dateFormat The date pattern, may be {@code null}
     * @param timeFormat The time pattern, may be {@code null}
     * @param dateTimeFormat The date-time pattern, may be {@code null}
     * @param timeZone The time zone, may be {@code null}
     * @param locale The locale, may be {@code null}
     *
     * @return The configured JSON mapper builder
     */
    @Nonnull
    public static JsonMapper.Builder jsonObjectMapperBuilder(@Nullable String dateFormat, @Nullable String timeFormat, @Nullable String dateTimeFormat, @Nullable TimeZone timeZone, @Nullable Locale locale) {
        JsonMapper.Builder builder = JsonMapper.builder();
        configureMapper(builder, dateFormat, timeFormat, dateTimeFormat, timeZone, locale);
        return builder;
    }

    /**
     * Return a {@link tools.jackson.databind.json.JsonMapper.Builder} instance which is configured with the same features as the given {@code mapper}
     *
     * @param mapper The mapper to copy the features from
     *
     * @return a {@link tools.jackson.databind.json.JsonMapper.Builder} instance which is configured with the same features as the given {@code mapper}
     *
     * @see tools.jackson.databind.MapperFeature
     */
    @Nonnull
    public static JsonMapper.Builder jsonMapperBuilder(@Nullable ObjectMapper mapper) {
        JsonMapper.Builder builder = JsonMapper.builder().findAndAddModules();
        if (mapper == null) {
            return builder;
        }
        SerializationConfig seConfig = mapper.serializationConfig();
        DeserializationConfig deConfig = mapper.deserializationConfig();
        Arrays.stream(SerializationFeature.values()).forEach(feature -> builder.configure(feature, seConfig.isEnabled(feature)));
        Arrays.stream(DeserializationFeature.values()).forEach(feature -> builder.configure(feature, deConfig.isEnabled(feature)));
        Arrays.stream(MapperFeature.values()).forEach(feature -> builder.configure(feature, mapper.isEnabled(feature)));
        Arrays.stream(DateTimeFeature.values()).forEach(feature -> builder.configure(feature, mapper.isEnabled(feature)));
        builder.propertyNamingStrategy(seConfig.getPropertyNamingStrategy());
        builder.changeDefaultPropertyInclusion(inclusion -> seConfig.getDefaultPropertyInclusion());
        builder.defaultDateFormat(seConfig.getDateFormat());
        builder.defaultLocale(seConfig.getLocale());
        builder.defaultTimeZone(seConfig.getTimeZone());
        return builder;
    }

    /**
     * Configure the mapper builder with serialization, inclusion, visibility, locale, time zone settings and temporal modules.
     *
     * @param builder The mapper builder to configure
     * @param dateFormat The date pattern to use, may be {@code null}
     * @param timeFormat The time pattern to use, may be {@code null}
     * @param dateTimeFormat The date-time pattern to use, may be {@code null}
     * @param timeZone The time zone to apply, may be {@code null} for the JVM default
     * @param locale The locale to apply, may be {@code null} for the JVM default
     */
    public static <M extends ObjectMapper, B extends MapperBuilder<M, B>> void configureMapper(@Nonnull B builder, @Nullable String dateFormat, @Nullable String timeFormat, @Nullable String dateTimeFormat, @Nullable TimeZone timeZone, @Nullable Locale locale) {
        builder.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        builder.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        builder.configure(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        builder.configure(DateTimeFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        builder.changeDefaultPropertyInclusion(inclusion -> JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.NON_NULL));
        builder.changeDefaultVisibility(visibility -> visibility.withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        builder.defaultLocale(ObjectUtils.getIfNull(locale, Locale.getDefault()));
        builder.defaultTimeZone(ObjectUtils.getIfNull(timeZone, TimeZone.getDefault()));
        builder.addModules(temporalModules(dateFormat, timeFormat, dateTimeFormat));
    }

    /**
     * Instantiate the optional Jackson Joda-Time module by reflection so that {@code jackson-datatype-joda} is never resolved when absent.
     *
     * @return The instantiated module, or {@code null} when it is unavailable
     */
    @Nullable
    public static JacksonModule newJodaModule() {
        try {
            Class<?> type = ClassUtils.forName(JacksonTemporalType.JODA.getValue(), null);
            Object instance = type.getDeclaredConstructor().newInstance();
            return (instance instanceof JacksonModule alias) ? alias : null;
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Build the temporal modules for util-date, Joda-Time and {@code java.time} types, omitting modules whose dependencies are unavailable.
     *
     * @param dateFormat The date pattern to use, may be {@code null}
     * @param timeFormat The time pattern to use, may be {@code null}
     * @param dateTimeFormat The date-time pattern to use, may be {@code null}
     *
     * @return The available temporal modules
     */
    @Nonnull
    public static JacksonModule[] temporalModules(@Nullable String dateFormat, @Nullable String timeFormat, @Nullable String dateTimeFormat) {
        List<JacksonModule> modules = new ArrayList<>();
        modules.add(JdkDateModule.mapperModule(dateFormat));
        if (ClassUtils.isPresent(JacksonTemporalType.JODA.getValue(), null)) {
            modules.add(newJodaModule());
            modules.add(JodaTimeModule.mapperModule(dateFormat, timeFormat, dateTimeFormat));
        }
        if (ClassUtils.isPresent(JacksonTemporalType.JSR310.getValue(), null)) {
            modules.add(Jsr310Module.mapperModule(dateFormat, timeFormat, dateTimeFormat));
        }
        return modules.stream().filter(Objects::nonNull).toArray(JacksonModule[]::new);
    }
}
