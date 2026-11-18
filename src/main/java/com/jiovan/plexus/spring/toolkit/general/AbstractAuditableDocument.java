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

package com.jiovan.plexus.spring.toolkit.general;


import java.time.LocalDateTime;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiovan.plexus.common.toolkit.annotation.BeanCopyIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Abstract bson document for Spring Data auditing
 *
 * @author David Hsing
 *
 * @see org.springframework.data.auditing.AuditingHandler
 */
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@Setter
@ToString(callSuper = true)
@SuppressWarnings("unused")
public abstract class AbstractAuditableDocument extends AbstractBsonDocument {
    /**
     * The user who created this document.
     */
    @Field(name = "created_by")
    @CreatedBy
    @JsonIgnore
    private String createdBy;

    /**
     * The time when this document was created.
     */
    @Field(name = "created_at")
    @CreatedDate
    @JsonIgnore
    private LocalDateTime createdAt;

    /**
     * The user who last updated this document.
     */
    @Field(name = "updated_by")
    @LastModifiedBy
    @JsonIgnore
    private String updatedBy;

    /**
     * The time when this document was last updated.
     */
    @Field(name = "updated_at")
    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime updatedAt;

    /**
     * Whether to apply auditing when creating this document.
     */
    @Transient
    @JsonIgnore
    @BeanCopyIgnore
    private Boolean auditCreating = Boolean.TRUE;

    /**
     * Whether to apply auditing when updating this document.
     */
    @Transient
    @JsonIgnore
    @BeanCopyIgnore
    private Boolean auditUpdating = Boolean.TRUE;
}
