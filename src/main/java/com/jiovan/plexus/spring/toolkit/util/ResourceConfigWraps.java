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


import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.config.annotation.ResourceChainRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolver;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import com.jiovan.plexus.common.toolkit.util.BooleanUtilsWraps;
import com.jiovan.plexus.common.toolkit.util.DurationUtilsWraps;
import com.jiovan.plexus.common.toolkit.util.ObjectUtilsWraps;


/**
 * Utilities for configuring {@link org.springframework.core.io.Resource}
 *
 * @author David Hsing
 *
 * @see org.springframework.core.io.Resource
 * @see org.springframework.boot.webmvc.autoconfigure.WebMvcAutoConfiguration.ResourceChainResourceHandlerRegistrationCustomizer
 *
 * @see "http://www.mscharhag.com/spring/resource-versioning-with-spring-mvc"
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue", "JavadocReference"})
public abstract class ResourceConfigWraps {
    /**
     * @see org.springframework.boot.webmvc.autoconfigure.WebMvcAutoConfiguration.ResourceChainResourceHandlerRegistrationCustomizer#configureResourceChain
     */
    public static void configResourceChain(@Nullable WebProperties.Resources.Chain chain, @Nullable ResourceChainRegistration registration) {
        if (ObjectUtils.anyNull(chain, registration)) {
            return;
        }
        BooleanUtilsWraps.ifTrue(chain.isCompressed(), () -> registration.addResolver(new EncodedResourceResolver()));
        WebProperties.Resources.Chain.Strategy strategy = chain.getStrategy();
        if (strategy.getContent().isEnabled() || strategy.getFixed().isEnabled()) {
            ObjectUtilsWraps.ifNotNull(getVersionResourceResolver(strategy), registration::addResolver);
        }
    }

    public static void configResourceCache(@Nullable WebProperties.Resources resources, @Nullable ResourceHandlerRegistration registration) {
        if (ObjectUtils.anyNull(resources, registration)) {
            return;
        }
        WebProperties.Resources.Chain chain = resources.getChain();
        WebProperties.Resources.Cache cache = resources.getCache();
        if (chain.isCache() && DurationUtilsWraps.isPositive(cache.getPeriod())) {
            registration.setCachePeriod(Math.toIntExact(cache.getPeriod().getSeconds()));
        }
    }

    /**
     * @see org.springframework.web.servlet.resource.ContentVersionStrategy
     * @see org.springframework.web.servlet.resource.FixedVersionStrategy
     */
    public static ResourceResolver getVersionResourceResolver(@Nullable WebProperties.Resources.Chain.Strategy strategy) {
        if (strategy == null) {
            return null;
        }
        VersionResourceResolver resolver = new VersionResourceResolver();
        if (strategy.getFixed().isEnabled()) {
            String version = strategy.getFixed().getVersion();
            String[] paths = strategy.getFixed().getPaths();
            if (StringUtils.isNotBlank(version) && ArrayUtils.isNotEmpty(paths)) {
                resolver.addFixedVersionStrategy(version, paths);
            }
        }
        if (strategy.getContent().isEnabled() && ArrayUtils.isNotEmpty(strategy.getContent().getPaths())) {
            resolver.addContentVersionStrategy(strategy.getContent().getPaths());
        }
        return CollectionUtils.isEmpty(resolver.getStrategyMap()) ? null : resolver;
    }
}
