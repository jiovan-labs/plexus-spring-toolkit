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
import com.jiovan.plexus.spring.toolkit.annotation.EnableSecurityContextAuditor;
import com.jiovan.plexus.spring.toolkit.audit.SecurityContextAuditorAware;


/**
 * Registrar of a {@link com.jiovan.plexus.spring.toolkit.audit.SecurityContextAuditorAware}
 *
 * @author David Hsing
 */
public class SecurityContextAuditorRegistrar implements ImportAware {
    /**
     * The bean name of the security context auditor aware instance.
     */
    public static final String AUDITOR_AWARE = "securityContextAuditorAware";    // $NON-NLS-1$

    /**
     * The annotation type that enables this registrar.
     */
    private final Class<? extends Annotation> annotation = EnableSecurityContextAuditor.class;

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
     * Create the security-context based auditor aware bean with the configured default auditor.
     *
     * @return The configured auditor aware bean.
     */
    @Bean(name = AUDITOR_AWARE)
    @ConditionalOnMissingBean(name = AUDITOR_AWARE)
    public SecurityContextAuditorAware securityContextAuditorAware() {
        SecurityContextAuditorAware result = new SecurityContextAuditorAware();
        result.setDefaultAuditor(attributes.getString("defaultAuditor"));    // $NON-NLS-1$
        return result;
    }
}
