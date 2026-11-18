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

package com.jiovan.plexus.spring.toolkit.security.event;


import jakarta.annotation.Nonnull;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.Authentication;
import com.jiovan.plexus.common.toolkit.util.ObjectUtilsWraps;


/**
 * Event when the jwt authentication been created
 *
 * @author David Hsing
 *
 * @see com.jiovan.plexus.spring.toolkit.security.authentication.JwtAuthenticationToken
 */
@SuppressWarnings("unused")
public class JwtAuthenticationCreatedEvent extends ApplicationEvent {
    /**
     * Create a JwtAuthenticationCreatedEvent for the given authentication.
     *
     * @param authentication The authentication object
     */
    public JwtAuthenticationCreatedEvent(@Nonnull Authentication authentication) {
        super(authentication);
    }

    /**
     * Returns the authentication carried by this event.
     *
     * @return The authentication carried by this event
     */
    @Nonnull
    public Authentication getAuthentication() {
        return ObjectUtilsWraps.castAs(super.getSource(), Authentication.class);
    }
}
