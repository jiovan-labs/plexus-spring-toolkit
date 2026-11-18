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


import java.util.Properties;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.security.authentication.event.AuthenticationFailureProxyUntrustedEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import com.jiovan.plexus.common.toolkit.util.MapPlainWraps;
import lombok.Getter;


/**
 * {@link org.springframework.security.authentication.event.AuthenticationFailureProxyUntrustedEvent} with properties capable
 *
 * @author David Hsing
 *
 * @see org.springframework.security.authentication.event.AuthenticationFailureProxyUntrustedEvent
 */
@Getter
@SuppressWarnings("unused")
public class PropertiesAuthenticationFailureProxyUntrustedEvent extends AbstractPropertiesAuthenticationFailureEvent {
    /**
     * Create a PropertiesAuthenticationFailureProxyUntrustedEvent from the given source event.
     *
     * @param event The source event
     */
    public PropertiesAuthenticationFailureProxyUntrustedEvent(@Nonnull AuthenticationFailureProxyUntrustedEvent event) {
        super(event.getAuthentication(), event.getException());
    }

    /**
     * Create a PropertiesAuthenticationFailureProxyUntrustedEvent from the given source event with additional properties.
     *
     * @param event The source event
     * @param properties Additional properties to attach, may be {@code null}
     */
    public PropertiesAuthenticationFailureProxyUntrustedEvent(@Nonnull AuthenticationFailureProxyUntrustedEvent event, @Nullable Properties properties) {
        super(event.getAuthentication(), event.getException());
        MapPlainWraps.ifNotEmpty(properties, super.properties::putAll);
    }

    /**
     * Create a PropertiesAuthenticationFailureProxyUntrustedEvent for the given authentication.
     *
     * @param authentication The authentication object
     * @param exception The authentication exception
     */
	public PropertiesAuthenticationFailureProxyUntrustedEvent(@Nonnull Authentication authentication, @Nonnull AuthenticationException exception) {
		super(authentication, exception);
	}

    /**
     * Create a PropertiesAuthenticationFailureProxyUntrustedEvent with the given values and additional properties.
     *
     * @param authentication The authentication object
     * @param exception The authentication exception
     * @param properties Additional properties to attach, may be {@code null}
     */
    public PropertiesAuthenticationFailureProxyUntrustedEvent(@Nonnull Authentication authentication, @Nonnull AuthenticationException exception, @Nullable Properties properties) {
        super(authentication, exception);
        MapPlainWraps.ifNotEmpty(properties, super.properties::putAll);
    }
}
