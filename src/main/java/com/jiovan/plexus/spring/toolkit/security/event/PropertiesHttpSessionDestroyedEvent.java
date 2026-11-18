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
import jakarta.servlet.http.HttpSession;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import com.jiovan.plexus.common.toolkit.util.MapPlainWraps;
import lombok.Getter;


/**
 * {@link org.springframework.security.web.session.HttpSessionDestroyedEvent} with properties capable
 *
 * @author David Hsing
 *
 * @see org.springframework.security.web.session.HttpSessionDestroyedEvent
 */
@Getter
@SuppressWarnings("unused")
public class PropertiesHttpSessionDestroyedEvent extends HttpSessionDestroyedEvent {
    /**
     * Additional properties carried by this event.
     */
    private final Properties properties = new Properties();

    /**
     * Create a PropertiesHttpSessionDestroyedEvent from the given source event.
     *
     * @param event The source event
     */
    public PropertiesHttpSessionDestroyedEvent(@Nonnull HttpSessionDestroyedEvent event) {
        super(event.getSession());
    }

    /**
     * Create a PropertiesHttpSessionDestroyedEvent from the given source event with additional properties.
     *
     * @param event The source event
     * @param properties Additional properties to attach, may be {@code null}
     */
    public PropertiesHttpSessionDestroyedEvent(@Nonnull HttpSessionDestroyedEvent event, @Nullable Properties properties) {
        super(event.getSession());
        MapPlainWraps.ifNotEmpty(properties, this.properties::putAll);
    }

    /**
     * Create a PropertiesHttpSessionDestroyedEvent.
     *
     * @param session The HTTP session
     */
    public PropertiesHttpSessionDestroyedEvent(@Nonnull HttpSession session) {
        super(session);
    }

    /**
     * Create a PropertiesHttpSessionDestroyedEvent with the given values and additional properties.
     *
     * @param session The HTTP session
     * @param properties Additional properties to attach, may be {@code null}
     */
    public PropertiesHttpSessionDestroyedEvent(@Nonnull HttpSession session, @Nullable Properties properties) {
        super(session);
        MapPlainWraps.ifNotEmpty(properties, this.properties::putAll);
    }
}
