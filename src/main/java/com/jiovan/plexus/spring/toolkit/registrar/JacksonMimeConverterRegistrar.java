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
import jakarta.annotation.Nonnull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import com.jiovan.plexus.spring.toolkit.annotation.EnableJacksonMimeConverter;
import com.jiovan.plexus.spring.toolkit.jackson.processor.JacksonJsonMediaTypeProcessor;
import com.jiovan.plexus.spring.toolkit.jackson.processor.JacksonXmlMediaTypeProcessor;


/**
 * Registrar of additional mime types for jackson message converter
 *
 * @author David Hsing
 *
 * @see org.springframework.context.annotation.ImportAware
 * @see org.springframework.context.annotation.ImportBeanDefinitionRegistrar
 * @see org.springframework.boot.http.converter.autoconfigure.JacksonHttpMessageConvertersConfiguration
 *
 * @see "https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto-customize-the-jackson-objectmapper"
 * @see "https://www.baeldung.com/spring-boot-jsoncomponent"
 */
@SuppressWarnings({"JavadocReference"})
public class JacksonMimeConverterRegistrar implements ImportAware {
    private final Class<? extends Annotation> annotation = EnableJacksonMimeConverter.class;
    private AnnotationAttributes attributes;

    @Override
    public void setImportMetadata(@Nonnull AnnotationMetadata metadata) {
        attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotation.getName()));
        if (attributes == null) {
            throw new IllegalArgumentException(String.format("@%s is not present on importing class: %s", annotation.getSimpleName(), metadata.getClassName()));    // $NON-NLS-1$
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public JacksonJsonMediaTypeProcessor jacksonJsonMediaTypeProcessor() {
        return !attributes.getBoolean("json") ? null : new JacksonJsonMediaTypeProcessor();    // $NON-NLS-1$
    }

    @Bean
    @ConditionalOnMissingBean
    public JacksonXmlMediaTypeProcessor jacksonXmlMediaTypeProcessor() {
        return !attributes.getBoolean("xml") ? null : new JacksonXmlMediaTypeProcessor();    // $NON-NLS-1$
    }
}
