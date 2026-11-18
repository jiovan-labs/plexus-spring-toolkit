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
import com.jiovan.plexus.spring.toolkit.annotation.EnableHashCodeKeyGenerator;
import com.jiovan.plexus.spring.toolkit.cache.HashCodeKeyGenerator;


/**
 * Registrar of hash code key generator for spring cache
 *
 * @author David Hsing
 */
public class HashCodeKeyGeneratorRegistrar implements ImportAware {
    /**
     * The bean name of the hash-code based key generator.
     */
    public static final String KEY_GENERATOR = "hashCodeKeyGenerator";    // $NON-NLS-1$
    /**
     * The annotation type that enables this registrar.
     */
    private final Class<? extends Annotation> annotation = EnableHashCodeKeyGenerator.class;
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
     * Create the hash-code based cache key generator from the annotation attributes.
     *
     * @return The configured key generator bean.
     */
    @Bean(name = KEY_GENERATOR)
    @ConditionalOnMissingBean(name = KEY_GENERATOR)
    @SuppressWarnings("DuplicatedCode")
    public HashCodeKeyGenerator hashCodeKeyGenerator() {
        HashCodeKeyGenerator result = new HashCodeKeyGenerator();
        result.setPrefix(attributes.getString("prefix"));    // $NON-NLS-1$
        result.setSuffix(attributes.getString("suffix"));    // $NON-NLS-1$
        result.setClazzName(attributes.getBoolean("clazzName"));    // $NON-NLS-1$
        result.setShortClazzName(attributes.getBoolean("shortClazzName"));    // $NON-NLS-1$
        result.setMethodName(attributes.getBoolean("methodName"));    // $NON-NLS-1$
        result.setMethodHash(attributes.getBoolean("methodHash"));    // $NON-NLS-1$
        result.setParamParentheses(attributes.getBoolean("paramParentheses"));    // $NON-NLS-1$
        return result;
    }
}
