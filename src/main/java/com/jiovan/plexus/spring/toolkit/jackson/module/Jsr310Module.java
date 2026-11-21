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

package com.jiovan.plexus.spring.toolkit.jackson.module;


import java.time.format.DateTimeFormatter;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ClassUtils;
import com.jiovan.plexus.common.toolkit.constant.TemporalFormatConst;
import com.jiovan.plexus.spring.toolkit.enumeration.JacksonTemporalType;
import tools.jackson.databind.module.SimpleModule;


/**
 * Jackson module that registers serializers and deserializers for {@code java.time} types.
 *
 * @author David Hsing
 *
 * @see tools.jackson.databind.module.SimpleModule
 */
public abstract class Jsr310Module {
    /**
     * Build a Jackson module with custom serializers and deserializers for {@code java.time} types.
     *
     * @param dateFormat The date pattern, or {@code null} for the default
     * @param timeFormat The time pattern, or {@code null} for the default
     * @param dateTimeFormat The date-time pattern, or {@code null} for the default
     *
     * @return The configured module, or {@code null} if {@code java.time} support is not available
     */
    @Nullable
    public static SimpleModule mapperModule(@Nullable String dateFormat, @Nullable String timeFormat, @Nullable String dateTimeFormat) {
        if (!ClassUtils.isPresent(JacksonTemporalType.JSR310.getValue(), null)) {
            return null;
        }
        dateFormat = StringUtils.defaultIfBlank(dateFormat, TemporalFormatConst.ISO_YYYYMMDD);
        timeFormat = StringUtils.defaultIfBlank(timeFormat, TemporalFormatConst.ISO_HHMMSS);
        dateTimeFormat = StringUtils.defaultIfBlank(dateTimeFormat, TemporalFormatConst.ISO_YYYYMMDD_HHMMSS);
        SimpleModule result = new tools.jackson.databind.module.SimpleModule();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timeFormat);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        result.addSerializer(java.time.LocalDate.class, new tools.jackson.databind.ext.javatime.ser.LocalDateSerializer(dateFormatter));
        result.addSerializer(java.time.LocalTime.class, new tools.jackson.databind.ext.javatime.ser.LocalTimeSerializer(timeFormatter));
        result.addSerializer(java.time.LocalDateTime.class, new tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer(dateTimeFormatter));
        result.addDeserializer(java.time.LocalDate.class, new tools.jackson.databind.ext.javatime.deser.LocalDateDeserializer(dateFormatter));
        result.addDeserializer(java.time.LocalTime.class, new tools.jackson.databind.ext.javatime.deser.LocalTimeDeserializer(timeFormatter));
        result.addDeserializer(java.time.LocalDateTime.class, new tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer(dateTimeFormatter));
        return result;
    }
}
