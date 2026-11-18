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

package com.jiovan.plexus.spring.toolkit.support;


import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.Environment;


/**
 * {@link org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider} for simple scanning components
 *
 * @author David Hsing
 *
 * @see org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
 */
@SuppressWarnings("unused")
public class ClassPathScanningSimpleComponentProvider extends ClassPathScanningCandidateComponentProvider {
    /**
     * Create a ClassPathScanningSimpleComponentProvider with the given default-filter setting.
     *
     * @param useDefaultFilters Whether to register the default filters
     */
    public ClassPathScanningSimpleComponentProvider(boolean useDefaultFilters) {
        super(useDefaultFilters);
    }

    /**
     * Create a ClassPathScanningSimpleComponentProvider with the given default-filter setting.
     *
     * @param useDefaultFilters Whether to register the default filters
     * @param environment The Spring environment
     */
    public ClassPathScanningSimpleComponentProvider(boolean useDefaultFilters, @Nonnull Environment environment) {
        super(useDefaultFilters, environment);
    }

    /**
     * Returns whether the given bean definition is a candidate component.
     *
     * @param definition The bean definition to inspect
     *
     * @return Whether the definition is a candidate component
     */
    @Override
    protected boolean isCandidateComponent(@Nonnull AnnotatedBeanDefinition definition) {
        return true;
    }
}
