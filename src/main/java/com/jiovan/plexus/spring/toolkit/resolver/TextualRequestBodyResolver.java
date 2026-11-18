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

package com.jiovan.plexus.spring.toolkit.resolver;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.ContentCachingRequestWrapper;
import com.jiovan.plexus.spring.toolkit.annotation.TextualRequestBody;
import com.jiovan.plexus.spring.toolkit.util.HttpHeaderWraps;
import com.jiovan.plexus.spring.toolkit.util.WebUtilsWraps;


/**
 * {@link org.springframework.web.method.support.HandlerMethodArgumentResolver} for resolving {@link com.jiovan.plexus.spring.toolkit.annotation.TextualRequestBody}
 *
 * @author David Hsing
 *
 * @see "https://sadique.io/blog/2016/01/30/using-custom-arguments-in-spring-mvc-controllers/"
 * @see "https://github.com/jkschneider/spring-mvc-requestbodypart"
 * @see "https://github.com/chujianyun/Spring-MultiRequestBody"
 */
@SuppressWarnings("unused")
public class TextualRequestBodyResolver implements HandlerMethodArgumentResolver {
    public static final String REQUEST_ATTRIBUTE = "TEXTUAL_REQUEST_BODY";    // $NON-NLS-1$

    /**
     * Check whether this resolver supports the given method parameter annotated with {@link TextualRequestBody}.
     *
     * @param parameter The method parameter to check
     *
     * @return {@code true} if the parameter has the {@link TextualRequestBody} annotation
     */
    @Override
    public boolean supportsParameter(@Nonnull MethodParameter parameter) {
        return parameter.hasParameterAnnotation(TextualRequestBody.class);
    }

    /**
     * Extract the raw body content from the HTTP request and cache it for subsequent retrievals.
     *
     * @param parameter The method parameter being resolved
     * @param container The current model and view container (unused)
     * @param request The native web request providing access to the servlet request
     * @param factory The data binder factory used for type conversion, may be {@code null}
     *
     * @return The raw request body as a string, or {@code null} if multipart content is detected or no annotation present
     */
    @Override
    public Object resolveArgument(@Nonnull MethodParameter parameter, ModelAndViewContainer container, @Nonnull NativeWebRequest request, @Nullable WebDataBinderFactory factory) {
        Object attribute = request.getAttribute(REQUEST_ATTRIBUTE, NativeWebRequest.SCOPE_REQUEST);
        if (attribute instanceof String) {
            return attribute;
        }
        TextualRequestBody annotation = parameter.getParameterAnnotation(TextualRequestBody.class);
        HttpServletRequest servletRequest = request.getNativeRequest(HttpServletRequest.class);
        if (ObjectUtils.anyNull(annotation, servletRequest) || (!annotation.multipart() && HttpHeaderWraps.isContentTypeMultipart(servletRequest))) {
            return null;
        }
        ContentCachingRequestWrapper wrapper;
        if (servletRequest instanceof ContentCachingRequestWrapper alias) {
            wrapper = alias;
        } else {
            wrapper = (annotation.limitation() >= 0) ? new ContentCachingRequestWrapper(servletRequest, annotation.limitation()) : new ContentCachingRequestWrapper(servletRequest, -1);
        }
        String content = WebUtilsWraps.getContentAsStringQuietly(wrapper);
        if (StringUtils.isNotEmpty(content)) {
            request.setAttribute(REQUEST_ATTRIBUTE, content, NativeWebRequest.SCOPE_REQUEST);
        }
        return content;
    }
}
