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


import java.util.Collection;
import java.util.Map;
import java.util.Set;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import com.jiovan.plexus.common.toolkit.util.ArrayUtilsWraps;
import com.jiovan.plexus.common.toolkit.util.BeanUtilsWraps;
import com.jiovan.plexus.common.toolkit.util.CollectionPlainWraps;
import com.jiovan.plexus.common.toolkit.util.MapPlainWraps;
import com.jiovan.plexus.spring.toolkit.constant.BeanCopierConst;


/**
 * Utilities for populating request parameters
 *
 * @author David Hsing
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class RequestPopulateWraps {
    /**
     * Populate context request parameters to bean.
     *
     * @param bean The given bean
     */
    public static void populateContextRequestParametersToBean(@Nullable Object bean) {
        populateContextRequestParametersToBean(bean, false, false, false, (Collection<String>) null);
    }

    /**
     * Populate context request parameters to bean.
     *
     * @param bean The given bean
     * @param emptyAsNull The given emptyAsNull
     */
    public static void populateContextRequestParametersToBean(@Nullable Object bean, boolean emptyAsNull) {
        populateContextRequestParametersToBean(bean, emptyAsNull, false, false, (Collection<String>) null);
    }

    /**
     * Populate context request parameters to bean.
     *
     * @param bean The given bean
     * @param emptyAsNull The given emptyAsNull
     * @param includePayload The given includePayload
     */
    public static void populateContextRequestParametersToBean(@Nullable Object bean, boolean emptyAsNull, boolean includePayload) {
        populateContextRequestParametersToBean(bean, emptyAsNull, includePayload, false, (Collection<String>) null);
    }

    /**
     * Populate context request parameters to bean.
     *
     * @param bean The given bean
     * @param emptyAsNull The given emptyAsNull
     * @param includePayload The given includePayload
     * @param unwrapSingleArray The given unwrapSingleArray
     */
    public static void populateContextRequestParametersToBean(@Nullable Object bean, boolean emptyAsNull, boolean includePayload, boolean unwrapSingleArray) {
        populateContextRequestParametersToBean(bean, emptyAsNull, includePayload, unwrapSingleArray, (Collection<String>) null);
    }

    /**
     * Populate context request parameters to bean.
     *
     * @param bean The given bean
     * @param emptyAsNull The given emptyAsNull
     * @param includePayload The given includePayload
     * @param unwrapSingleArray The given unwrapSingleArray
     * @param ignoredFields The given ignoredFields
     */
    public static void populateContextRequestParametersToBean(@Nullable Object bean, boolean emptyAsNull, boolean includePayload, boolean unwrapSingleArray, @Nullable String... ignoredFields) {
        populateContextRequestParametersToBean(bean, emptyAsNull, includePayload, unwrapSingleArray, ArrayUtilsWraps.asList(ignoredFields));
    }

    /**
     * Populate context request parameters to bean.
     *
     * @param bean The given bean
     * @param emptyAsNull The given emptyAsNull
     * @param includePayload The given includePayload
     * @param unwrapSingleArray The given unwrapSingleArray
     * @param ignoredFields The given ignoredFields
     */
    public static void populateContextRequestParametersToBean(@Nullable Object bean, boolean emptyAsNull, boolean includePayload, boolean unwrapSingleArray, @Nullable Collection<String> ignoredFields) {
        populateRequestParametersToBean(bean, WebUtilsWraps.getContextServletRequest(), emptyAsNull, includePayload, unwrapSingleArray, ignoredFields);
    }

    /**
     * Populate context request parameters to bean sheared.
     *
     * @param bean The given bean
     */
    public static void populateContextRequestParametersToBeanSheared(@Nullable Object bean) {
        populateContextRequestParametersToBeanSheared(bean, false, false, false, (Collection<String>) null);
    }

    /**
     * Populate context request parameters to bean sheared.
     *
     * @param bean The given bean
     * @param emptyAsNull The given emptyAsNull
     */
    public static void populateContextRequestParametersToBeanSheared(@Nullable Object bean, boolean emptyAsNull) {
        populateContextRequestParametersToBeanSheared(bean, emptyAsNull, false, false, (Collection<String>) null);
    }

    /**
     * Populate context request parameters to bean sheared.
     *
     * @param bean The given bean
     * @param emptyAsNull The given emptyAsNull
     * @param includePayload The given includePayload
     */
    public static void populateContextRequestParametersToBeanSheared(@Nullable Object bean, boolean emptyAsNull, boolean includePayload) {
        populateContextRequestParametersToBeanSheared(bean, emptyAsNull, includePayload, false, (Collection<String>) null);
    }

    /**
     * Populate context request parameters to bean sheared.
     *
     * @param bean The given bean
     * @param emptyAsNull The given emptyAsNull
     * @param includePayload The given includePayload
     * @param unwrapSingleArray The given unwrapSingleArray
     */
    public static void populateContextRequestParametersToBeanSheared(@Nullable Object bean, boolean emptyAsNull, boolean includePayload, boolean unwrapSingleArray) {
        populateContextRequestParametersToBeanSheared(bean, emptyAsNull, includePayload, unwrapSingleArray, (Collection<String>) null);
    }

    /**
     * Populate context request parameters to bean sheared.
     *
     * @param bean The given bean
     * @param emptyAsNull The given emptyAsNull
     * @param includePayload The given includePayload
     * @param unwrapSingleArray The given unwrapSingleArray
     * @param ignoredFields The given ignoredFields
     */
    public static void populateContextRequestParametersToBeanSheared(@Nullable Object bean, boolean emptyAsNull, boolean includePayload, boolean unwrapSingleArray, @Nullable String... ignoredFields) {
        populateContextRequestParametersToBeanSheared(bean, emptyAsNull, includePayload, unwrapSingleArray, ArrayUtilsWraps.asList(ignoredFields));
    }

    /**
     * Populate context request parameters to bean sheared.
     *
     * @param bean The given bean
     * @param emptyAsNull The given emptyAsNull
     * @param includePayload The given includePayload
     * @param unwrapSingleArray The given unwrapSingleArray
     * @param ignoredFields The given ignoredFields
     */
    public static void populateContextRequestParametersToBeanSheared(@Nullable Object bean, boolean emptyAsNull, boolean includePayload, boolean unwrapSingleArray, @Nullable Collection<String> ignoredFields) {
        populateRequestParametersToBeanSheared(bean, WebUtilsWraps.getContextServletRequest(), emptyAsNull, includePayload, unwrapSingleArray, ignoredFields);
    }

    /**
     * Populate parameters to bean.
     *
     * @param bean The given bean
     * @param params The parameters, may be {@code null}
     */
    public static void populateParametersToBean(@Nullable Object bean, @Nullable Map<String, Object> params) {
        populateParametersToBean(bean, params, (Collection<String>) null);
    }

    /**
     * Populate parameters to bean.
     *
     * @param bean The given bean
     * @param params The parameters, may be {@code null}
     * @param ignoredFields The given ignoredFields
     */
    public static void populateParametersToBean(@Nullable Object bean, @Nullable Map<String, Object> params, @Nullable String... ignoredFields) {
        populateParametersToBean(bean, params, ArrayUtilsWraps.asList(ignoredFields));
    }

    /**
     * Populate parameters to bean.
     *
     * @param bean The given bean
     * @param params The parameters, may be {@code null}
     * @param ignoredFields The given ignoredFields
     */
    public static void populateParametersToBean(@Nullable Object bean, @Nullable Map<String, Object> params, @Nullable Collection<String> ignoredFields) {
        if (bean == null || MapPlainWraps.isEmpty(params)) {
            return;
        }
        Map<String, Object> alias = MapPlainWraps.newHashMapIfNull(params);
        if (CollectionPlainWraps.isNotEmpty(ignoredFields)) {
            ignoredFields.forEach(alias::remove);
        }
        BeanUtilsWraps.mapToBeanQuietly(bean, alias);
    }

    /**
     * Populate parameters to bean sheared.
     *
     * @param bean The given bean
     * @param params The parameters, may be {@code null}
     */
    public static void populateParametersToBeanSheared(@Nullable Object bean, @Nullable Map<String, Object> params) {
        populateParametersToBeanSheared(bean, params, (Collection<String>) null);
    }

    /**
     * Populate parameters to bean sheared.
     *
     * @param bean The given bean
     * @param params The parameters, may be {@code null}
     * @param ignoredFields The given ignoredFields
     */
    public static void populateParametersToBeanSheared(@Nullable Object bean, @Nullable Map<String, Object> params, @Nullable String... ignoredFields) {
        populateParametersToBeanSheared(bean, params, ArrayUtilsWraps.asList(ignoredFields));
    }

    /**
     * Populate parameters to bean sheared.
     *
     * @param bean The given bean
     * @param params The parameters, may be {@code null}
     * @param ignoredFields The given ignoredFields
     */
    public static void populateParametersToBeanSheared(@Nullable Object bean, @Nullable Map<String, Object> params, @Nullable Collection<String> ignoredFields) {
        if (bean == null || MapPlainWraps.isEmpty(params)) {
            return;
        }
        Set<String> ignores = CollectionPlainWraps.newHashSetIfNull(ignoredFields);
        CollectionPlainWraps.addAllIfNotBlank(ignores, ReflectionUtilsWraps.getFieldNamesWithAnyAnnotationsToSet(bean.getClass(), BeanCopierConst.IGNORABLE_ANNOTATIONS));
        populateParametersToBean(bean, params, ignores);
    }

    /**
     * Populate request parameters to bean.
     *
     * @param bean The given bean
     * @param request The HTTP request, may be {@code null}
     */
    public static void populateRequestParametersToBean(@Nullable Object bean, @Nullable HttpServletRequest request) {
        populateRequestParametersToBean(bean, request, false, false, false, (Collection<String>) null);
    }

    /**
     * Populate request parameters to bean.
     *
     * @param bean The given bean
     * @param request The HTTP request, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     */
    public static void populateRequestParametersToBean(@Nullable Object bean, @Nullable HttpServletRequest request, boolean emptyAsNull) {
        populateRequestParametersToBean(bean, request, emptyAsNull, false, false, (Collection<String>) null);
    }

    /**
     * Populate request parameters to bean.
     *
     * @param bean The given bean
     * @param request The HTTP request, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     * @param includePayload The given includePayload
     */
    public static void populateRequestParametersToBean(@Nullable Object bean, @Nullable HttpServletRequest request, boolean emptyAsNull, boolean includePayload) {
        populateRequestParametersToBean(bean, request, emptyAsNull, includePayload, false, (Collection<String>) null);
    }

    /**
     * Populate request parameters to bean.
     *
     * @param bean The given bean
     * @param request The HTTP request, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     * @param includePayload The given includePayload
     * @param unwrapSingleArray The given unwrapSingleArray
     */
    public static void populateRequestParametersToBean(@Nullable Object bean, @Nullable HttpServletRequest request, boolean emptyAsNull, boolean includePayload, boolean unwrapSingleArray) {
        populateRequestParametersToBean(bean, request, emptyAsNull, includePayload, unwrapSingleArray, (Collection<String>) null);
    }

    /**
     * Populate request parameters to bean.
     *
     * @param bean The given bean
     * @param request The HTTP request, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     * @param includePayload The given includePayload
     * @param unwrapSingleArray The given unwrapSingleArray
     * @param ignoredFields The given ignoredFields
     */
    public static void populateRequestParametersToBean(@Nullable Object bean, @Nullable HttpServletRequest request, boolean emptyAsNull, boolean includePayload, boolean unwrapSingleArray, @Nullable String... ignoredFields) {
        populateRequestParametersToBean(bean, request, emptyAsNull, includePayload, unwrapSingleArray, ArrayUtilsWraps.asList(ignoredFields));
    }

    /**
     * Populate request parameters to bean.
     *
     * @param bean The given bean
     * @param request The HTTP request, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     * @param includePayload The given includePayload
     * @param unwrapSingleArray The given unwrapSingleArray
     * @param ignoredFields The given ignoredFields
     */
    public static void populateRequestParametersToBean(@Nullable Object bean, @Nullable HttpServletRequest request, boolean emptyAsNull, boolean includePayload, boolean unwrapSingleArray, @Nullable Collection<String> ignoredFields) {
        if (ObjectUtils.anyNull(bean, request)) {
            return;
        }
        Map<String, Object> map = RequestParamWraps.getParameterObjectMap(request, emptyAsNull, includePayload, unwrapSingleArray);
        if (MapPlainWraps.isEmpty(map)) {
            return;
        }
        if (CollectionPlainWraps.isNotEmpty(ignoredFields)) {
            ignoredFields.forEach(map::remove);
        }
        BeanUtilsWraps.mapToBeanQuietly(bean, map);
    }

    /**
     * Populate request parameters to bean sheared.
     *
     * @param bean The given bean
     * @param request The HTTP request, may be {@code null}
     */
    public static void populateRequestParametersToBeanSheared(@Nullable Object bean, @Nullable HttpServletRequest request) {
        populateRequestParametersToBeanSheared(bean, request, false, false, false, (Collection<String>) null);
    }

    /**
     * Populate request parameters to bean sheared.
     *
     * @param bean The given bean
     * @param request The HTTP request, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     */
    public static void populateRequestParametersToBeanSheared(@Nullable Object bean, @Nullable HttpServletRequest request, boolean emptyAsNull) {
        populateRequestParametersToBeanSheared(bean, request, emptyAsNull, false, false, (Collection<String>) null);
    }

    /**
     * Populate request parameters to bean sheared.
     *
     * @param bean The given bean
     * @param request The HTTP request, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     * @param includePayload The given includePayload
     */
    public static void populateRequestParametersToBeanSheared(@Nullable Object bean, @Nullable HttpServletRequest request, boolean emptyAsNull, boolean includePayload) {
        populateRequestParametersToBeanSheared(bean, request, emptyAsNull, includePayload, false, (Collection<String>) null);
    }

    /**
     * Populate request parameters to bean sheared.
     *
     * @param bean The given bean
     * @param request The HTTP request, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     * @param includePayload The given includePayload
     * @param unwrapSingleArray The given unwrapSingleArray
     */
    public static void populateRequestParametersToBeanSheared(@Nullable Object bean, @Nullable HttpServletRequest request, boolean emptyAsNull, boolean includePayload, boolean unwrapSingleArray) {
        populateRequestParametersToBeanSheared(bean, request, emptyAsNull, includePayload, unwrapSingleArray, (Collection<String>) null);
    }

    /**
     * Populate request parameters to bean sheared.
     *
     * @param bean The given bean
     * @param request The HTTP request, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     * @param includePayload The given includePayload
     * @param unwrapSingleArray The given unwrapSingleArray
     * @param ignoredFields The given ignoredFields
     */
    public static void populateRequestParametersToBeanSheared(@Nullable Object bean, @Nullable HttpServletRequest request, boolean emptyAsNull, boolean includePayload, boolean unwrapSingleArray, @Nullable String... ignoredFields) {
        populateRequestParametersToBeanSheared(bean, request, emptyAsNull, includePayload, unwrapSingleArray, ArrayUtilsWraps.asList(ignoredFields));
    }

    /**
     * Populate request parameters to bean sheared.
     *
     * @param bean The given bean
     * @param request The HTTP request, may be {@code null}
     * @param emptyAsNull The given emptyAsNull
     * @param includePayload The given includePayload
     * @param unwrapSingleArray The given unwrapSingleArray
     * @param ignoredFields The given ignoredFields
     */
    public static void populateRequestParametersToBeanSheared(@Nullable Object bean, @Nullable HttpServletRequest request, boolean emptyAsNull, boolean includePayload, boolean unwrapSingleArray, @Nullable Collection<String> ignoredFields) {
        if (ObjectUtils.anyNull(bean, request)) {
            return;
        }
        Set<String> ignores = CollectionPlainWraps.newHashSetIfNull(ignoredFields);
        CollectionPlainWraps.addAllIfNotBlank(ignores, ReflectionUtilsWraps.getFieldNamesWithAnyAnnotationsToSet(bean.getClass(), BeanCopierConst.IGNORABLE_ANNOTATIONS));
        populateRequestParametersToBean(bean, request, emptyAsNull, includePayload, unwrapSingleArray, ignores);
    }
}
