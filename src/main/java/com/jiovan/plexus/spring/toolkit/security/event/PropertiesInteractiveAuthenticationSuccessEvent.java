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
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import com.jiovan.plexus.common.toolkit.util.MapPlainWraps;
import lombok.Getter;


/**
 * {@link org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent} with properties capable
 *
 * @author David Hsing
 *
 * @see org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent
 */
@Getter
@SuppressWarnings("unused")
public class PropertiesInteractiveAuthenticationSuccessEvent extends AbstractPropertiesAuthenticationEvent {
    /**
     * The class that generated the authentication event.
     */
    private final Class<?> generatedBy;

    /**
     * Create a PropertiesInteractiveAuthenticationSuccessEvent from the given source event.
     *
     * @param event The source event
     */
    public PropertiesInteractiveAuthenticationSuccessEvent(@Nonnull InteractiveAuthenticationSuccessEvent event) {
        super(event.getAuthentication());
        this.generatedBy = event.getGeneratedBy();
    }

    /**
     * Create a PropertiesInteractiveAuthenticationSuccessEvent from the given source event with additional properties.
     *
     * @param event The source event
     * @param properties Additional properties to attach, may be {@code null}
     */
    public PropertiesInteractiveAuthenticationSuccessEvent(@Nonnull InteractiveAuthenticationSuccessEvent event, @Nullable Properties properties) {
        super(event.getAuthentication());
        this.generatedBy = event.getGeneratedBy();
        MapPlainWraps.ifNotEmpty(properties, super.properties::putAll);
    }

    /**
     * Create a PropertiesInteractiveAuthenticationSuccessEvent for the given authentication.
     *
     * @param authentication The authentication object
     * @param generatedBy The class that generated the event
     */
    public PropertiesInteractiveAuthenticationSuccessEvent(@Nonnull Authentication authentication, @Nonnull Class<?> generatedBy) {
        super(authentication);
        this.generatedBy = generatedBy;
    }

    /**
     * Create a PropertiesInteractiveAuthenticationSuccessEvent with the given values and additional properties.
     *
     * @param authentication The authentication object
     * @param generatedBy The class that generated the event
     * @param properties Additional properties to attach, may be {@code null}
     */
    public PropertiesInteractiveAuthenticationSuccessEvent(@Nonnull Authentication authentication, @Nonnull Class<?> generatedBy, @Nullable Properties properties) {
        super(authentication);
        this.generatedBy = generatedBy;
        MapPlainWraps.ifNotEmpty(properties, super.properties::putAll);
    }
}
