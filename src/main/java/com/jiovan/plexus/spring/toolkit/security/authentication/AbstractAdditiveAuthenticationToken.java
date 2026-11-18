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

package com.jiovan.plexus.spring.toolkit.security.authentication;


import java.util.Collection;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import com.jiovan.plexus.common.toolkit.util.ObjectUtilsWraps;
import lombok.Getter;
import lombok.Setter;


/**
 * {@link org.springframework.security.core.Authentication} token with additive
 *
 * @author David Hsing
 *
 * @see org.springframework.security.authentication.AbstractAuthenticationToken
 */
@Getter
@SuppressWarnings("unused")
public abstract class AbstractAdditiveAuthenticationToken extends AbstractAuthenticationToken {
    /**
     * The authenticated principal.
     */
    private final Object principal;

    /**
     * The authentication credentials.
     */
    private final Object credentials;

    /**
     * An additional value carried by this token.
     */
    @Setter
    private Object additive;

    /**
     * Create a AbstractAdditiveAuthenticationToken.
     *
     * @param principal The given principal
     */
    public AbstractAdditiveAuthenticationToken(@Nonnull Object principal) {
        this(principal, null, null);
    }

    /**
     * Create a AbstractAdditiveAuthenticationToken.
     *
     * @param principal The given principal
     * @param credentials The given credentials
     */
    public AbstractAdditiveAuthenticationToken(@Nonnull Object principal, @Nullable Object credentials) {
        this(principal, credentials, null);
    }

    /**
     * Create a AbstractAdditiveAuthenticationToken.
     *
     * @param principal The given principal
     * @param credentials The given credentials
     * @param authorities The given authorities
     */
    public AbstractAdditiveAuthenticationToken(@Nonnull Object principal, @Nullable Object credentials, @Nullable Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
    }

    /**
     * Returns the principalAs.
     *
     * @param expectType The expected type, may be {@code null}
     *
     * @return The result of the operation
     */
    public <T> T getPrincipalAs(@Nullable Class<T> expectType) {
        return getPrincipalAs(expectType, null);
    }

    /**
     * Returns the principalAs.
     *
     * @param expectType The expected type, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    public <T> T getPrincipalAs(@Nullable Class<T> expectType, @Nullable T defaultValue) {
        return ObjectUtilsWraps.castAs(principal, expectType, defaultValue);
    }

    /**
     * Returns the credentialsAs.
     *
     * @param expectType The expected type, may be {@code null}
     *
     * @return The result of the operation
     */
    public <T> T getCredentialsAs(@Nullable Class<T> expectType) {
        return getCredentialsAs(expectType, null);
    }

    /**
     * Returns the credentialsAs.
     *
     * @param expectType The expected type, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    public <T> T getCredentialsAs(@Nullable Class<T> expectType, @Nullable T defaultValue) {
        return ObjectUtilsWraps.castAs(credentials, expectType, defaultValue);
    }

    /**
     * Returns the additiveAs.
     *
     * @param expectType The expected type, may be {@code null}
     *
     * @return The result of the operation
     */
    public <T> T getAdditiveAs(@Nullable Class<T> expectType) {
        return getAdditiveAs(expectType, null);
    }

    /**
     * Returns the additiveAs.
     *
     * @param expectType The expected type, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The result of the operation
     */
    public <T> T getAdditiveAs(@Nullable Class<T> expectType, @Nullable T defaultValue) {
        return ObjectUtilsWraps.castAs(additive, expectType, defaultValue);
    }
}
