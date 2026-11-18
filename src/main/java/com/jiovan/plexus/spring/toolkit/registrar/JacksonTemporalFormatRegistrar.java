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

package com.jiovan.plexus.spring.toolkit.registrar;


import java.lang.annotation.Annotation;
import java.util.TimeZone;
import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.boot.jackson.autoconfigure.JacksonProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import com.jiovan.plexus.common.toolkit.constant.AssertMessageConst;
import com.jiovan.plexus.spring.toolkit.annotation.EnableJacksonTemporalFormat;
import com.jiovan.plexus.spring.toolkit.registrar.assistant.JacksonJodaTimeCustomizer;
import com.jiovan.plexus.spring.toolkit.registrar.assistant.JacksonJsr310Customizer;
import com.jiovan.plexus.spring.toolkit.registrar.assistant.JacksonJdkDateCustomizer;


/**
 * Registrar of date time formats for jackson
 *
 * @author David Hsing
 *
 * @see org.springframework.context.annotation.ImportAware
 * @see org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 */
public class JacksonTemporalFormatRegistrar implements ImportAware {
    /**
     * The annotation type that enables this registrar.
     */
    private final Class<? extends Annotation> annotation = EnableJacksonTemporalFormat.class;
    /**
     * The resolved attributes of the enabling annotation.
     */
    private AnnotationAttributes attributes;

    /**
     * Read the enabling annotation attributes from the importing class metadata.
     *
     * @param metadata The annotation metadata of the importing class
     *
     * @throws IllegalArgumentException If the enabling annotation is not present on the importing class
     */
    @Override
    public void setImportMetadata(@Nonnull AnnotationMetadata metadata) {
        attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotation.getName()));
        if (attributes == null) {
            throw new IllegalArgumentException(String.format("@%s is not present on importing class: %s", annotation.getSimpleName(), metadata.getClassName()));    // $NON-NLS-1$
        }
    }

    /**
     * {@link tools.jackson.databind.json.JsonMapper#builder()} for util-date
     * <p>
     * These customizer beans can be ordered (Spring own customizer has an order of 0)
     */
    @Bean
    @Order(value = 100)
    public JsonMapperBuilderCustomizer jacksonJdkDateMapperCustomizer(@Nonnull JacksonProperties properties) {
        Assert.notNull(attributes, AssertMessageConst.NOT_NULL);
        String dateFormat = StringUtils.defaultIfBlank(attributes.getString("dateFormat"), properties.getDateFormat());    // $NON-NLS-1$
        TimeZone timeZone = ObjectUtils.getIfNull(properties.getTimeZone(), TimeZone.getDefault());
        return JacksonJdkDateCustomizer.mapperCustomizer(dateFormat, timeZone);
    }

    /**
     * {@link tools.jackson.databind.json.JsonMapper#builder()} for jsr-310
     * <p>
     * These customizer beans can be ordered (Spring own customizer has an order of 0)
     *
     * @see tools.jackson.databind.ext.javatime.ser.LocalTimeSerializer
     * @see tools.jackson.databind.ext.javatime.deser.LocalDateDeserializer
     * @see tools.jackson.databind.ext.javatime.deser.LocalTimeDeserializer
     */
    @Bean
    @Order(value = 110)
    @ConditionalOnClass(name = "tools.jackson.databind.module.SimpleModule")
    @SuppressWarnings("DuplicatedCode")
    public JsonMapperBuilderCustomizer jacksonJsr310MapperCustomizer(@Nonnull JacksonProperties properties) {
        Assert.notNull(attributes, AssertMessageConst.NOT_NULL);
        String dateFormat = StringUtils.defaultIfBlank(attributes.getString("dateFormat"), properties.getDateFormat());    // $NON-NLS-1$
        String timeFormat = attributes.getString("timeFormat");    // $NON-NLS-1$
        String dateTimeFormat = attributes.getString("dateTimeFormat");    // $NON-NLS-1$
        TimeZone timeZone = ObjectUtils.getIfNull(properties.getTimeZone(), TimeZone.getDefault());
        return JacksonJsr310Customizer.mapperCustomizer(dateFormat, timeFormat, dateTimeFormat, timeZone);
    }

    /**
     * {@link tools.jackson.databind.json.JsonMapper#builder()} for joda-time
     * <p>
     * These customizer beans can be ordered (Spring own customizer has an order of 0)
     */
    @Bean
    @Order(value = 120)
    @ConditionalOnClass(name = "tools.jackson.datatype.joda.JodaModule")
    @SuppressWarnings("DuplicatedCode")
    public JsonMapperBuilderCustomizer jacksonJodaTimeMapperCustomizer(@Nonnull JacksonProperties properties) {
        Assert.notNull(attributes, AssertMessageConst.NOT_NULL);
        String dateFormat = StringUtils.defaultIfBlank(attributes.getString("dateFormat"), properties.getDateFormat());    // $NON-NLS-1$
        String timeFormat = attributes.getString("timeFormat");    // $NON-NLS-1$
        String dateTimeFormat = attributes.getString("dateTimeFormat");    // $NON-NLS-1$
        TimeZone timeZone = ObjectUtils.getIfNull(properties.getTimeZone(), TimeZone.getDefault());
        return JacksonJodaTimeCustomizer.mapperCustomizer(dateFormat, timeFormat, dateTimeFormat, timeZone);
    }
}
