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


import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiovan.plexus.common.toolkit.validation.sequence.InsertUpdateSequence;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Abstract jpa entity for archiving history
 *
 * @author David Hsing
 */
@Getter
@Setter
@ToString
@SuppressWarnings("unused")
public abstract class AbstractArchivableEntity implements Serializable {
    @Column(name = "archive_id", nullable = false, updatable = false, length = 40)
    @NotBlank(groups = InsertUpdateSequence.class)
    @Size(max = 40)
    @JsonIgnore
    private String archiveId;

    @Column(name = "archive_type", nullable = false, updatable = false, length = 1)
    @NotBlank(groups = InsertUpdateSequence.class)
    @Size(max = 1)
    @JsonIgnore
    private String archiveType;

    @Column(name = "archived_by", nullable = false, updatable = false, length = 40)
    @NotBlank(groups = InsertUpdateSequence.class)
    @Size(max = 40)
    @JsonIgnore
    private String archivedBy;

    @Column(name = "archived_at", nullable = false, updatable = false)
    @NotNull(groups = InsertUpdateSequence.class)
    @JsonIgnore
    private LocalDateTime archivedAt;
}
