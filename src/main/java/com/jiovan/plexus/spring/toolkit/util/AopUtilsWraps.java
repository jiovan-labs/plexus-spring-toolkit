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


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import com.jiovan.plexus.common.toolkit.util.ObjectUtilsWraps;


/**
 * Utilities for Spring aop
 *
 * @author David Hsing
 *
 * @see org.springframework.aop.support.AopUtils
 * @see org.springframework.aop.framework.AopProxyUtils
 * @see org.springframework.aop.config.AopNamespaceUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class AopUtilsWraps {
    /**
     * Return the constructor targeted by the given join point.
     *
     * @param point The join point, may be {@code null}
     *
     * @return The resolved constructor, or {@code null}
     *
     * @throws NoSuchMethodException If the operation fails
     * @throws SecurityException If the operation fails
     */
    @Nullable
    public static Constructor<?> getConstructor(@Nullable JoinPoint point) throws NoSuchMethodException, SecurityException {
        ConstructorSignature signature = getConstructorSignature(point);
        return (signature == null) ? null : point.getTarget().getClass().getConstructor(signature.getParameterTypes());
    }

    /**
     * Return the constructor targeted by the given join point, or {@code null} on failure.
     *
     * @param point The join point, may be {@code null}
     *
     * @return The resolved constructor, or {@code null}
     */
    @Nullable
    public static Constructor<?> getConstructorQuietly(@Nullable JoinPoint point) {
        try {
            return getConstructor(point);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Return the constructor signature of the join point when available.
     *
     * @param point The join point, may be {@code null}
     *
     * @return The constructor signature, or {@code null}
     */
    public static ConstructorSignature getConstructorSignature(@Nullable JoinPoint point) {
        return (point != null && point.getSignature() instanceof ConstructorSignature alias) ? alias : null;
    }

    /**
     * Return the method signature of the join point when available.
     *
     * @param point The join point, may be {@code null}
     *
     * @return The method signature, or {@code null}
     */
    public static MethodSignature getMethodSignature(@Nullable JoinPoint point) {
        return (point != null && point.getSignature() instanceof MethodSignature alias) ? alias : null;
    }

    /**
     * Return the join point argument at the given index.
     *
     * @param point The join point, may be {@code null}
     * @param index The zero-based argument index
     *
     * @return The argument value, or the default value
     */
    public static Object getParameter(@Nullable JoinPoint point, int index) {
        return getParameter(point, index, null);
    }

    /**
     * Return the join point argument at the given index.
     *
     * @param point The join point, may be {@code null}
     * @param index The zero-based argument index
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The argument value, or the default value
     */
    public static Object getParameter(@Nullable JoinPoint point, int index, @Nullable Object defaultValue) {
        return (point == null || index < 0) ? defaultValue : ArrayUtils.get(point.getArgs(), index, defaultValue);
    }

    /**
     * Return the join point argument at the given index converted to the expected type.
     *
     * @param point The join point, may be {@code null}
     * @param index The zero-based argument index
     * @param expectType The expected type, may be {@code null}
     *
     * @return The converted argument value, or the default value
     */
    public static <T> T getParameterAs(@Nullable JoinPoint point, int index, @Nullable Class<T> expectType) {
        return getParameterAs(point, index, expectType, null);
    }

    /**
     * Return the join point argument at the given index converted to the expected type.
     *
     * @param point The join point, may be {@code null}
     * @param index The zero-based argument index
     * @param expectType The expected type, may be {@code null}
     * @param defaultValue The fallback value, may be {@code null}
     *
     * @return The converted argument value, or the default value
     */
    public static <T> T getParameterAs(@Nullable JoinPoint point, int index, @Nullable Class<T> expectType, @Nullable T defaultValue) {
        return ObjectUtilsWraps.castAs(getParameter(point, index), expectType, defaultValue);
    }

    /**
     * Return the underlying target class of the given candidate object.
     *
     * @param candidate The candidate object, may be {@code null}
     *
     * @return The underlying target class, or {@code null}
     */
    @Nullable
    public static Class<?> getTargetClass(@Nullable Object candidate) {
        return (candidate == null) ? null : AopUtils.getTargetClass(candidate);
    }

    /**
     * Return the method targeted by the given join point.
     *
     * @param point The join point, may be {@code null}
     *
     * @return The resolved method, or {@code null}
     *
     * @throws NoSuchMethodException If the operation fails
     * @throws SecurityException If the operation fails
     */
    @Nullable
    public static Method getTargetMethod(@Nullable JoinPoint point) throws NoSuchMethodException, SecurityException {
        MethodSignature signature = getMethodSignature(point);
        return (signature == null) ? null : point.getTarget().getClass().getMethod(signature.getName(), signature.getParameterTypes());
    }

    /**
     * Return the method targeted by the given join point, or {@code null} on failure.
     *
     * @param point The join point, may be {@code null}
     *
     * @return The resolved method, or {@code null}
     */
    @Nullable
    public static Method getTargetMethodQuietly(@Nullable JoinPoint point) {
        try {
            return getTargetMethod(point);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Return whether the join point target class is assignable to the given superclass.
     *
     * @param point The join point, may be {@code null}
     * @param superclass The target superclass, may be {@code null}
     *
     * @return Whether the target class is assignable
     */
    public static boolean isTargetClassAssignable(@Nullable JoinPoint point, @Nullable Class<?> superclass) {
        return point != null && ClassUtilsWraps.isAssignable(superclass, point.getTarget().getClass());
    }

    /**
     * Return whether the join point target class is assignable to the given superclass.
     *
     * @param point The join point, may be {@code null}
     * @param superclassName The fully qualified superclass name, may be {@code null}
     *
     * @return Whether the target class is assignable
     */
    public static boolean isTargetClassAssignable(@Nullable JoinPoint point, @Nullable String superclassName) {
        return isTargetClassAssignable(point, superclassName, null);
    }

    /**
     * Return whether the join point target class is assignable to the given superclass.
     *
     * @param point The join point, may be {@code null}
     * @param superclassName The fully qualified superclass name, may be {@code null}
     * @param classLoader The class loader to use, may be {@code null}
     *
     * @return Whether the target class is assignable
     */
    public static boolean isTargetClassAssignable(@Nullable JoinPoint point, @Nullable String superclassName, @Nullable ClassLoader classLoader) {
        return point != null && ClassUtilsWraps.isAssignable(superclassName, point.getTarget().getClass().getName(), classLoader);
    }
}
