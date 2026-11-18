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

package com.jiovan.plexus.spring.toolkit.jackson.deserializer;


import java.util.Date;
import jakarta.annotation.Nullable;
import com.jiovan.plexus.common.toolkit.util.SqlDateWraps;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;


/**
 * {@link tools.jackson.databind.ValueDeserializer} for deserializing {@link java.sql.Timestamp} {@link java.lang.Long} to {@link java.util.Date}
 * <p>
 * Usage: annotates on bean type, fields, or methods<br/>
 * <pre><code>
 *     {@literal @}JsonDeserialize(using = SqlTimestamp2SqlDateDeserializer.class)
 * </code></pre>
 *
 * @author David Hsing
 *
 * @see tools.jackson.databind.ValueDeserializer
 * @see tools.jackson.databind.annotation.JsonDeserialize
 */
@SuppressWarnings("unused")
public class SqlTimestampToSqlDateDeserializer extends SqlTimestampToJdkDateDeserializer {
    /**
     * Deserialize a JSON number or string representing epoch milliseconds into a {@link java.sql.Date}.
     *
     * @param parser The active parser holding the JSON token
     * @param context The active deserialization context
     *
     * @return The parsed SQL date, or {@code null} if the input is non-positive or blank
     */
    @Override
    public Date deserialize(@Nullable JsonParser parser, @Nullable DeserializationContext context) {
        return SqlDateWraps.ofJdkDate(super.deserialize(parser, context));
    }
}
