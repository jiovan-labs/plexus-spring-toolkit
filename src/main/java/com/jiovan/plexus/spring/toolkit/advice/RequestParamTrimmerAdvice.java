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
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;


/**
 * {@link ControllerAdvice} that binds a {@link StringTrimmerEditor} to {@link WebDataBinder}
 *
 * @author David Hsing
 *
 * @see org.springframework.beans.propertyeditors.StringTrimmerEditor
 * @see com.jiovan.plexus.spring.toolkit.annotation.EnableRequestParamTrimmer
 */
@ControllerAdvice
@SuppressWarnings("unused")
public class RequestParamTrimmerAdvice {
    /**
     * Whether a trimmed blank string should be returned as {@code null} instead of an empty string
     */
    private final boolean emptyAsNull;

    public RequestParamTrimmerAdvice(boolean emptyAsNull) {
        this.emptyAsNull = emptyAsNull;
    }

    /**
     * Registers a {@link StringTrimmerEditor} that trims blank strings, converting them to {@code null} when {@code emptyAsNull} is {@code true}
     *
     * @param binder the web data binder to register the custom editor with
     */
    @InitBinder
    public void initBinder(@Nonnull WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(emptyAsNull));
    }
}
