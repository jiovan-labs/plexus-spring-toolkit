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
import org.springframework.security.core.GrantedAuthority;
import lombok.Getter;


/**
 * {@link org.springframework.security.core.Authentication} token for mobile with captcha
 *
 * @author David Hsing
 *
 * @see org.springframework.security.authentication.AbstractAuthenticationToken
 */
@Getter
@SuppressWarnings("unused")
public class MobileCaptchaAuthenticationToken extends AbstractAdditiveAuthenticationToken {
    /**
     * Create a MobileCaptchaAuthenticationToken.
     *
     * @param principal The given principal
     */
    public MobileCaptchaAuthenticationToken(@Nonnull Object principal) {
        this(principal, null, null);
    }

    /**
     * Create a MobileCaptchaAuthenticationToken.
     *
     * @param principal The given principal
     * @param credentials The given credentials
     */
    public MobileCaptchaAuthenticationToken(@Nonnull Object principal, @Nullable Object credentials) {
        this(principal, credentials, null);
    }

    /**
     * Create a MobileCaptchaAuthenticationToken.
     *
     * @param principal The given principal
     * @param credentials The given credentials
     * @param authorities The given authorities
     */
    public MobileCaptchaAuthenticationToken(@Nonnull Object principal, @Nullable Object credentials, @Nullable Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
