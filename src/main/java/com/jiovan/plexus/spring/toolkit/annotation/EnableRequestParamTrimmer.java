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

package com.jiovan.plexus.spring.toolkit.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.jiovan.plexus.spring.toolkit.registrar.RequestParamTrimmerRegistrar;


/**
 * Annotation that enables a {@link com.jiovan.plexus.spring.toolkit.advice.RequestParamTrimmerAdvice}
 *
 * @author David Hsing
 *
 * @see com.jiovan.plexus.spring.toolkit.advice.RequestParamTrimmerAdvice
 * @see com.jiovan.plexus.spring.toolkit.registrar.RequestParamTrimmerRegistrar
 * @see org.springframework.beans.propertyeditors.StringTrimmerEditor
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication
@Import(value = RequestParamTrimmerRegistrar.class)
@SuppressWarnings("unused")
public @interface EnableRequestParamTrimmer {
    /**
     * Returns whether a trimmed blank string should be returned as {@code null} instead of an empty string
     *
     * @return whether a trimmed blank string should be returned as {@code null}
     */
    boolean emptyAsNull() default false;
}
