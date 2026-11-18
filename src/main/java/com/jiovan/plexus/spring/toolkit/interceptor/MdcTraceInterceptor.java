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

package com.jiovan.plexus.spring.toolkit.interceptor;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.jiovan.plexus.common.toolkit.constant.HttpHeaderConst;
import com.jiovan.plexus.common.toolkit.constant.StringVariantConst;
import com.jiovan.plexus.common.toolkit.identity.JdkUuidGenerator;
import com.jiovan.plexus.spring.toolkit.util.HttpHeaderWraps;


/**
 * {@link org.springframework.web.servlet.HandlerInterceptor} for tracing Mapped Diagnostic Context
 * <p>
 * Usage: include <code>%X{traceId}</code> as pattern in your logging configuration
 *
 * @author David Hsing
 */
public class MdcTraceInterceptor implements HandlerInterceptor {
    /**
     * Extract the trace ID from the request header or generate a new one, then store it in request attributes and MDC.
     *
     * @param request The current servlet request
     * @param response The current servlet response
     * @param handler The selected handler (controller, global handler, etc.)
     *
     * @return {@code true} to proceed with the interceptor chain
     */
    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        String traceId = HttpHeaderWraps.getHeader(request, HttpHeaderConst.X_TRACE_ID, JdkUuidGenerator.getRandomId());
        request.setAttribute(StringVariantConst.TRACE_ID, traceId);
        MDC.put(StringVariantConst.TRACE_ID, traceId);
        return true;
    }

    /**
     * No-op hook invoked after the controller method executes but before rendering the view.
     *
     * @param request The current servlet request
     * @param response The current servlet response
     * @param handler The selected handler
     * @param modelAndView The {@link ModelAndView} returned by the handler
     */
    @Override
    public void postHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler, @Nullable ModelAndView modelAndView) {
    }

    /**
     * Remove the trace ID from request attributes and MDC once the request is fully complete.
     *
     * @param request The current servlet request
     * @param response The current servlet response
     * @param handler The selected handler
     * @param cause Exception thrown during handler execution, may be {@code null}
     */
    @Override
    public void afterCompletion(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler, @Nullable Exception cause) {
        request.removeAttribute(StringVariantConst.TRACE_ID);
        MDC.remove(StringVariantConst.TRACE_ID);
    }
}
