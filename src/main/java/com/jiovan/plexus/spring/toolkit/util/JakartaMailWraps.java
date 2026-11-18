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


import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Objects;
import jakarta.annotation.Nullable;
import jakarta.mail.internet.InternetAddress;
import org.apache.commons.lang3.StringUtils;
import com.jiovan.plexus.common.toolkit.constant.CharVariantConst;


/**
 * Utilities for jakarta mail
 *
 * @author David Hsing
 *
 * @see jakarta.mail.internet.MimeUtility
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class JakartaMailWraps {
    /**
     * Create an internet address from the given email.
     *
     * @param email The email address, may be {@code null}
     *
     * @return The internet address, or {@code null} when the email is blank
     *
     * @throws UnsupportedEncodingException If the operation fails
     */
    @Nullable
    public static InternetAddress mailAddress(@Nullable String email) throws UnsupportedEncodingException {
        return mailAddress(email, null);
    }

    /**
     * Returns a new {@link jakarta.mail.internet.InternetAddress} with the specified email
     *
     * @param email The given email address
     * @param personal Whether to extract personal information automatically (before the character @)
     *
     * @return a new {@link jakarta.mail.internet.InternetAddress} with the specified email
     *
     * @throws java.io.UnsupportedEncodingException if the personal name can't be encoded in the default charset
     */
    @Nullable
    public static InternetAddress mailAddress(@Nullable String email, boolean personal) throws UnsupportedEncodingException {
        return mailAddress(email, (personal ? StringUtils.substringBefore(email, CharVariantConst.AT) : null));
    }

    /**
     * Create an internet address from the given email.
     *
     * @param email The email address, may be {@code null}
     * @param personal The personal name, may be {@code null}
     *
     * @return The internet address, or {@code null} when the email is blank
     *
     * @throws UnsupportedEncodingException If the operation fails
     */
    @Nullable
    public static InternetAddress mailAddress(@Nullable String email, @Nullable String personal) throws UnsupportedEncodingException {
        return mailAddress(email, personal, null);
    }

    /**
     * Create an internet address from the given email.
     *
     * @param email The email address, may be {@code null}
     * @param personal The personal name, may be {@code null}
     * @param charset The charset for the personal name, may be {@code null}
     *
     * @return The internet address, or {@code null} when the email is blank
     *
     * @throws UnsupportedEncodingException If the operation fails
     */
    @Nullable
    public static InternetAddress mailAddress(@Nullable String email, @Nullable String personal, @Nullable Charset charset) throws UnsupportedEncodingException {
        return StringUtils.isBlank(email) ? null : new InternetAddress(email, personal, Objects.toString(charset, null));
    }
}
