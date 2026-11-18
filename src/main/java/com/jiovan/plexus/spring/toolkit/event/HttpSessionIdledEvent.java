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


import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.ApplicationEvent;
import com.jiovan.plexus.common.toolkit.util.ObjectUtilsWraps;


/**
 * Event when {@link jakarta.servlet.http.HttpSession} idled
 *
 * @author David Hsing
 */
@SuppressWarnings("unused")
public class HttpSessionIdledEvent extends ApplicationEvent {
    /**
     * Create an event for an HTTP session that has become idle.
     *
     * @param session The idle HTTP session
     */
    public HttpSessionIdledEvent(@Nonnull HttpSession session) {
        super(session);
    }

    /**
     * Return the idle HTTP session.
     *
     * @return The HTTP session that triggered this event
     */
    @Nonnull
    public HttpSession getHttpSession() {
        return ObjectUtilsWraps.castAs(super.getSource(), HttpSession.class);
    }
}
