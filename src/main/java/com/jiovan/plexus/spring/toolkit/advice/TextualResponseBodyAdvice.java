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

package com.jiovan.plexus.spring.toolkit.advice;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import com.jiovan.plexus.common.toolkit.constant.LogMessageConst;
import com.jiovan.plexus.spring.toolkit.annotation.TextualResponseBody;
import lombok.extern.slf4j.Slf4j;


/**
 * {@link org.springframework.web.bind.annotation.ControllerAdvice} for textual response body
 *
 * @author David Hsing
 *
 * @see "https://blog.csdn.net/jiangzeyin_/article/details/81094258"
 */
@Slf4j
@SuppressWarnings("unused")
public class TextualResponseBodyAdvice implements ResponseBodyAdvice<String> {
    /**
     * Callback invoked before a {@link String} response body is written.
     *
     * <p>Applies the content type declared by {@link TextualResponseBody} to the response
     * when a non-empty body is produced for an annotated handler.
     *
     * @param body The response body, may be {@code null}
     * @param parameter The method parameter that produced the body
     * @param mediaType The selected media type
     * @param converter The message converter used to write the body
     * @param request The current request
     * @param response The current response
     *
     * @return The response body to write
     */
    @Override
    public String beforeBodyWrite(@Nullable String body, @Nonnull MethodParameter parameter, @Nonnull MediaType mediaType, @Nonnull Class<? extends HttpMessageConverter<?>> converter, @Nonnull ServerHttpRequest request, @Nonnull ServerHttpResponse response) {
        if (StringUtils.isEmpty(body) || !supports(parameter, converter)) {
            return body;
        }
        TextualResponseBody annotation = parameter.getMethodAnnotation(TextualResponseBody.class);
        if (annotation == null) {
            annotation = AnnotationUtils.findAnnotation(parameter.getDeclaringClass(), TextualResponseBody.class);
        }
        if (annotation != null && StringUtils.isNotBlank(annotation.contentType())) {
            try {
                MediaType contentType = MediaType.parseMediaType(annotation.contentType());
                response.getHeaders().setContentType(contentType);
            } catch (Exception ex) {
                log.error(LogMessageConst.EXCEPTION_OCCURRED, ex);
            }
        }
        return body;
    }

    /**
     * Return whether the given method or its declaring class is annotated with
     * {@link TextualResponseBody}.
     *
     * @param parameter The method parameter to inspect
     * @param clazz The message converter type
     *
     * @return {@code true} if the annotation is present, {@code false} otherwise
     */
    @Override
    public boolean supports(@Nonnull MethodParameter parameter, @Nonnull Class<? extends HttpMessageConverter<?>> clazz) {
        return parameter.hasMethodAnnotation(TextualResponseBody.class) || AnnotatedElementUtils.hasAnnotation(parameter.getDeclaringClass(), TextualResponseBody.class);
    }
}
