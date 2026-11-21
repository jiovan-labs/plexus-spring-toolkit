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


import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ClassUtils;
import com.jiovan.plexus.common.toolkit.constant.TemporalFormatConst;
import com.jiovan.plexus.spring.toolkit.enumeration.JacksonTemporalType;
import tools.jackson.databind.module.SimpleModule;


/**
 * Jackson module that registers serializers and deserializers for Joda-Time types.
 *
 * @author David Hsing
 *
 * @see tools.jackson.databind.module.SimpleModule
 */
public abstract class JodaTimeModule {
    /**
     * Build a Jackson module with custom serializers and deserializers for Joda-Time types.
     *
     * @param dateFormat The date pattern, or {@code null} for the default
     * @param timeFormat The time pattern, or {@code null} for the default
     * @param dateTimeFormat The date-time pattern, or {@code null} for the default
     *
     * @return The configured module, or {@code null} if Joda-Time is not on the classpath
     */
    @Nullable
    public static SimpleModule mapperModule(@Nullable String dateFormat, @Nullable String timeFormat, @Nullable String dateTimeFormat) {
        if (!ClassUtils.isPresent(JacksonTemporalType.JODA.getValue(), null)) {
            return null;
        }
        dateFormat = StringUtils.defaultIfBlank(dateFormat, TemporalFormatConst.ISO_YYYYMMDD);
        timeFormat = StringUtils.defaultIfBlank(timeFormat, TemporalFormatConst.ISO_HHMMSS);
        dateTimeFormat = StringUtils.defaultIfBlank(dateTimeFormat, TemporalFormatConst.ISO_YYYYMMDD_HHMMSS);
        tools.jackson.datatype.joda.cfg.JacksonJodaDateFormat dateFormatter = new tools.jackson.datatype.joda.cfg.JacksonJodaDateFormat(org.joda.time.format.DateTimeFormat.forPattern(dateFormat));
        tools.jackson.datatype.joda.cfg.JacksonJodaDateFormat timeFormatter = new tools.jackson.datatype.joda.cfg.JacksonJodaDateFormat(org.joda.time.format.DateTimeFormat.forPattern(timeFormat));
        tools.jackson.datatype.joda.cfg.JacksonJodaDateFormat dateTimeFormatter = new tools.jackson.datatype.joda.cfg.JacksonJodaDateFormat(org.joda.time.format.DateTimeFormat.forPattern(dateTimeFormat));
        SimpleModule result = new tools.jackson.databind.module.SimpleModule();
        result.addSerializer(org.joda.time.LocalDate.class, new tools.jackson.datatype.joda.ser.LocalDateSerializer(dateFormatter));
        result.addSerializer(org.joda.time.LocalTime.class, new tools.jackson.datatype.joda.ser.LocalTimeSerializer(timeFormatter));
        result.addSerializer(org.joda.time.LocalDateTime.class, new tools.jackson.datatype.joda.ser.LocalDateTimeSerializer(timeFormatter));
        result.addDeserializer(org.joda.time.LocalDate.class, new tools.jackson.datatype.joda.deser.LocalDateDeserializer(dateFormatter));
        result.addDeserializer(org.joda.time.LocalTime.class, new tools.jackson.datatype.joda.deser.LocalTimeDeserializer(timeFormatter));
        result.addDeserializer(org.joda.time.LocalDateTime.class, new tools.jackson.datatype.joda.deser.LocalDateTimeDeserializer(dateTimeFormatter));
        return result;
    }
}
