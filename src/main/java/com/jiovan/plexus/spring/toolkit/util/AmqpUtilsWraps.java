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

package com.jiovan.plexus.spring.toolkit.util;


import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.util.CollectionUtils;
import com.jiovan.plexus.common.toolkit.util.ArrayUtilsWraps;
import com.jiovan.plexus.common.toolkit.util.CharsetPlainWraps;
import com.jiovan.plexus.common.toolkit.util.DurationUtilsWraps;
import com.jiovan.plexus.common.toolkit.util.JdkDateWraps;
import com.jiovan.plexus.common.toolkit.util.MapPlainWraps;
import com.jiovan.plexus.common.toolkit.util.NumberUtilsWraps;
import com.jiovan.plexus.common.toolkit.util.StringUtilsWraps;
import com.jiovan.plexus.spring.toolkit.constant.AmqpHeaderConst;
import com.jiovan.plexus.spring.toolkit.identity.AlternativeUuidGenerator;


/**
 * Utilities for AMQP messages
 *
 * @author David Hsing
 *
 * @see org.springframework.amqp.core.MessagePostProcessor
 * @see org.springframework.amqp.core.MessageProperties
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class AmqpUtilsWraps {
    /**
     * Build a message post processor that applies basic message properties.
     *
     * @return The configured message post processor
     */
    @Nonnull
    public static MessagePostProcessor buildBasicProcessor() {
        return buildBasicProcessor(null, null, null, null);
    }

    /**
     * Build a message post processor that applies basic message properties.
     *
     * @param headers The message headers to apply, may be {@code null}
     *
     * @return The configured message post processor
     */
    @Nonnull
    public static MessagePostProcessor buildBasicProcessor(@Nullable Map<String, Object> headers) {
        return buildBasicProcessor(headers, null, null, null);
    }

    /**
     * Build a message post processor that applies basic message properties.
     *
     * @param headers The message headers to apply, may be {@code null}
     * @param contentType The content type to apply, may be {@code null}
     * @param charset The charset to apply, may be {@code null}
     * @param mode The delivery mode to apply, may be {@code null}
     *
     * @return The configured message post processor
     */
    @Nonnull
    public static MessagePostProcessor buildBasicProcessor(@Nullable Map<String, Object> headers, @Nullable String contentType, @Nullable Charset charset, @Nullable MessageDeliveryMode mode) {
        return buildDelayProcessor(Duration.ZERO, null, headers, contentType, charset, mode);
    }

    /**
     * Build a message post processor that applies delayed message properties.
     *
     * @param delay The non-positive delay to apply, may be {@code null}
     * @param delayedTimes The delayed-times count to apply, may be {@code null}
     *
     * @return The configured message post processor
     */
    @Nonnull
    public static MessagePostProcessor buildDelayProcessor(@Nullable Duration delay, @Nullable Integer delayedTimes) {
        return buildDelayProcessor(delay, delayedTimes, null, null, null, null);
    }

    /**
     * Build a message post processor that applies delayed message properties.
     *
     * @param delay The non-positive delay to apply, may be {@code null}
     * @param delayedTimes The delayed-times count to apply, may be {@code null}
     * @param headers The message headers to apply, may be {@code null}
     *
     * @return The configured message post processor
     */
    @Nonnull
    public static MessagePostProcessor buildDelayProcessor(@Nullable Duration delay, @Nullable Integer delayedTimes, @Nullable Map<String, Object> headers) {
        return buildDelayProcessor(delay, delayedTimes, headers, null, null, null);
    }

    /**
     * Build a message post processor that applies delayed message properties.
     *
     * @param delay The non-positive delay to apply, may be {@code null}
     * @param delayedTimes The delayed-times count to apply, may be {@code null}
     * @param headers The message headers to apply, may be {@code null}
     * @param contentType The content type to apply, may be {@code null}
     * @param charset The charset to apply, may be {@code null}
     * @param mode The delivery mode to apply, may be {@code null}
     *
     * @return The configured message post processor
     */
    @Nonnull
    public static MessagePostProcessor buildDelayProcessor(@Nullable Duration delay, @Nullable Integer delayedTimes, @Nullable Map<String, Object> headers, @Nullable String contentType, @Nullable Charset charset, @Nullable MessageDeliveryMode mode) {
        return message -> {
            MessageProperties properties = message.getMessageProperties();
            MapPlainWraps.forEach(headers, properties::setHeader);
            DurationUtilsWraps.ifPositive(delay, item -> properties.setHeader(MessageProperties.X_DELAY, item.toMillis()));
            properties.setHeader(AmqpHeaderConst.X_DELAYED_TIMES, NumberUtilsWraps.isPositive(delayedTimes) ? delayedTimes : 0);
            properties.setContentType(StringUtils.defaultIfBlank(contentType, MessageProperties.CONTENT_TYPE_JSON));
            properties.setContentEncoding(CharsetPlainWraps.defaultCharsetName(charset));
            properties.setDeliveryMode(mode != null ? mode : MessageDeliveryMode.PERSISTENT);
            properties.setMessageId(AlternativeUuidGenerator.getBalanceId());
            properties.setTimestamp(JdkDateWraps.getCurrentDateTime());
            return message;
        };
    }

    /**
     * Return the message body rendered as a string.
     *
     * @param message The AMQP message, may be {@code null}
     *
     * @return The message body as a string, or {@code null} when empty
     */
    public static String getContentAsString(@Nullable Message message) {
        return getContentAsString(message, null);
    }

    /**
     * @see org.springframework.amqp.core.Message#getBodyContentAsString
     */
    @SuppressWarnings("JavadocReference")
    public static String getContentAsString(@Nullable Message message, @Nullable Charset fallbackCharset) {
        if (message == null || ArrayUtils.isEmpty(message.getBody())) {
            return null;
        }
        MessageProperties properties = message.getMessageProperties();
        if (StringUtilsWraps.equalsIgnoreCase(properties.getContentType(), MessageProperties.CONTENT_TYPE_SERIALIZED_OBJECT)) {
            return ObjectUtilsWraps.getDisplayString(SerializationUtils.deserialize(message.getBody()));
        }
        String charset = CharsetPlainWraps.defaultCharsetName(fallbackCharset);
        return IOUtils.toString(message.getBody(), StringUtils.defaultIfBlank(properties.getContentEncoding(), charset));
    }

    /**
     * Return the delayed-times header value of the message.
     *
     * @param message The AMQP message, may be {@code null}
     *
     * @return The delayed-times value, or {@code null}
     */
    public static Integer getDelayedTimes(@Nullable Message message) {
        return getHeaderAs(message, AmqpHeaderConst.X_DELAYED_TIMES, Integer.class);
    }

    /**
     * Return the value of the named message header.
     *
     * @param message The AMQP message, may be {@code null}
     * @param header The header name, may be {@code null}
     *
     * @return The header value, or {@code null}
     */
    public static Object getHeader(@Nullable Message message, @Nullable String header) {
        return (message == null || StringUtils.isBlank(header)) ? null : MapPlainWraps.getObject(getHeaders(message), header);
    }

    /**
     * Return the named message header converted to the expected type.
     *
     * @param message The AMQP message, may be {@code null}
     * @param header The header name, may be {@code null}
     * @param expectType The expected type, may be {@code null}
     *
     * @return The converted header value, or {@code null}
     */
    @Nullable
    public static <T> T getHeaderAs(@Nullable Message message, @Nullable String header, @Nullable Class<T> expectType) {
        if (ObjectUtils.anyNull(message, expectType) || StringUtils.isBlank(header)) {
            return null;
        }
        return MapPlainWraps.getObjectAs(getHeaders(message), header, expectType);
    }

    /**
     * Return all headers of the message.
     *
     * @param message The AMQP message, may be {@code null}
     *
     * @return The message headers, or {@code null}
     */
    public static Map<String, Object> getHeaders(@Nullable Message message) {
        return (message == null) ? null : message.getMessageProperties().getHeaders();
    }

    /**
     * Return the message headers excluding the named ones.
     *
     * @param message The AMQP message, may be {@code null}
     * @param headers The message headers to apply, may be {@code null}
     *
     * @return The filtered headers, or {@code null} when empty
     */
    @Nullable
    public static Map<String, Object> getHeadersExcluding(@Nullable Message message, @Nullable String... headers) {
        return getHeadersExcluding(message, ArrayUtilsWraps.asList(headers));
    }

    /**
     * Return the message headers excluding the named ones.
     *
     * @param message The AMQP message, may be {@code null}
     * @param headers The message headers to apply, may be {@code null}
     *
     * @return The filtered headers, or {@code null} when empty
     */
    @Nullable
    public static Map<String, Object> getHeadersExcluding(@Nullable Message message, @Nullable Collection<String> headers) {
        Map<String, Object> result = getHeaders(message);
        MapPlainWraps.removeByKeys(result, headers);
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    /**
     * Return the message headers excluding delay-related ones.
     *
     * @param message The AMQP message, may be {@code null}
     *
     * @return The filtered headers, or {@code null} when empty
     */
    @Nullable
    public static Map<String, Object> getHeadersExcludingDelay(@Nullable Message message) {
        return getHeadersExcluding(message, MessageProperties.X_DELAY, AmqpHeaderConst.X_DELAYED_TIMES);
    }

    /**
     * Return only the named message headers.
     *
     * @param message The AMQP message, may be {@code null}
     * @param headers The message headers to apply, may be {@code null}
     *
     * @return The filtered headers, or {@code null} when empty
     */
    @Nullable
    public static Map<String, Object> getHeadersIncluding(@Nullable Message message, @Nullable String... headers) {
        return getHeadersIncluding(message, ArrayUtilsWraps.asList(headers));
    }

    /**
     * Return only the named message headers.
     *
     * @param message The AMQP message, may be {@code null}
     * @param headers The message headers to apply, may be {@code null}
     *
     * @return The filtered headers, or {@code null} when empty
     */
    @Nullable
    public static Map<String, Object> getHeadersIncluding(@Nullable Message message, @Nullable Collection<String> headers) {
        Map<String, Object> result = getHeaders(message);
        MapPlainWraps.removeIfKey(result, key -> !StringUtilsWraps.equalsAny(key, headers));
        return CollectionUtils.isEmpty(result) ? null : result;
    }
}
