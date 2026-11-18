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
import java.util.List;
import jakarta.annotation.Nonnull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.CollectionUtils;
import com.jiovan.plexus.common.toolkit.util.ArrayUtilsWraps;
import com.jiovan.plexus.spring.toolkit.annotation.EnableContentCachingRequest;
import com.jiovan.plexus.spring.toolkit.filter.ContentCachingRequestFilter;


/**
 * Registrar of a {@link com.jiovan.plexus.spring.toolkit.filter.ContentCachingRequestFilter}
 *
 * @author David Hsing
 */
public class ContentCachingRequestRegistrar implements ImportAware {
    /**
     * The annotation type that enables this registrar.
     */
    private final Class<? extends Annotation> annotation = EnableContentCachingRequest.class;
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
     * Create and configure the content caching request filter registration bean.
     *
     * @return The configured filter registration bean.
     */
    @Bean
    @ConditionalOnMissingBean(value = ContentCachingRequestFilter.class, parameterizedContainer = FilterRegistrationBean.class)
    public FilterRegistrationBean<ContentCachingRequestFilter> contentCachingRequestFilterRegistration() {
        ContentCachingRequestFilter filter = new ContentCachingRequestFilter();
        filter.setCacheMultipart(attributes.getBoolean("cacheMultipart"));    // $NON-NLS-1$
        filter.setCacheLimit(attributes.getNumber("cacheLimit"));    // $NON-NLS-1$
        filter.addExcludedPath(attributes.getStringArray("urlExcludes"));    // $NON-NLS-1$
        FilterRegistrationBean<ContentCachingRequestFilter> result = new FilterRegistrationBean<>(filter);
        List<String> urlPatterns = ArrayUtilsWraps.asList(attributes.getStringArray("urlPatterns"));    // $NON-NLS-1$
        if (!CollectionUtils.isEmpty(urlPatterns)) {
            result.setUrlPatterns(urlPatterns);
        }
        result.setOrder(attributes.getNumber("filterOrder"));    // $NON-NLS-1$
        return result;
    }
}
