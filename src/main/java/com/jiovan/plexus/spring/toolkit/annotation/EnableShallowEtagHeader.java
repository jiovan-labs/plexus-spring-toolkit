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
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.jiovan.plexus.spring.toolkit.constant.AntPathConst;
import com.jiovan.plexus.spring.toolkit.registrar.ShallowEtagHeaderRegistrar;


/**
 * Annotation that enables a {@link org.springframework.web.filter.ShallowEtagHeaderFilter}
 *
 * @author David Hsing
 *
 * @see org.springframework.web.filter.ShallowEtagHeaderFilter
 * @see com.jiovan.plexus.spring.toolkit.registrar.ShallowEtagHeaderRegistrar
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Configuration(proxyBeanMethods = false)
@Import(value = ShallowEtagHeaderRegistrar.class)
@SuppressWarnings("unused")
public @interface EnableShallowEtagHeader {
    /**
     * Returns the url patterns of the filter
     *
     * @return the url patterns of the filter
     */
    String[] urlPatterns() default {AntPathConst.SLASH_STARS};

    /**
     * Returns the order of the filter
     *
     * @return the order of the filter
     */
    int filterOrder() default 0;

    /**
     * Returns whether enable writing weak Etag or not
     *
     * @return whether enable writing weak Etag or not
     */
    boolean writeWeakETag() default false;
}
