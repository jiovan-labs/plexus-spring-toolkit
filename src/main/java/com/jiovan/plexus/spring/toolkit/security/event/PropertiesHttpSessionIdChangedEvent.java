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
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.session.HttpSessionIdChangedEvent;
import com.jiovan.plexus.common.toolkit.util.MapPlainWraps;
import com.jiovan.plexus.common.toolkit.util.ObjectUtilsWraps;
import lombok.Getter;


/**
 * {@link org.springframework.security.web.session.HttpSessionIdChangedEvent} with properties capable
 *
 * @author David Hsing
 *
 * @see org.springframework.security.web.session.HttpSessionIdChangedEvent
 */
@Getter
@SuppressWarnings("unused")
public class PropertiesHttpSessionIdChangedEvent extends HttpSessionIdChangedEvent {
    /**
     * Additional properties carried by this event.
     */
    private final Properties properties = new Properties();

    /**
     * Create a PropertiesHttpSessionIdChangedEvent from the given source event.
     *
     * @param event The source event
     */
    public PropertiesHttpSessionIdChangedEvent(@Nonnull HttpSessionIdChangedEvent event) {
        super(ObjectUtilsWraps.castAs(event.getSource(), HttpSession.class), event.getOldSessionId());
    }

    /**
     * Create a PropertiesHttpSessionIdChangedEvent from the given source event with additional properties.
     *
     * @param event The source event
     * @param properties Additional properties to attach, may be {@code null}
     */
    public PropertiesHttpSessionIdChangedEvent(@Nonnull HttpSessionIdChangedEvent event, @Nullable Properties properties) {
        super(ObjectUtilsWraps.castAs(event.getSource(), HttpSession.class), event.getOldSessionId());
        MapPlainWraps.ifNotEmpty(properties, this.properties::putAll);
    }

    /**
     * Create a PropertiesHttpSessionIdChangedEvent.
     *
     * @param session The HTTP session
     * @param previousId The previous session id, may be {@code null}
     */
    public PropertiesHttpSessionIdChangedEvent(@Nonnull HttpSession session, @Nullable String previousId) {
        super(session, StringUtils.defaultString(previousId));
    }

    /**
     * Create a PropertiesHttpSessionIdChangedEvent with the given values and additional properties.
     *
     * @param session The HTTP session
     * @param previousId The previous session id, may be {@code null}
     * @param properties Additional properties to attach, may be {@code null}
     */
    public PropertiesHttpSessionIdChangedEvent(@Nonnull HttpSession session, @Nullable String previousId, @Nullable Properties properties) {
        super(session, StringUtils.defaultString(previousId));
        MapPlainWraps.ifNotEmpty(properties, this.properties::putAll);
    }
}
