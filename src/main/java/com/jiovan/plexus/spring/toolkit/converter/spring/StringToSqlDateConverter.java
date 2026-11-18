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

package com.jiovan.plexus.spring.toolkit.converter.spring;


import java.sql.Date;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import com.jiovan.plexus.common.toolkit.constant.TemporalFormatCombo;
import com.jiovan.plexus.common.toolkit.util.JdkDateWraps;
import com.jiovan.plexus.common.toolkit.util.SqlDateWraps;
import lombok.Getter;
import lombok.Setter;


/**
 * Converts an object to {@link java.sql.Date}
 *
 * @author David Hsing
 *
 * @see org.springframework.core.convert.converter.Converter
 */
@Getter
@Setter
@SuppressWarnings("unused")
public class StringToSqlDateConverter implements Converter<String, Date> {
    private String[] formats = TemporalFormatCombo.ALL_DATETIME_DATES;

    /**
     * Convert a string value to a {@link Date} using the configured formats.
     *
     * @param value The source string, may be {@code null}
     *
     * @return The parsed SQL date, or {@code null} if the value cannot be parsed
     */
    @Override
    public Date convert(@Nullable String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        for (String pattern : formats) {
            java.util.Date result = JdkDateWraps.parseDateQuietly(value, pattern);
            if (result != null) {
                return SqlDateWraps.ofJdkDate(result);
            }
        }
        return null;
    }
}
