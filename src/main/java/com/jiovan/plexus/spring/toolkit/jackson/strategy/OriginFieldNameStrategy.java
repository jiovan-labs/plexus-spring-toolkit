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

package com.jiovan.plexus.spring.toolkit.jackson.strategy;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import tools.jackson.databind.PropertyNamingStrategy;
import tools.jackson.databind.cfg.MapperConfig;
import tools.jackson.databind.introspect.AnnotatedField;
import tools.jackson.databind.introspect.AnnotatedMethod;


/**
 * {@link tools.jackson.databind.PropertyNamingStrategy} for using original field names
 * <p>
 * Usage: annotates on bean type<br/>
 * <pre><code>
 *     {@literal @}JsonNaming(value = OriginFieldNameStrategy.class)
 * </code></pre>
 *
 * @author David Hsing
 *
 * @see tools.jackson.databind.PropertyNamingStrategy
 * @see tools.jackson.databind.annotation.JsonNaming
 *
 * @see "http://stackoverflow.com/questions/18319931/custom-naming-strategy-and-jsonproperty-in-jackson"
 * @see "http://wiki.fasterxml.com/JacksonFeaturePropertyNamingStrategy"
 */
@SuppressWarnings("unused")
public class OriginFieldNameStrategy extends PropertyNamingStrategy {
    @Override
    public String nameForField(@Nullable MapperConfig<?> config, @Nonnull AnnotatedField field, @Nonnull String defaultName) {
        return field.getName();
    }

    @Override
    public String nameForGetterMethod(@Nullable MapperConfig<?> config, @Nonnull AnnotatedMethod method, @Nonnull String defaultName) {
        return method.getName();
    }

    @Override
    public String nameForSetterMethod(@Nullable MapperConfig<?> config, @Nonnull AnnotatedMethod method, @Nonnull String defaultName) {
        return method.getName();
    }
}
