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
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import com.jiovan.plexus.spring.toolkit.jackson.module.JdkDateModule;


/**
 * Customizer of util-date for {@link tools.jackson.databind.ObjectMapper}
 *
 * @author David Hsing
 */
public abstract class JdkDateCustomizer {
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
            builder.addModules(JdkDateModule.mapperModule(dateFormat));
            builder.defaultTimeZone(ObjectUtils.getIfNull(timeZone, TimeZone.getDefault()));
        };
    }
}
