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


import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import com.jiovan.plexus.common.toolkit.annotation.BeanCopyIgnore;
import com.jiovan.plexus.common.toolkit.annotation.ViewTransferIgnore;
import com.jiovan.plexus.common.toolkit.util.ArrayUtilsWraps;
import com.jiovan.plexus.common.toolkit.util.CollectionPlainWraps;
import com.jiovan.plexus.common.toolkit.util.MapPlainWraps;
import com.jiovan.plexus.common.toolkit.util.PropertyPlainWraps;
import com.jiovan.plexus.spring.toolkit.constant.BeanCopierConst;


/**
 * Utilities for {@link org.springframework.beans.BeanUtils}
 *
 * @author David Hsing
 *
 * @see org.springframework.beans.BeanUtils
 * @see org.springframework.cglib.beans.BeanMap
 * @see org.springframework.cglib.beans.BeanCopier
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class BeanUtilsWraps {
    /**
     * Copy properties from the source object to the target object.
     *
     * @param source The source object to read from, may be {@code null}
     * @param target The target object to write to, may be {@code null}
     * @param excludes The field names to exclude, may be {@code null}
     *
     * @throws BeansException If the operation fails
     */
    public static void copyProperties(@Nullable Object source, @Nullable Object target, @Nullable Collection<String> excludes) throws BeansException {
        copyProperties(source, target, true, excludes);
    }

    /**
     * Copy properties from the source object to the target object.
     *
     * @param source The source object to read from, may be {@code null}
     * @param target The target object to write to, may be {@code null}
     * @param exclude Whether {@code fields} are exclusions rather than inclusions
     * @param fields The field names, may be {@code null}
     *
     * @throws BeansException If the operation fails
     */
    public static void copyProperties(@Nullable Object source, @Nullable Object target, boolean exclude, @Nullable String... fields) throws BeansException {
        copyProperties(source, target, exclude, ArrayUtilsWraps.asList(fields));
    }

    /**
     * Copy properties from the source object to the target object.
     *
     * @param source The source object to read from, may be {@code null}
     * @param target The target object to write to, may be {@code null}
     * @param exclude Whether {@code fields} are exclusions rather than inclusions
     * @param fields The field names, may be {@code null}
     *
     * @throws BeansException If the operation fails
     */
    @SuppressWarnings("DataFlowIssue")
    public static void copyProperties(@Nullable Object source, @Nullable Object target, boolean exclude, @Nullable Collection<String> fields) throws BeansException {
        if (source == null || target == null || (!exclude && CollectionUtils.isEmpty(fields))) {
            return;
        }
        String[] ignores;
        if (exclude) {
            ignores = (fields == null) ? null : fields.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
        } else {
            Set<String> nested = ReflectionUtilsWraps.getNestedFieldNamesToSet(target.getClass());
            if (CollectionUtils.isEmpty(nested)) {
                return;
            }
            ignores = nested.stream().filter(item -> !CollectionPlainWraps.containsString(fields, item)).toArray(String[]::new);
        }
        BeanUtils.copyProperties(source, target, ignores);
    }

    /**
     * Copy properties from source to target, ignoring failures.
     *
     * @param source The source object to read from, may be {@code null}
     * @param target The target object to write to, may be {@code null}
     * @param excludes The field names to exclude, may be {@code null}
     */
    @SuppressWarnings("DataFlowIssue")
    public static void copyPropertiesQuietly(@Nullable Object source, @Nullable Object target, @Nullable String... excludes) {
        if (source == null || target == null) {
            return;
        }
        try {
            BeanUtils.copyProperties(source, target, excludes);
        } catch (Exception ignored) {
        }
    }

    /**
     * Copy properties from source to target, ignoring failures.
     *
     * @param source The source object to read from, may be {@code null}
     * @param target The target object to write to, may be {@code null}
     * @param excludes The field names to exclude, may be {@code null}
     */
    public static void copyPropertiesQuietly(@Nullable Object source, @Nullable Object target, @Nullable Collection<String> excludes) {
        copyPropertiesQuietly(source, target, true, excludes);
    }

    /**
     * Copy properties from source to target, ignoring failures.
     *
     * @param source The source object to read from, may be {@code null}
     * @param target The target object to write to, may be {@code null}
     * @param exclude Whether {@code fields} are exclusions rather than inclusions
     * @param fields The field names, may be {@code null}
     */
    public static void copyPropertiesQuietly(@Nullable Object source, @Nullable Object target, boolean exclude, @Nullable String... fields) {
        copyPropertiesQuietly(source, target, exclude, ArrayUtilsWraps.asList(fields));
    }

    /**
     * Copy properties from source to target, ignoring failures.
     *
     * @param source The source object to read from, may be {@code null}
     * @param target The target object to write to, may be {@code null}
     * @param exclude Whether {@code fields} are exclusions rather than inclusions
     * @param fields The field names, may be {@code null}
     */
    public static void copyPropertiesQuietly(@Nullable Object source, @Nullable Object target, boolean exclude, @Nullable Collection<String> fields) {
        try {
            copyProperties(source, target, exclude, fields);
        } catch (Exception ignored) {
        }
    }

    /**
     * Copy properties while also ignoring audit and annotated fields.
     *
     * @param source The source object to read from, may be {@code null}
     * @param target The target object to write to, may be {@code null}
     *
     * @throws BeansException If the operation fails
     */
    public static void copyPropertiesSheared(@Nullable Object source, @Nullable Object target) throws BeansException {
        copyPropertiesSheared(source, target, (Collection<String>) null);
    }

    /**
     * Copy properties while also ignoring audit and annotated fields.
     *
     * @param source The source object to read from, may be {@code null}
     * @param target The target object to write to, may be {@code null}
     * @param excludes The field names to exclude, may be {@code null}
     *
     * @throws BeansException If the operation fails
     */
    public static void copyPropertiesSheared(@Nullable Object source, @Nullable Object target, @Nullable String... excludes) throws BeansException {
        copyPropertiesSheared(source, target, ArrayUtilsWraps.asList(excludes));
    }

    /**
     * Copy properties from source to target, but ignore the given fields and other fields with some special annotations
     * <p>
     * <ul>
     *     <li>Fields with {@link CreatedBy} annotation</li>
     *     <li>Fields with {@link CreatedDate} annotation</li>
     *     <li>Fields with {@link LastModifiedBy} annotation</li>
     *     <li>Fields with {@link LastModifiedDate} annotation</li>
     *     <li>Fields with {@link BeanCopyIgnore} annotation</li>
     *     <li>Fields with {@link ViewTransferIgnore} annotation</li>
     * </ul>
     *
     * @param source The source object to copy properties from
     * @param target The target object to copy properties to
     * @param excludes The fields to ignore
     */
    public static void copyPropertiesSheared(@Nullable Object source, @Nullable Object target, @Nullable Collection<String> excludes) throws BeansException {
        if (source == null || target == null) {
            return;
        }
        Set<String> ignores = new HashSet<>();
        CollectionPlainWraps.addAllIfNotBlank(ignores, excludes);
        CollectionPlainWraps.addAllIfNotBlank(ignores, ReflectionUtilsWraps.getFieldNamesWithAnyAnnotationsToSet(target.getClass(), BeanCopierConst.IGNORABLE_ANNOTATIONS));
        copyProperties(source, target, true, ignores);
    }

    /**
     * Copy shared properties, ignoring failures.
     *
     * @param source The source object to read from, may be {@code null}
     * @param target The target object to write to, may be {@code null}
     */
    public static void copyPropertiesShearedQuietly(@Nullable Object source, @Nullable Object target) {
        copyPropertiesShearedQuietly(source, target, (Collection<String>) null);
    }

    /**
     * Copy shared properties, ignoring failures.
     *
     * @param source The source object to read from, may be {@code null}
     * @param target The target object to write to, may be {@code null}
     * @param excludes The field names to exclude, may be {@code null}
     */
    public static void copyPropertiesShearedQuietly(@Nullable Object source, @Nullable Object target, @Nullable String... excludes) {
        copyPropertiesShearedQuietly(source, target, ArrayUtilsWraps.asList(excludes));
    }

    /**
     * Copy shared properties, ignoring failures.
     *
     * @param source The source object to read from, may be {@code null}
     * @param target The target object to write to, may be {@code null}
     * @param excludes The field names to exclude, may be {@code null}
     */
    public static void copyPropertiesShearedQuietly(@Nullable Object source, @Nullable Object target, @Nullable Collection<String> excludes) {
        try {
            copyPropertiesSheared(source, target, excludes);
        } catch (Exception ignored) {
        }
    }

    /**
     * Convert the bean properties into a map.
     *
     * @param bean The bean object, may be {@code null}
     */
    @Nullable
    public static Map<String, Object> beanToMap(@Nullable Object bean) {
        return beanToMap(bean, true, Collections.emptyList());
    }

    /**
     * Convert the bean properties into a map.
     *
     * @param bean The bean object, may be {@code null}
     * @param excludes The field names to exclude, may be {@code null}
     */
    @Nullable
    public static Map<String, Object> beanToMap(@Nullable Object bean, @Nullable Collection<String> excludes) {
        return beanToMap(bean, true, excludes);
    }

    /**
     * Convert the bean properties into a map.
     *
     * @param bean The bean object, may be {@code null}
     * @param exclude Whether {@code fields} are exclusions rather than inclusions
     * @param fields The field names, may be {@code null}
     */
    @Nullable
    public static Map<String, Object> beanToMap(@Nullable Object bean, boolean exclude, @Nullable String... fields) {
        return beanToMap(bean, true, ArrayUtilsWraps.asList(fields));
    }

    /**
     * Convert the bean properties into a map.
     *
     * @param bean The bean object, may be {@code null}
     * @param exclude Whether {@code fields} are exclusions rather than inclusions
     * @param fields The field names, may be {@code null}
     */
    @Nullable
    public static Map<String, Object> beanToMap(@Nullable Object bean, boolean exclude, @Nullable Collection<String> fields) {
        if (bean == null || (!exclude && CollectionUtils.isEmpty(fields))) {
            return null;
        }
        BeanMap map = BeanMap.create(bean);
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Object> result = new LinkedHashMap<>(MapPlainWraps.size(map));
        for (Object key : map.keySet()) {
            String name = ObjectUtils.getDisplayString(key);
            if ((exclude && !CollectionPlainWraps.containsString(fields, name)) || (!exclude && CollectionPlainWraps.containsString(fields, name))) {
                result.put(name, map.get(key));
            }
        }
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    /**
     * Convert the bean properties into a Properties object.
     *
     * @param bean The bean object, may be {@code null}
     *
     * @return The bean properties, or {@code null}
     */
    @Nullable
    public static Properties beanToProperties(@Nullable Object bean) {
        return PropertyPlainWraps.ofMap(beanToMap(bean));
    }

    /**
     * Convert the bean properties into a Properties object.
     *
     * @param bean The bean object, may be {@code null}
     * @param excludes The field names to exclude, may be {@code null}
     *
     * @return The bean properties, or {@code null}
     */
    @Nullable
    public static Properties beanToProperties(@Nullable Object bean, @Nullable Collection<String> excludes) {
        return PropertyPlainWraps.ofMap(beanToMap(bean, excludes));
    }

    /**
     * Convert the bean properties into a Properties object.
     *
     * @param bean The bean object, may be {@code null}
     * @param exclude Whether {@code fields} are exclusions rather than inclusions
     * @param fields The field names, may be {@code null}
     *
     * @return The bean properties, or {@code null}
     */
    @Nullable
    public static Properties beanToProperties(@Nullable Object bean, boolean exclude, @Nullable String... fields) {
        return PropertyPlainWraps.ofMap(beanToMap(bean, exclude, fields));
    }

    /**
     * Convert the bean properties into a Properties object.
     *
     * @param bean The bean object, may be {@code null}
     * @param exclude Whether {@code fields} are exclusions rather than inclusions
     * @param fields The field names, may be {@code null}
     *
     * @return The bean properties, or {@code null}
     */
    @Nullable
    public static Properties beanToProperties(@Nullable Object bean, boolean exclude, @Nullable Collection<String> fields) {
        return PropertyPlainWraps.ofMap(beanToMap(bean, exclude, fields));
    }

    /**
     * Apply the map entries as properties on the given bean.
     *
     * @param map The source map, may be {@code null}
     * @param bean The bean object, may be {@code null}
     *
     * @throws BeansException If the operation fails
     */
    public static void mapToBean(@Nullable Map<?, ?> map, @Nullable Object bean) throws BeansException {
        if (map == null || bean == null || map.isEmpty()) {
            return;
        }
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            setProperty(bean, ObjectUtils.getDisplayString(entry.getKey()), entry.getValue());
        }
    }

    /**
     * Apply the map entries as properties on the given bean, ignoring failures.
     *
     * @param map The source map, may be {@code null}
     * @param bean The bean object, may be {@code null}
     */
    public static void mapToBeanQuietly(@Nullable Map<String, ?> map, @Nullable Object bean) {
        try {
            mapToBean(map, bean);
        } catch (Exception ignored) {
        }
    }

    /**
     * Instantiate the given class or constructor.
     *
     * @param clazz The class to use, may be {@code null}
     *
     * @return The instantiated object, or {@code null}
     *
     * @throws BeanInstantiationException If the operation fails
     */
    @Nullable
    public static <T> T instantiateClass(@Nullable Class<T> clazz) throws BeanInstantiationException {
        return (clazz == null) ? null : BeanUtils.instantiateClass(clazz);
    }

    /**
     * Instantiate the given class or constructor, returning {@code null} on failure.
     *
     * @param clazz The class to use, may be {@code null}
     *
     * @return The instantiated object, or {@code null}
     */
    @Nullable
    public static <T> T instantiateClassQuietly(@Nullable Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        try {
            return BeanUtils.instantiateClass(clazz);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Instantiate the given class or constructor.
     *
     * @param clazz The class to use, may be {@code null}
     * @param assignableTo The type the result must be assignable to, may be {@code null}
     *
     * @return The instantiated object, or {@code null}
     *
     * @throws BeanInstantiationException If the operation fails
     */
    @Nullable
    public static <T> T instantiateClass(@Nullable Class<?> clazz, Class<T> assignableTo) throws BeanInstantiationException {
        return (clazz == null || assignableTo == null) ? null : BeanUtils.instantiateClass(clazz, assignableTo);
    }

    /**
     * Instantiate the given class or constructor, returning {@code null} on failure.
     *
     * @param clazz The class to use, may be {@code null}
     * @param assignableTo The type the result must be assignable to, may be {@code null}
     *
     * @return The instantiated object, or {@code null}
     */
    @Nullable
    public static <T> T instantiateClassQuietly(@Nullable Class<?> clazz, Class<T> assignableTo) {
        if (clazz == null || assignableTo == null) {
            return null;
        }
        try {
            return BeanUtils.instantiateClass(clazz, assignableTo);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Instantiate the given class or constructor.
     *
     * @param constructor The constructor to invoke, may be {@code null}
     * @param args The constructor or factory arguments, may be {@code null}
     *
     * @return The instantiated object, or {@code null}
     *
     * @throws BeanInstantiationException If the operation fails
     */
    @Nullable
    public static <T> T instantiateClass(@Nullable Constructor<T> constructor, @Nullable Object... args) throws BeanInstantiationException {
        return (constructor == null || args == null) ? null : BeanUtils.instantiateClass(constructor, args);
    }

    /**
     * Instantiate the given class or constructor, returning {@code null} on failure.
     *
     * @param constructor The constructor to invoke, may be {@code null}
     * @param args The constructor or factory arguments, may be {@code null}
     *
     * @return The instantiated object, or {@code null}
     */
    @Nullable
    public static <T> T instantiateClassQuietly(@Nullable Constructor<T> constructor, @Nullable Object... args) {
        if (constructor == null || args == null) {
            return null;
        }
        try {
            return BeanUtils.instantiateClass(constructor, args);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * @see org.springframework.beans.BeanUtils#copyProperties(java.lang.Object, java.lang.Object)
     */
    public static boolean setProperty(@Nullable Object bean, @Nullable String property, @Nullable Object value) throws BeansException {
        if (bean == null || !StringUtils.hasText(property)) {
            return false;
        }
        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(bean.getClass(), property);
        if (descriptor == null) {
            return false;
        }
        Method writeMethod = descriptor.getWriteMethod();
        if (writeMethod == null) {
            return false;
        }
        try {
            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                writeMethod.setAccessible(true);
            }
            writeMethod.invoke(bean, value);
            return true;
        } catch (Exception ex) {
            throw new FatalBeanException("Could not copy property '" + property + "' from source to target", ex);
        }
    }

    /**
     * Set a single bean property, returning {@code false} on failure.
     *
     * @param bean The bean object, may be {@code null}
     * @param property The property name, may be {@code null}
     * @param value The property value, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean setPropertyQuietly(@Nullable Object bean, @Nullable String property, @Nullable Object value) {
        try {
            return setProperty(bean, property, value);
        } catch (Exception ignored) {
        }
        return false;
    }
}
