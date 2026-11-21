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

package com.jiovan.plexus.spring.toolkit.jackson.customizer;


import java.util.TimeZone;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import com.jiovan.plexus.spring.toolkit.jackson.module.JodaTimeModule;
import tools.jackson.databind.module.SimpleModule;


/**
 * Customizer of joda-time for {@link tools.jackson.databind.ObjectMapper}
 *
 * @author David Hsing
 */
public abstract class JodaTimeCustomizer {
    /**
     * Build a JSON mapper builder customizer for Joda-Time types and the default time zone.
     *
     * @param dateFormat The date pattern, or {@code null} for the default
     * @param timeFormat The time pattern, or {@code null} for the default
     * @param dateTimeFormat The date-time pattern, or {@code null} for the default
     * @param timeZone The time zone to apply, or {@code null} for the JVM default
     *
     * @return The configured customizer, or {@code null} if Joda-Time is not on the classpath
     */
    @Nullable
    public static JsonMapperBuilderCustomizer mapperCustomizer(@Nullable String dateFormat, @Nullable String timeFormat, @Nullable String dateTimeFormat, @Nullable TimeZone timeZone) {
        SimpleModule module = JodaTimeModule.mapperModule(dateFormat, timeFormat, dateTimeFormat);
        return (module == null) ? null : builder -> {
            builder.addModules(module);
            builder.defaultTimeZone(ObjectUtils.getIfNull(timeZone, TimeZone.getDefault()));
        };
    }
}
