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

package com.jiovan.plexus.spring.toolkit.event;


import java.util.Map;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.ApplicationEvent;
import com.jiovan.plexus.common.toolkit.util.ObjectUtilsWraps;
import lombok.Getter;


/**
 * Event when {@link jakarta.servlet.http.HttpSession} saved
 *
 * @author David Hsing
 */
@SuppressWarnings("unused")
public class HttpSessionSavedEvent extends ApplicationEvent {
    @Getter
    private final Map<String, Object> changedAttributes;

    /**
     * Create an event for the given HTTP session.
     *
     * @param session The saved HTTP session
     */
    public HttpSessionSavedEvent(@Nonnull HttpSession session) {
        this(session, null);
    }

    /**
     * Create an event for the given HTTP session and its changed attributes.
     *
     * @param session The saved HTTP session
     * @param changedAttributes The attributes whose values changed, may be {@code null}
     */
    public HttpSessionSavedEvent(@Nonnull HttpSession session, @Nullable Map<String, Object> changedAttributes) {
        super(session);
        this.changedAttributes = changedAttributes;
    }

    /**
     * Return the saved HTTP session.
     *
     * @return The HTTP session that triggered this event
     */
    @Nonnull
    public HttpSession getHttpSession() {
        return ObjectUtilsWraps.castAs(super.getSource(), HttpSession.class);
    }
}
