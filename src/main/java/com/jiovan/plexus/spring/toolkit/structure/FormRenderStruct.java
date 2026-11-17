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

package com.jiovan.plexus.spring.toolkit.structure;


import java.io.Serializable;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import com.jiovan.plexus.common.toolkit.constant.StringVariantConst;
import lombok.experimental.Accessors;
import lombok.Getter;
import lombok.Setter;


/**
 * Structure for form render
 *
 * @param <T> the type of the object inside
 *
 * @author David Hsing
 *
 * @see com.jiovan.plexus.spring.toolkit.enumeration.FormRenderType
 * @see "https://xrender.fun"
 */
@Accessors(chain = true)
@Getter
@SuppressWarnings("unused")
public class FormRenderStruct<T> implements Serializable {
    private final String type = StringVariantConst.OBJECT;

    @Setter
    private T properties;

    public FormRenderStruct(@Nullable T properties) {
        this.properties = properties;
    }

    @Nonnull
    public static <T> FormRenderStruct<T> of(@Nullable T properties) {
        return new FormRenderStruct<>(properties);
    }
}
