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


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Utilities for {@link org.springframework.security.crypto.password.PasswordEncoder}
 *
 * @author David Hsing
 *
 * @see org.springframework.security.crypto.password.PasswordEncoder
 * @see org.springframework.security.crypto.factory.PasswordEncoderFactories
 * @see com.jiovan.plexus.common.toolkit.enumeration.SecurityAlgorithmType
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class PasswordEncoderWraps {
    /**
     * Build a BCrypt password encoder.
     *
     * @param strength The given strength
     *
     * @return The configured password encoder
     */
    @Nonnull
    public static PasswordEncoder bCryptEncoder(int strength) {
        return new BCryptPasswordEncoder(strength);
    }

    /**
     * Build a BCrypt password encoder.
     *
     * @param strength The given strength
     * @param algorithm The given algorithm
     *
     * @return The configured password encoder
     *
     * @throws NoSuchAlgorithmException If the operation fails
     */
    @Nullable
    public static PasswordEncoder bCryptEncoder(int strength, @Nullable String algorithm) throws NoSuchAlgorithmException {
        return StringUtils.isBlank(algorithm) ? null : new BCryptPasswordEncoder(strength, SecureRandom.getInstance(algorithm));
    }

    /**
     * Build a BCrypt password encoder, or {@code null} on failure.
     *
     * @param strength The given strength
     * @param algorithm The given algorithm
     *
     * @return The configured password encoder, or {@code null}
     */
    @Nullable
    public static PasswordEncoder bCryptEncoderQuietly(int strength, @Nullable String algorithm) {
        try {
            return bCryptEncoder(strength, algorithm);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Build a BCrypt password encoder.
     *
     * @param version The given version
     * @param algorithm The given algorithm
     *
     * @return The configured password encoder
     *
     * @throws NoSuchAlgorithmException If the operation fails
     */
    @Nullable
    public static PasswordEncoder bCryptEncoder(@Nullable BCryptPasswordEncoder.BCryptVersion version, @Nullable String algorithm) throws NoSuchAlgorithmException {
        return (version == null || StringUtils.isBlank(algorithm)) ? null : new BCryptPasswordEncoder(version, SecureRandom.getInstance(algorithm));
    }

    /**
     * Build a BCrypt password encoder, or {@code null} on failure.
     *
     * @param version The given version
     * @param algorithm The given algorithm
     *
     * @return The configured password encoder, or {@code null}
     */
    @Nullable
    public static PasswordEncoder bCryptEncoderQuietly(@Nullable BCryptPasswordEncoder.BCryptVersion version, @Nullable String algorithm) {
        try {
            return bCryptEncoder(version, algorithm);
        } catch (Exception ignored) {
        }
        return null;
    }
}
