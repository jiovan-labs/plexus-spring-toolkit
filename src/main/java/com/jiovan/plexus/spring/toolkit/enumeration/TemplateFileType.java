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


import com.jiovan.plexus.common.toolkit.constant.FileExtensionConst;
import com.jiovan.plexus.common.toolkit.enumeration.ValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Enumerations of template file types
 *
 * @author David Hsing
 */
@AllArgsConstructor
@Getter
@SuppressWarnings("unused")
public enum TemplateFileType implements ValueEnum<String> {
    /**
     * FreeMarker template files ({@code .ftlh}).
     */
    FREEMARKER(".ftlh"),    // $NON-NLS-1$

    /**
     * Groovy template files ({@code .tpl}).
     */
    GROOVY(".tpl"),    // $NON-NLS-1$

    /**
     * Mustache template files ({@code .mustache}).
     */
    MUSTACHE(".mustache"),    // $NON-NLS-1$

    /**
     * Thymeleaf template files ({@code .html}).
     */
    THYMELEAF(FileExtensionConst.HTML);

    private final String value;
}
