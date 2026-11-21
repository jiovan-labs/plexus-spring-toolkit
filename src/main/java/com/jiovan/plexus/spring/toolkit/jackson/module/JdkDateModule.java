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


import java.text.SimpleDateFormat;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import com.jiovan.plexus.common.toolkit.constant.TemporalFormatConst;
import com.jiovan.plexus.spring.toolkit.jackson.deserializer.JdkDateDeserializer;
import com.jiovan.plexus.spring.toolkit.jackson.deserializer.SqlDateDeserializer;
import com.jiovan.plexus.spring.toolkit.jackson.serializer.SqlDateSerializer;
import tools.jackson.databind.module.SimpleModule;


/**
 * Jackson module that registers serializers and deserializers for util-date types.
 *
 * @author David Hsing
 *
 * @see tools.jackson.databind.module.SimpleModule
 */
public abstract class JdkDateModule {
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
}
