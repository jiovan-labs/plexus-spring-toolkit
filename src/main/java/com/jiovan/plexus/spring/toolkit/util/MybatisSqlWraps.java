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

package com.jiovan.plexus.spring.toolkit.util;


import java.util.HashMap;
import java.util.Map;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.util.CollectionUtils;
import com.github.pagehelper.PageRowBounds;
import com.jiovan.plexus.common.toolkit.constant.CharVariantConst;
import com.jiovan.plexus.common.toolkit.util.MapPlainWraps;
import com.jiovan.plexus.spring.toolkit.constant.MybatisSqlConst;


/**
 * Utilities for mybatis SQL
 *
 * @author David Hsing
 *
 * @see "com.github.pagehelper.util.PageObjectUtil"
 *
 * @see "https://github.com/pagehelper/Mybatis-PageHelper"
 * @see "https://www.cnblogs.com/jinit/p/14841966.html"
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public abstract class MybatisSqlWraps {
    /**
     * Build row bounds from the paging parameters in the given map.
     *
     * @param params The parameters, may be {@code null}
     * @param offsetParam The given offsetParam
     * @param limitParam The given limitParam
     *
     * @return The constructed row bounds, or {@code null}
     */
    @Nonnull
    public static RowBounds getRowBounds(@Nullable Map<String, Object> params, @Nullable String offsetParam, @Nullable String limitParam) {
        return getRowBounds(params, offsetParam, limitParam, null, null);
    }

    /**
     * Build row bounds from the paging parameters in the given map.
     *
     * @param params The parameters, may be {@code null}
     * @param offsetParam The given offsetParam
     * @param limitParam The given limitParam
     * @param defaultOffset The given defaultOffset
     * @param defaultLimit The given defaultLimit
     *
     * @return The constructed row bounds, or {@code null}
     */
    @Nonnull
    public static RowBounds getRowBounds(@Nullable Map<String, Object> params, @Nullable String offsetParam, @Nullable String limitParam, @Nullable Integer defaultOffset, @Nullable Integer defaultLimit) {
        if (CollectionUtils.isEmpty(params)) {
            return RowBounds.DEFAULT;
        }
        int offset = Math.max(0, MapPlainWraps.getInteger(params, offsetParam, ObjectUtils.getIfNull(defaultOffset, RowBounds.NO_ROW_OFFSET)));
        int limit = Math.max(0, MapPlainWraps.getInteger(params, limitParam, ObjectUtils.getIfNull(defaultLimit, RowBounds.NO_ROW_LIMIT)));
        return new RowBounds(offset, limit);
    }

    /**
     * Build row bounds from the paging parameters in the given map.
     *
     * @param params The parameters, may be {@code null}
     * @param offsetParam The given offsetParam
     * @param limitParam The given limitParam
     * @param count The given count
     *
     * @return The constructed row bounds, or {@code null}
     */
    @Nonnull
    public static PageRowBounds getRowBounds(@Nullable Map<String, Object> params, @Nullable String offsetParam, @Nullable String limitParam, boolean count) {
        return getRowBounds(params, offsetParam, limitParam, null, null, count);
    }

    /**
     * Build row bounds from the paging parameters in the given map.
     *
     * @param params The parameters, may be {@code null}
     * @param offsetParam The given offsetParam
     * @param limitParam The given limitParam
     * @param defaultOffset The given defaultOffset
     * @param defaultLimit The given defaultLimit
     * @param count The given count
     *
     * @return The constructed row bounds, or {@code null}
     */
    @Nonnull
    public static PageRowBounds getRowBounds(@Nullable Map<String, Object> params, @Nullable String offsetParam, @Nullable String limitParam, @Nullable Integer defaultOffset, @Nullable Integer defaultLimit, boolean count) {
        RowBounds bounds = getRowBounds(params, offsetParam, limitParam, defaultOffset, defaultLimit);
        PageRowBounds result = new PageRowBounds(bounds.getOffset(), bounds.getLimit());
        result.setCount(count);
        return result;
    }

    /**
     * Build row bounds with the given offset and limit.
     *
     * @return The constructed row bounds, or {@code null}
     */
    @Nonnull
    public static PageRowBounds ofRowBounds() {
        return ofRowBounds(RowBounds.NO_ROW_OFFSET, 10, true);
    }

    /**
     * Build row bounds with the given offset and limit.
     *
     * @param count The given count
     *
     * @return The constructed row bounds, or {@code null}
     */
    @Nonnull
    public static PageRowBounds ofRowBounds(boolean count) {
        return ofRowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT, count);
    }

    /**
     * Build row bounds with the given offset and limit.
     *
     * @param offset The offset
     * @param limit The limit
     * @param count The given count
     *
     * @return The constructed row bounds, or {@code null}
     */
    @Nonnull
    public static PageRowBounds ofRowBounds(int offset, int limit, boolean count) {
        PageRowBounds result = new PageRowBounds(Math.max(0, offset), Math.max(0, limit));
        result.setCount(count);
        return result;
    }

    /**
     * Disable pagination by removing the paging parameters from the map.
     *
     * @param params The parameters, may be {@code null}
     */
    public static Map<String, Object> disablePagination(@Nullable Map<String, Object> params) {
        return disablePagination(params, true);
    }

    /**
     * Disable pagination by removing the paging parameters from the map.
     *
     * @param params The parameters, may be {@code null}
     * @param newIfNull The given newIfNull
     */
    public static Map<String, Object> disablePagination(@Nullable Map<String, Object> params, boolean newIfNull) {
        if (params == null) {
            return !newIfNull ? null : MapPlainWraps.newHashMapWithin(MybatisSqlConst.PAGE_SIZE_ZERO, true);
        }
        try {
            params.put(MybatisSqlConst.PAGE_SIZE_ZERO, true);
            return params;
        } catch (Exception ignored) {
            return new HashMap<>(params);
        }
    }

    /**
     * Extract the SQL statement with the given id from the mapped statements.
     *
     * @param factory The factory, may be {@code null}
     * @param statementId The mapped statement id
     * @param parameter The given parameter
     *
     * @return The SQL text, or {@code null}
     */
    @Nullable
    public static String extractSqlById(@Nullable SqlSessionFactory factory, @Nullable String statementId, @Nullable Object parameter) {
        if (factory == null || StringUtils.isBlank(statementId)) {
            return null;
        }
        Configuration configuration = factory.getConfiguration();
        MappedStatement statement = configuration.getMappedStatement(statementId);
        if (statement == null) {
            return null;
        }
        BoundSql boundSql = statement.getBoundSql(parameter);
        return (boundSql == null) ? null : boundSql.getSql();
    }

    /**
     * Extract the SQL statement with the given name from the mapped statements.
     *
     * @param factory The factory, may be {@code null}
     * @param mapper The given mapper
     * @param method The method, may be {@code null}
     * @param parameter The given parameter
     *
     * @return The SQL text, or {@code null}
     */
    @Nullable
    public static String extractSqlByName(@Nullable SqlSessionFactory factory, @Nullable Class<?> mapper, String method, @Nullable Object parameter) {
        if (ObjectUtils.anyNull(factory, mapper)) {
            return null;
        }
        String statementId = StringUtils.join(mapper.getName(), CharVariantConst.DOT, method);
        return extractSqlById(factory, statementId, parameter);
    }
}
