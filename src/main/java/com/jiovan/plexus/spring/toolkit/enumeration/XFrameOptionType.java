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

package com.jiovan.plexus.spring.toolkit.enumeration;


import com.jiovan.plexus.common.toolkit.enumeration.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Enumerations of Spring Security "X-Frame-Options" header types
 *
 * @author David Hsing
 *
 * @see org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode
 *
 * @see "https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-Frame-Options"
 * @see "https://html.spec.whatwg.org/multipage/document-lifecycle.html#the-x-frame-options-header"
 */
@AllArgsConstructor
@Getter
@SuppressWarnings("unused")
public enum XFrameOptionType implements ValueEnum<String> {
    /**
     * Deny rendering in a frame.
     */
    DENY("deny"),    // $NON-NLS-1$

    /**
     * Allow rendering only from the same origin.
     */
    SAME_ORIGIN("sameorigin"),    // $NON-NLS-1$

    /**
     * Allow rendering from a specified origin.
     */
    ALLOW_FROM("allow-from"),    // $NON-NLS-1$

    /**
     * Allow rendering from any origin.
     */
    ALLOW_ALL("allowall"),    // $NON-NLS-1$

    /**
     * Invalid or unrecognized option.
     */
    INVALID("invalid");    // $NON-NLS-1$

    private final String value;
}
