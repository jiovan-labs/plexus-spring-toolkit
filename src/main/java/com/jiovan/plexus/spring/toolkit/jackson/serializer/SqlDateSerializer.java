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


import java.text.DateFormat;
import jakarta.annotation.Nullable;
import lombok.NoArgsConstructor;


/**
 * {@link tools.jackson.databind.ValueSerializer} for serializing {@link java.lang.String} to {@link java.sql.Date}
 * <p>
 * Usage: annotates on bean properties<br/>
 * <pre><code>
 *     {@literal @}JsonSerialize(using = SqlDateSerializer.class)
 * </code></pre>
 *
 * @author David Hsing
 *
 * @see tools.jackson.databind.ValueSerializer
 * @see tools.jackson.databind.annotation.JsonSerialize
 */
@NoArgsConstructor
@SuppressWarnings("unused")
public class SqlDateSerializer extends tools.jackson.databind.ext.sql.JavaSqlDateSerializer {
    /**
     * Create a serializer for {@link java.sql.Date} with the given configuration options.
     *
     * @param useTimestamp whether to emit timestamps instead of plain dates
     * @param dateFormat the date format to use, or {@code null} for defaults
     */
    public SqlDateSerializer(@Nullable Boolean useTimestamp, @Nullable DateFormat dateFormat) {
        super(useTimestamp, dateFormat);
    }
}
