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


import java.time.Duration;
import java.util.Date;
import java.util.function.Consumer;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jiovan.plexus.common.toolkit.identity.JdkUuidGenerator;
import com.jiovan.plexus.common.toolkit.util.JdkDateWraps;
import com.jiovan.plexus.spring.toolkit.enumeration.JwtAlgorithmType;


/**
 * Utilities for JWT
 *
 * @author David Hsing
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class JwtAuthWraps {
    /**
     * Create and sign a JWT token.
     *
     * @param jwtId The token id, may be {@code null}
     * @param callback An optional callback for customizing the token builder
     * @param algorithm The signing algorithm, defaulting to HS256
     * @param secret The signing secret, required for HMAC algorithms
     *
     * @return The signed JWT token
     */
    @Nonnull
    @SuppressWarnings("DuplicatedCode")
    public static String encodeToken(@Nullable String jwtId, @Nullable Consumer<JWTCreator.Builder> callback, @Nullable JwtAlgorithmType algorithm, @Nullable String secret) {
        JWTCreator.Builder builder = JWT.create();
        builder.withJWTId(StringUtils.defaultIfBlank(jwtId, JdkUuidGenerator.getRandomId()));
        if (callback != null) {
            callback.accept(builder);
        }
        JwtAlgorithmType algorithmAlias = ObjectUtils.getIfNull(algorithm, JwtAlgorithmType.HS256);
        if (algorithmAlias != JwtAlgorithmType.NONE) {
            Assert.notNull(secret, "The secret must not be null");
        }
        return switch (algorithmAlias) {
            case NONE -> builder.sign(Algorithm.none());
            case HS256 -> builder.sign(Algorithm.HMAC256(secret));
            case HS384 -> builder.sign(Algorithm.HMAC384(secret));
            case HS512 -> builder.sign(Algorithm.HMAC512(secret));
        };
    }

    /**
     * Create and sign a JWT token.
     *
     * @param jwtId The token id, may be {@code null}
     * @param audience The token audience, may be {@code null}
     * @param issuer The token issuer, may be {@code null}
     * @param subject The token subject, may be {@code null}
     * @param timestamp The issue time, defaulting to the current time
     * @param timeout The validity duration, may be {@code null}
     * @param algorithm The signing algorithm, defaulting to HS256
     * @param secret The signing secret, required for HMAC algorithms
     *
     * @return The signed JWT token
     */
    @Nonnull
    @SuppressWarnings("DuplicatedCode")
    public static String encodeToken(@Nullable String jwtId, @Nullable String audience, @Nullable String issuer, @Nullable String subject, @Nullable Date timestamp, @Nullable Duration timeout, @Nullable JwtAlgorithmType algorithm, @Nullable String secret) {
        JWTCreator.Builder builder = JWT.create();
        builder.withJWTId(StringUtils.defaultIfBlank(jwtId, JdkUuidGenerator.getRandomId()));
        if (StringUtils.isNotBlank(audience)) {
            builder.withAudience(audience);
        }
        if (StringUtils.isNotBlank(issuer)) {
            builder.withIssuer(issuer);
        }
        if (StringUtils.isNotBlank(subject)) {
            builder.withSubject(subject);
        }
        Date timestampAlias = ObjectUtils.getIfNull(timestamp, JdkDateWraps.getCurrentDateTime());
        builder.withIssuedAt(timestampAlias);
        if (timeout != null) {
            builder.withExpiresAt(JdkDateWraps.plusTemporal(timestampAlias, timeout));
        }
        JwtAlgorithmType algorithmAlias = ObjectUtils.getIfNull(algorithm, JwtAlgorithmType.HS256);
        if (algorithmAlias != JwtAlgorithmType.NONE) {
            Assert.notNull(secret, "The secret must not be null");
        }
        return switch (algorithmAlias) {
            case NONE -> builder.sign(Algorithm.none());
            case HS256 -> builder.sign(Algorithm.HMAC256(secret));
            case HS384 -> builder.sign(Algorithm.HMAC384(secret));
            case HS512 -> builder.sign(Algorithm.HMAC512(secret));
        };
    }

    /**
     * Decode the given JWT token without verifying its signature.
     *
     * @param token The JWT token, may be {@code null}
     *
     * @return The decoded JWT, or {@code null} on failure
     */
    @Nullable
    public static DecodedJWT decodeToken(@Nullable String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        try {
            return JWT.decode(token);
        } catch (Exception ignored) {
        }
        return null;
    }
}
