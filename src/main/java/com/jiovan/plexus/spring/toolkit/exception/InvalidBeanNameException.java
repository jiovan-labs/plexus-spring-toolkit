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

package com.jiovan.plexus.spring.toolkit.exception;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.BeansException;


/**
 * Exception thrown to indicate that the requested bean name is invalid
 *
 * @author David Hsing
 */
@SuppressWarnings("unused")
public class InvalidBeanNameException extends BeansException {
    /**
     * Create an exception for the given invalid bean name.
     *
     * @param beanName The invalid bean name
     */
    public InvalidBeanNameException(@Nonnull String beanName) {
        super(String.format("The bean name '%s' is invalid", beanName));    // $NON-NLS-1$
    }

    /**
     * Create an exception for the given invalid bean name with an additional message.
     *
     * @param beanName The invalid bean name
     * @param message Additional detail, may be {@code null}
     */
    public InvalidBeanNameException(@Nonnull String beanName, @Nullable String message) {
        super(String.format("The bean name '%s' is invalid: %s", beanName, message));    // $NON-NLS-1$
    }
}
