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

package com.jiovan.plexus.spring.toolkit.audit;


import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.auditing.IsNewAwareAuditingHandler;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mapping.context.PersistentEntities;
import com.jiovan.plexus.spring.toolkit.general.AbstractAuditableDocument;


/**
 * {@link org.springframework.data.auditing.AuditingHandler} for mongo document
 *
 * @author David Hsing
 *
 * @see org.springframework.data.auditing.IsNewAwareAuditingHandler
 */
@SuppressWarnings("unused")
public class MongoDataAuditHandler extends IsNewAwareAuditingHandler {
    /**
     * Create a handler from the given mapping context.
     *
     * @param context The mapping context used to resolve persistent entities
     */
    public MongoDataAuditHandler(@Nonnull MappingContext<? extends PersistentEntity<?, ?>, ? extends PersistentProperty<?>> context) {
        this(PersistentEntities.of(context));
    }

    /**
     * Create a handler from the given persistent entities.
     *
     * @param entities The persistent entities used for auditing
     */
    public MongoDataAuditHandler(@Nonnull PersistentEntities entities) {
        super(entities);
    }

    /**
     * Create a handler from the given persistent entities and auditor provider.
     *
     * @param entities The persistent entities used for auditing
     * @param auditor The auditor provider used to resolve the current auditor
     */
    public MongoDataAuditHandler(@Nonnull PersistentEntities entities, @Nonnull AuditorAware<?> auditor) {
        super(entities);
        super.setAuditorAware(auditor);
    }

    /**
     * Mark the given source as created when auditing is enabled for the auditable document.
     *
     * @param <T> The source type
     * @param source The source to mark
     *
     * @return The marked source, or the original source when auditing is not enabled
     */
    @Nonnull
    @Override
    public <T> T markCreated(@Nonnull T source) {
        return (source instanceof AbstractAuditableDocument alias && BooleanUtils.isTrue(alias.getAuditCreating())) ? super.markCreated(source) : source;
    }

    /**
     * Mark the given source as modified when auditing is enabled for the auditable document.
     *
     * @param <T> The source type
     * @param source The source to mark
     *
     * @return The marked source, or the original source when auditing is not enabled
     */
    @Nonnull
    @Override
    public <T> T markModified(@Nonnull T source) {
        return (source instanceof AbstractAuditableDocument alias && BooleanUtils.isTrue(alias.getAuditCreating())) ? super.markModified(source) : source;
    }
}
