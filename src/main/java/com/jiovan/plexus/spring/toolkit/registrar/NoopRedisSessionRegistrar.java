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

package com.jiovan.plexus.spring.toolkit.registrar;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.ConfigureRedisAction;


/**
 * Registrar of a {@link org.springframework.session.data.redis.config.ConfigureRedisAction} with no operations
 *
 * @author David Hsing
 *
 * @see "http://www.jianshu.com/p/5d6c7772ad87"
 */
public class NoopRedisSessionRegistrar {
    /**
     * Provide a no-op Redis configuration action so Spring Session does not change Redis settings.
     *
     * @return The no-op configuration action.
     */
    @Bean
    @ConditionalOnMissingBean
    @SuppressWarnings("SameReturnValue")
    public ConfigureRedisAction noopConfigureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }
}
