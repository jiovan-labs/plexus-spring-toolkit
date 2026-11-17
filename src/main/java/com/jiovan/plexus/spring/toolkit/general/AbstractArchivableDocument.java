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
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Abstract bson document for archiving history
 *
 * @author David Hsing
 */
@Getter
@Setter
@ToString(callSuper = true)
@SuppressWarnings("unused")
public abstract class AbstractArchivableDocument extends AbstractBsonDocument {
    @Field(name = "archive_id")
    @JsonIgnore
    private String archiveId;

    @Field(name = "archive_type")
    @JsonIgnore
    private String archiveType;

    @Field(name = "archived_by")
    @JsonIgnore
    private String archivedBy;

    @Field(name = "archived_at")
    @JsonIgnore
    private LocalDateTime archivedAt;
}
