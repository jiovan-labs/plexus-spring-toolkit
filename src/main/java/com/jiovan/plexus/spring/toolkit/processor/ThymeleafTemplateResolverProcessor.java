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


import jakarta.annotation.Nonnull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.thymeleaf.autoconfigure.ThymeleafProperties;
import org.springframework.core.Ordered;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.UrlTemplateResolver;
import lombok.Getter;
import lombok.Setter;


/**
 * {@link org.springframework.beans.factory.config.BeanPostProcessor} for {@link org.thymeleaf.templateresolver.ITemplateResolver}
 *
 * @author David Hsing
 *
 * @see "https://ultraq.github.io/thymeleaf-layout-dialect/"
 */
@SuppressWarnings("unused")
public class ThymeleafTemplateResolverProcessor implements BeanPostProcessor, Ordered {
    /**
     * The resolved {@link TemplateEngine} bean instance.
     */
    private TemplateEngine engine;

    /**
     * The resolved Thymeleaf configuration properties.
     */
    private ThymeleafProperties properties;

    /**
     * Flag indicating whether the processor has already been applied to any bean.
     */
    private boolean beanProcessed = false;

    /**
     * The order value for this processor.
     */
    @Getter
    @Setter
    private int order = 0;

    /**
     * Post-process beans to resolve Thymeleaf dependencies and configure a default template resolver.
     *
     * @param bean The Spring bean being initialized
     * @param beanName The name of the bean
     *
     * @return The original bean instance
     *
     * @throws BeansException If bean initialization fails
     */
    @Override
    public Object postProcessAfterInitialization(@Nonnull Object bean, @Nonnull String beanName) throws BeansException {
        if (beanProcessed) {
            return bean;
        }
        if (bean instanceof TemplateEngine alias) {
            engine = alias;
        }
        if (bean instanceof ThymeleafProperties alias) {
            properties = alias;
        }
        if (engine != null && properties != null) {
            UrlTemplateResolver resolver = new UrlTemplateResolver();
            resolver.setTemplateMode(properties.getMode());
            resolver.setCharacterEncoding(properties.getEncoding().name());
            resolver.setCacheable(properties.isCache());
            engine.addTemplateResolver(resolver);
            beanProcessed = true;
        }
        return bean;
    }
}
