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

package com.jiovan.plexus.spring.toolkit.registrar.assistant;


import java.text.SimpleDateFormat;
import java.util.TimeZone;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import com.jiovan.plexus.spring.toolkit.jackson.deserializer.SqlDateDeserializer;
import tools.jackson.databind.module.SimpleModule;
import com.jiovan.plexus.common.toolkit.constant.TemporalFormatConst;
import com.jiovan.plexus.spring.toolkit.jackson.deserializer.JdkDateDeserializer;
import com.jiovan.plexus.spring.toolkit.jackson.serializer.SqlDateSerializer;


/**
 * Customizer of util-date for {@link tools.jackson.databind.ObjectMapper}
 *
 * @author David Hsing
 */
public abstract class JacksonJdkDateCustomizer {
    /**
     * Build a Jackson module with custom serializers and deserializers for util-date types.
     *
     * @param dateFormat The date pattern to use, or {@code null} for the ISO date pattern
     *
     * @return The configured Jackson module
     */
    @Nonnull
    public static SimpleModule mapperModule(@Nullable String dateFormat) {
        dateFormat = StringUtils.defaultIfBlank(dateFormat, TemporalFormatConst.ISO_YYYYMMDD);
        SimpleModule result = new SimpleModule();
        result.addSerializer(java.util.Date.class, new tools.jackson.databind.ser.jdk.JavaUtilDateSerializer(false, new SimpleDateFormat(dateFormat)));
        result.addSerializer(java.sql.Date.class, new SqlDateSerializer(false, new SimpleDateFormat(dateFormat)));
        result.addDeserializer(java.util.Date.class, new JdkDateDeserializer());
        result.addDeserializer(java.sql.Date.class, new SqlDateDeserializer());
        return result;
    }

    /**
     * Build a JSON mapper builder customizer that registers date handling and sets the default time zone.
     *
     * @param dateFormat The date pattern to use, or {@code null} for the default
     * @param timeZone The time zone to apply, or {@code null} for the JVM default
     *
     * @return The configured mapper builder customizer
     */
    @Nonnull
    public static JsonMapperBuilderCustomizer mapperCustomizer(@Nullable String dateFormat, @Nullable TimeZone timeZone) {
        return builder -> {
            builder.addModules(mapperModule(dateFormat));
            builder.defaultTimeZone(ObjectUtils.getIfNull(timeZone, TimeZone.getDefault()));
        };
    }
}
