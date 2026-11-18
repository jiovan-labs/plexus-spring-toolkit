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
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.jiovan.plexus.spring.toolkit.support.HeaderCapableRequestWrapper;


/**
 * {@link jakarta.servlet.Filter} with header modifiable
 *
 * @author David Hsing
 *
 * @see com.jiovan.plexus.spring.toolkit.support.HeaderCapableRequestWrapper
 * @see org.springframework.web.filter.OncePerRequestFilter
 */
@SuppressWarnings("unused")
public class HeaderCapableRequestFilter extends AbstractExcludableRequestFilter {
    /**
     * Wrap the request with {@link HeaderCapableRequestWrapper} and continue the filter chain.
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
        HeaderCapableRequestWrapper wrapper = new HeaderCapableRequestWrapper(request);
        chain.doFilter(wrapper, response);
    }
}
