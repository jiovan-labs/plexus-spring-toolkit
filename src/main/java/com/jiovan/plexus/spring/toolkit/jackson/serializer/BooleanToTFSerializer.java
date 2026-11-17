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

package com.jiovan.plexus.spring.toolkit.jackson.serializer;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.SerializationContext;
import com.jiovan.plexus.common.toolkit.util.BooleanUtilsWraps;
import lombok.Getter;
import lombok.Setter;


/**
 * {@link tools.jackson.databind.ValueSerializer} for serializing {@link java.lang.Boolean} to {@link java.lang.String} with values "t/f"
 * <p>
 * Usage: annotates on bean properties<br/>
 * <pre><code>
 *     {@literal @}JsonSerialize(using = Boolean2TFSerializer.class)
 * </code></pre>
 *
 * @author David Hsing
 *
 * @see tools.jackson.databind.ValueSerializer
 * @see tools.jackson.databind.annotation.JsonSerialize
 */
@Getter
@Setter
@SuppressWarnings("unused")
public class BooleanToTFSerializer extends ValueSerializer<Boolean> {
    private boolean uppercase = false;

    @Override
    public void serialize(@Nullable Boolean value, @Nonnull JsonGenerator generator, @Nullable SerializationContext provider) {
        generator.writeString(BooleanUtilsWraps.toStringTF(value, uppercase));
    }
}
