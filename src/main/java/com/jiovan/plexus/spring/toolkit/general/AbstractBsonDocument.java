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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.util.Assert;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiovan.plexus.common.toolkit.constant.AssertMessageConst;
import com.jiovan.plexus.common.toolkit.util.LocalDateWraps;
import lombok.Getter;


/**
 * Abstract bson document with {@link org.bson.types.ObjectId}
 *
 * @author David Hsing
 *
 * @see org.springframework.data.mongodb.util.json.ParameterBindingDocumentCodec
 *
 * @see "https://www.jianshu.com/p/463d513cca6f"
 */
@Getter
@MappedSuperclass
@SuppressWarnings("unused")
public abstract class AbstractBsonDocument implements Serializable {
    /**
     * The MongoDB {@link ObjectId} that serves as the document identifier.
     */
    @Id
    @Column(name = "_id")
    @JsonIgnore
    private ObjectId objectId;

    /**
     * Set the document identifier.
     *
     * @param id The object ID, may be {@code null}
     */
    public void setObjectId(@Nullable ObjectId id) {
        objectId = id;
    }

    /**
     * Set the document identifier from a date.
     *
     * @param date The date to derive the ID from, may be {@code null}
     */
    public void setObjectId(@Nullable Date date) {
        objectId = (date == null) ? null : new ObjectId(date);
    }

    /**
     * Set the document identifier from a local date.
     *
     * @param date The date to derive the ID from, may be {@code null}
     */
    public void setObjectId(@Nullable LocalDate date) {
        setObjectId(LocalDateWraps.toJdkDate(date));
    }

    /**
     * Set the document identifier from a local date and time.
     *
     * @param dateTime The date and time to derive the ID from, may be {@code null}
     */
    public void setObjectId(@Nullable LocalDateTime dateTime) {
        setObjectId(LocalDateWraps.toJdkDate(dateTime));
    }

    /**
     * Set the document identifier from a timestamp in milliseconds.
     *
     * @param milliseconds The positive epoch timestamp
     */
    public void setObjectId(long milliseconds) {
        Assert.isTrue(milliseconds > 0L, AssertMessageConst.IS_POSITIVE);
        objectId = new ObjectId(new Date(milliseconds));
    }
}
