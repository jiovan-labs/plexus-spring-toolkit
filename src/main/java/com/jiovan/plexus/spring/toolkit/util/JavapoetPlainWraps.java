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


import java.lang.reflect.Method;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.MethodSpec;
import com.jiovan.plexus.common.toolkit.constant.JavaKeywordConst;
import com.jiovan.plexus.common.toolkit.enumeration.LineSeparatorType;


/**
 * Utilities for JavaPoet
 *
 * @author David Hsing
 *
 * @see com.palantir.javapoet.JavaFile
 * @see "https://github.com/square/javapoet"
 * @see "https://www.baeldung.com/java-poet"
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class JavapoetPlainWraps {
    /**
     * Append one or more line separators to the given code block.
     *
     * @param builder The code block builder, may be {@code null}
     * @param skipEmpty Whether to skip appending when the builder is empty
     */
    public static void addLineSeparator(@Nullable CodeBlock.Builder builder, boolean skipEmpty) {
        addLineSeparator(builder, skipEmpty, 1);
    }

    /**
     * Append one or more line separators to the given code block.
     *
     * @param builder The code block builder, may be {@code null}
     * @param skipEmpty Whether to skip appending when the builder is empty
     * @param count The number of line separators to append
     */
    public static void addLineSeparator(@Nullable CodeBlock.Builder builder, boolean skipEmpty, int count) {
        if (builder == null || (builder.isEmpty() && skipEmpty)) {
            return;
        }
        for (int i = 0; i < count; i++) {
            builder.add(LineSeparatorType.LF.getValue());
        }
    }

    /**
     * Append a default return statement matching the method return type.
     *
     * @param method The method whose return type is inspected, may be {@code null}
     * @param builder The code block builder, may be {@code null}
     * @param skipVoid Whether to skip void return types
     */
    public static void addReturnStatement(@Nullable Method method, @Nullable MethodSpec.Builder builder, boolean skipVoid) {
        if (ObjectUtils.anyNull(method, builder)) {
            return;
        }
        if (method.getReturnType() == Void.TYPE) {
            if (!skipVoid) {
                builder.addStatement(JavaKeywordConst.RETURN);
            }
            return;
        }
        if (method.getReturnType() == Boolean.TYPE) {
            builder.addStatement("return false");    // $NON-NLS-1$
        } else if (method.getReturnType() == Byte.TYPE || method.getReturnType() == Character.TYPE || method.getReturnType() == Integer.TYPE || method.getReturnType() == Short.TYPE) {
            builder.addStatement("return 0");    // $NON-NLS-1$
        } else if (method.getReturnType() == Double.TYPE) {
            builder.addStatement("return 0.0D");    // $NON-NLS-1$
        } else if (method.getReturnType() == Float.TYPE) {
            builder.addStatement("return 0.0F");    // $NON-NLS-1$
        } else if (method.getReturnType() == Long.TYPE) {
            builder.addStatement("return 0L");    // $NON-NLS-1$
        } else {
            builder.addStatement("return null");    // $NON-NLS-1$
        }
    }
}
