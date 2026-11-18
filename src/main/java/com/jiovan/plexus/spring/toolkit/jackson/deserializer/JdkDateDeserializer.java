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
import org.apache.commons.lang3.StringUtils;
import com.jiovan.plexus.common.toolkit.constant.TemporalFormatCombo;
import com.jiovan.plexus.common.toolkit.util.JdkDateWraps;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;
import lombok.Getter;
import lombok.Setter;


/**
 * {@link tools.jackson.databind.ValueDeserializer} for deserializing {@link java.lang.String} to  {@link java.util.Date}
 * <p>
 * Usage: annotates on bean type, fields, or methods<br/>
 * <pre><code>
 *     {@literal @}JsonDeserialize(using = JdkDateDeserializer.class)
 * </code></pre>
 *
 * @author David Hsing
 *
 * @see tools.jackson.databind.ValueDeserializer
 * @see tools.jackson.databind.annotation.JsonDeserialize
 * @see org.springframework.format.annotation.DateTimeFormat
 */
@Getter
@Setter
public class JdkDateDeserializer extends ValueDeserializer<Date> {
    /**
     * Default date formats used for parsing string values into {@link Date}.
     */
    private String[] dateFormats = TemporalFormatCombo.ALL_DATETIME_DATES;

    /**
     * Deserialize a JSON string value into a {@link Date} using configured format patterns.
     *
     * @param parser The active parser holding the JSON string token
     * @param context The active deserialization context
     *
     * @return The parsed date, or {@code null} if the input is blank
     */
    @Override
    public Date deserialize(@Nullable JsonParser parser, @Nullable DeserializationContext context) {
        if (parser == null || StringUtils.isBlank(parser.getString())) {
            return null;
        }
        return JdkDateWraps.parseDateFormats(StringUtils.trimToNull(parser.getString()), dateFormats);
    }
}
