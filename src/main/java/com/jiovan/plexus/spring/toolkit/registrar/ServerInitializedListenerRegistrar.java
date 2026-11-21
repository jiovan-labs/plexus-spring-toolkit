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
import org.springframework.boot.web.server.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import com.jiovan.plexus.spring.toolkit.annotation.EnableServerInitializedListener;
import com.jiovan.plexus.spring.toolkit.event.listener.WebServerInitializedListener;


/**
 * Registrar of a {@link org.springframework.boot.web.server.context.WebServerInitializedEvent} listener
 *
 * @author David Hsing
 */
public class ServerInitializedListenerRegistrar implements ImportAware {
    /**
     * The annotation type that enables this registrar.
     */
    private final Class<? extends Annotation> annotation = EnableServerInitializedListener.class;

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
     * Create the web server initialized event listener with the configured log setting.
     *
     * @return The configured listener bean.
     */
    @Bean
    @ConditionalOnMissingBean(value = WebServerInitializedEvent.class, parameterizedContainer = WebServerInitializedListener.class)
    public WebServerInitializedListener<WebServerInitializedEvent> webServerInitializedListener() {
        WebServerInitializedListener<WebServerInitializedEvent> result = new WebServerInitializedListener<>();
        result.setLogMessage(attributes.getBoolean("logMessage"));    // $NON-NLS-1$
        return result;
    }
}
