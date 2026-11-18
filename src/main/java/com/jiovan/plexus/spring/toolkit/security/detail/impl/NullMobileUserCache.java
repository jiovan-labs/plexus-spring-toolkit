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

package com.jiovan.plexus.spring.toolkit.security.detail.impl;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import com.jiovan.plexus.spring.toolkit.security.detail.MobileUserCache;


/**
 * {@link com.jiovan.plexus.spring.toolkit.security.detail.MobileUserCache} that performing any caching
 *
 * @author David Hsing
 *
 * @see org.springframework.security.core.userdetails.cache.NullUserCache
 */
@SuppressWarnings("unused")
public class NullMobileUserCache implements MobileUserCache {
    /**
     * Returns the cached user details for the given mobile and dial code.
     *
     * @param mobile The mobile number
     * @param dial The dial code, may be {@code null}
     *
     * @return The cached user details, or {@code null}
     */
    @Override
    public UserDetails getUserFromCache(@Nonnull String mobile, @Nullable String dial) {
        return null;
    }

    /**
     * Store the given user details in the cache.
     *
     * @param details The user details
     */
    @Override
    public void putUserInCache(@Nonnull UserDetails details) {
    }

    /**
     * Remove the cached user details for the given mobile and dial code.
     *
     * @param mobile The mobile number
     * @param dial The dial code, may be {@code null}
     */
    @Override
    public void removeUserFromCache(@Nonnull String mobile, @Nullable String dial) {
    }
}
