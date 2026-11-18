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

package com.jiovan.plexus.spring.toolkit.processor;


import java.util.Locale;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.Ordered;
import com.jiovan.plexus.spring.toolkit.context.MessageSourceAccessorAware;
import lombok.Getter;
import lombok.Setter;


/**
 * {@link org.springframework.beans.factory.config.BeanPostProcessor} for {@link com.jiovan.plexus.spring.toolkit.context.MessageSourceAccessorAware}
 *
 * @author David Hsing
 *
 * @see "org.springframework.context.support.ApplicationContextAwareProcessor"
 */
@SuppressWarnings("unused")
public class MessageSourceAccessorProcessor implements BeanPostProcessor, Ordered {
    /**
     * The Spring application context in use.
     */
    private final ConfigurableApplicationContext applicationContext;

    /**
     * Pre-created accessor for resolving localized messages from the message source.
     */
    private final MessageSourceAccessor messageAccessor;

    /**
     * The order value for this processor.
     */
    @Getter
    @Setter
    private int order = 0;

    /**
     * Create a processor using the application's default locale.
     *
     * @param context The Spring application context
     */
    public MessageSourceAccessorProcessor(@Nonnull ConfigurableApplicationContext context) {
        applicationContext = context;
        messageAccessor = new MessageSourceAccessor(applicationContext);
    }

    /**
     * Create a processor with an explicit default locale override.
     *
     * @param context The Spring application context
     * @param defaultLocale The locale to use when no match is found in the request, may be {@code null}
     */
    @SuppressWarnings("DataFlowIssue")
    public MessageSourceAccessorProcessor(@Nonnull ConfigurableApplicationContext context, @Nullable Locale defaultLocale) {
        applicationContext = context;
        messageAccessor = new MessageSourceAccessor(applicationContext, defaultLocale);
    }

    /**
     * Invoke the {@link MessageSourceAccessorAware} interface on the given bean by injecting the configured message accessor.
     *
     * @param bean The Spring bean being processed
     */
    private void invokeAwareInterfaces(@Nonnull Object bean) {
        if (bean instanceof MessageSourceAccessorAware alias) {
            alias.setMessageSourceAccessor(messageAccessor);
        }
    }
}
