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
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.support.MultipartFilter;
import com.jiovan.plexus.common.toolkit.util.ArrayUtilsWraps;
import com.jiovan.plexus.spring.toolkit.annotation.EnableMultipartFilter;


/**
 * Registrar of a {@link org.springframework.web.multipart.support.MultipartFilter}
 *
 * @author David Hsing
 *
 * @see "https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#csrf-multipartfilter"
 */
public class MultipartFilterRegistrar implements ImportAware {
    private final Class<? extends Annotation> annotation = EnableMultipartFilter.class;
    private AnnotationAttributes attributes;

    @Override
    public void setImportMetadata(@Nonnull AnnotationMetadata metadata) {
        attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(annotation.getName()));
        if (attributes == null) {
            throw new IllegalArgumentException(String.format("@%s is not present on importing class: %s", annotation.getSimpleName(), metadata.getClassName()));    // $NON-NLS-1$
        }
    }

    @Bean
    @ConditionalOnMissingBean(value = MultipartFilter.class, parameterizedContainer = FilterRegistrationBean.class)
    public FilterRegistrationBean<MultipartFilter> multipartFilterRegistration() {
        MultipartFilter filter = new MultipartFilter();
        String resolverName = attributes.getString("resolverName");    // $NON-NLS-1$
        if (StringUtils.isNotBlank(resolverName)) {
            filter.setMultipartResolverBeanName(resolverName);
        }
        FilterRegistrationBean<MultipartFilter> result = new FilterRegistrationBean<>(filter);
        List<String> urlPatterns = ArrayUtilsWraps.asList(attributes.getStringArray("urlPatterns"));    // $NON-NLS-1$
        if (!CollectionUtils.isEmpty(urlPatterns)) {
            result.setUrlPatterns(urlPatterns);
        }
        result.setOrder(attributes.getNumber("filterOrder"));    // $NON-NLS-1$
        return result;
    }
}
