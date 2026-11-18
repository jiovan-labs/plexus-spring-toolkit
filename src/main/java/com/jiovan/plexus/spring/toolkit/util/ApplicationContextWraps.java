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
import jakarta.servlet.ServletContext;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.server.context.WebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.jiovan.plexus.common.toolkit.util.ObjectUtilsWraps;
import com.jiovan.plexus.spring.toolkit.constant.SpringPropertyConst;


/**
 * Utilities for {@link org.springframework.context.ApplicationContext}
 *
 * @author David Hsing
 *
 * @see org.springframework.context.ApplicationContext
 * @see org.springframework.context.ApplicationContextAware
 * @see org.springframework.context.support.GenericApplicationContext
 * @see org.springframework.web.context.WebApplicationContext
 * @see org.springframework.web.context.support.WebApplicationObjectSupport
 * @see org.springframework.web.context.support.WebApplicationContextUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class ApplicationContextWraps {
    /**
     * Return the application name of the given context.
     *
     * @param context The application context, may be {@code null}
     *
     * @return The application name, or the default value
     */
    public static String getApplicationName(@Nullable ApplicationContext context) {
        return getApplicationName(context, null);
    }

    /**
     * Return the application name of the given context.
     *
     * @param context The application context, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The application name, or the default value
     */
    public static String getApplicationName(@Nullable ApplicationContext context, @Nullable String defaultValue) {
        return (context == null) ? defaultValue : StringUtils.firstNonBlank(context.getApplicationName(), getProperty(context, SpringPropertyConst.APPLICATION_NAME), defaultValue);
    }

    /**
     * Return the given context as an application event publisher.
     *
     * @param context The application context, may be {@code null}
     *
     * @return The context as event publisher, or the default value
     */
    public static ApplicationEventPublisher getApplicationEventPublisher(@Nullable ApplicationContext context) {
        return getApplicationEventPublisher(context, null);
    }

    /**
     * Return the given context as an application event publisher.
     *
     * @param context The application context, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The context as event publisher, or the default value
     */
    public static ApplicationEventPublisher getApplicationEventPublisher(@Nullable ApplicationContext context, @Nullable ApplicationEventPublisher defaultValue) {
        return (context == null) ? defaultValue : context;
    }

    /**
     * Return the display name of the given context.
     *
     * @param context The application context, may be {@code null}
     *
     * @return The display name, or the default value
     */
    public static String getDisplayName(@Nullable ApplicationContext context) {
        return getDisplayName(context, null);
    }

    /**
     * Return the display name of the given context.
     *
     * @param context The application context, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The display name, or the default value
     */
    public static String getDisplayName(@Nullable ApplicationContext context, @Nullable String defaultValue) {
        return (context == null) ? defaultValue : StringUtils.defaultIfBlank(context.getDisplayName(), defaultValue);
    }

    /**
     * Return the context as a configurable application context when applicable.
     *
     * @param context The application context, may be {@code null}
     *
     * @return The configurable context, or {@code null}
     */
    public static ConfigurableApplicationContext getConfigurableApplicationContext(@Nullable ApplicationContext context) {
        return (context instanceof ConfigurableApplicationContext alias) ? alias : null;
    }

    /**
     * Return the context as a web application context when applicable.
     *
     * @param context The application context, may be {@code null}
     *
     * @return The web context, or {@code null}
     */
    public static WebApplicationContext getWebApplicationContext(@Nullable ApplicationContext context) {
        return (context instanceof WebApplicationContext alias) ? alias : null;
    }

    /**
     * Return the context as a web application context when applicable.
     *
     * @param context The application context, may be {@code null}
     *
     * @return The web context, or {@code null}
     */
    public static WebApplicationContext getWebApplicationContext(@Nullable ServletContext context) {
        return (context == null) ? null : WebApplicationContextUtils.getWebApplicationContext(context);
    }

    /**
     * Return the context as a web server application context when applicable.
     *
     * @param context The application context, may be {@code null}
     *
     * @return The web server context, or {@code null}
     */
    public static WebServerApplicationContext getWebServerApplicationContext(@Nullable ApplicationContext context) {
        return (context instanceof WebServerApplicationContext alias) ? alias : null;
    }

    /**
     * @see org.springframework.boot.web.server.context.ServerPortInfoApplicationContextInitializer
     */
    public static Integer getLocalServerPort(@Nullable ApplicationContext context) {
        if (!(context instanceof ConfigurableApplicationContext alias)) {
            return null;
        }
        Integer result = ApplicationEnvironmentWraps.getLocalServerPort(alias.getEnvironment());
        if (result == null && context.getParent() != null) {
            return getLocalServerPort(context.getParent());
        }
        return result;
    }

    /**
     * Return the property name that holds the local server port.
     *
     * @param context The application context, may be {@code null}
     *
     * @return The local server port property name, or {@code null}
     */
    public static String getLocalServerPortProperty(@Nullable ApplicationContext context) {
        return getLocalServerPortProperty(getWebServerApplicationContext(context));
    }

    /**
     * @see org.springframework.boot.web.server.context.ServerPortInfoApplicationContextInitializer#onApplicationEvent
     */
    public static String getLocalServerPortProperty(@Nullable WebServerApplicationContext context) {
        return (context == null) ? null : StringUtils.join("local.", StringUtils.defaultIfBlank(context.getServerNamespace(), "server"), ".port");    // $NON-NLS-1$ // $NON-NLS-2$ // $NON-NLS-3$
    }

    /**
     * Return the named environment property from the given context.
     *
     * @param context The application context, may be {@code null}
     * @param name The property or attribute name, may be {@code null}
     *
     * @return The property value, or the default value
     */
    public static String getProperty(@Nullable ApplicationContext context, @Nullable String name) {
        return getProperty(context, name, null);
    }

    /**
     * Return the named environment property from the given context.
     *
     * @param context The application context, may be {@code null}
     * @param name The property or attribute name, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The property value, or the default value
     */
    @SuppressWarnings("DataFlowIssue")
    public static String getProperty(@Nullable ApplicationContext context, @Nullable String name, @Nullable String defaultValue) {
        return (context == null || StringUtils.isBlank(name)) ? defaultValue : context.getEnvironment().getProperty(name, defaultValue);
    }

    /**
     * Return the servlet context associated with the given context.
     *
     * @param context The application context, may be {@code null}
     *
     * @return The servlet context, or {@code null}
     */
    @Nullable
    public static ServletContext getServletContext(@Nullable ApplicationContext context) {
        WebApplicationContext webContext = getWebApplicationContext(context);
        return (webContext == null) ? null : webContext.getServletContext();
    }

    /**
     * Return the context path of the servlet context.
     *
     * @param context The application context, may be {@code null}
     *
     * @return The context path, or {@code null}
     */
    @Nullable
    public static String getServletContextPath(@Nullable ApplicationContext context) {
        ServletContext servletContext = getServletContext(context);
        return (servletContext == null) ? null : servletContext.getContextPath();
    }

    /**
     * @see org.springframework.web.util.ServletContextPropertyUtils
     */
    public static Object getServletContextAttribute(@Nullable ApplicationContext context, String name) {
        if (context == null || StringUtils.isBlank(name)) {
            return null;
        }
        ServletContext servletContext = getServletContext(context);
        return (servletContext == null) ? null : servletContext.getAttribute(name);
    }

    /**
     * Return the named servlet context attribute converted to the expected type.
     *
     * @param context The application context, may be {@code null}
     * @param name The property or attribute name, may be {@code null}
     * @param expectType The expected type, may be {@code null}
     *
     * @return The converted attribute value, or {@code null}
     */
    @Nullable
    public static <T> T getServletContextAttributeAs(@Nullable ApplicationContext context, @Nullable String name, @Nullable Class<T> expectType) {
        if (ObjectUtils.anyNull(context, expectType) || StringUtils.isBlank(name)) {
            return null;
        }
        Object value = getServletContextAttribute(context, name);
        return (value == null) ? null : ObjectUtilsWraps.castAs(value, expectType);
    }

    /**
     * Set a named attribute on the servlet context.
     *
     * @param context The application context, may be {@code null}
     * @param name The property or attribute name, may be {@code null}
     * @param value The attribute value to set, may be {@code null}
     *
     * @return Whether the attribute was set successfully
     */
    public static boolean setServletContextAttribute(@Nullable ApplicationContext context, @Nullable String name, @Nullable Object value) {
        if (context == null || StringUtils.isBlank(name)) {
            return false;
        }
        ServletContext servletContext = getServletContext(context);
        if (servletContext == null) {
            return false;
        }
        servletContext.setAttribute(name, value);
        return true;
    }
}
