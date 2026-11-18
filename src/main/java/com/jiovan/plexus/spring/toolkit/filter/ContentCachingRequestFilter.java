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

package com.jiovan.plexus.spring.toolkit.filter;


import java.io.IOException;
import java.lang.reflect.Constructor;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import com.jiovan.plexus.common.toolkit.constant.AssertMessageConst;
import com.jiovan.plexus.common.toolkit.constant.RegexVariantConst;
import com.jiovan.plexus.common.toolkit.util.RegexUtilsWraps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * {@link jakarta.servlet.Filter} for caching request contents
 *
 * @author David Hsing
 *
 * @see org.springframework.web.util.ContentCachingRequestWrapper
 * @see org.springframework.web.filter.OncePerRequestFilter
 * @see org.springframework.boot.servlet.filter.OrderedRequestContextFilter
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuppressWarnings("unused")
public class ContentCachingRequestFilter extends AbstractExcludableRequestFilter {
    /**
     * Whether multipart requests should also be cached.
     */
    private boolean cacheMultipart = false;

    /**
     * The maximum number of bytes to cache, or a non-positive value for no limit.
     */
    private int cacheLimit = -1;

    /**
     * The request wrapper class used for caching.
     */
    private Class<? extends ContentCachingRequestWrapper> requestWrapperClass = ContentCachingRequestWrapper.class;

    /**
     * Wrap eligible requests with a content caching wrapper before continuing the filter chain.
     *
     * @param request The current request
     * @param response The current response
     * @param chain The filter chain
     *
     * @throws ServletException If the filter fails
     * @throws IOException If an I/O error occurs
     */
    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain) throws ServletException, IOException {
        if (cacheMultipart || RegexUtilsWraps.findAnyIgnoreCase(request.getContentType(), RegexVariantConst.APPLICATION_JSON, RegexVariantConst.APPLICATION_XML)) {
            ContentCachingRequestWrapper wrapper;
            if (requestWrapperClass == ContentCachingRequestWrapper.class) {
                wrapper = (cacheLimit > 0) ? new ContentCachingRequestWrapper(request, cacheLimit) : new ContentCachingRequestWrapper(request, -1);
            } else {
                Constructor<? extends ContentCachingRequestWrapper> constructor = ClassUtils.getConstructorIfAvailable(requestWrapperClass, HttpServletRequest.class, Integer.class);
                Assert.notNull(constructor, AssertMessageConst.NOT_NULL);
                wrapper = BeanUtils.instantiateClass(constructor, request, cacheLimit);
            }
            chain.doFilter(wrapper, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
