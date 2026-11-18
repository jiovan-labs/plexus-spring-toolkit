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
import jakarta.annotation.Nullable;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.jiovan.plexus.common.toolkit.util.ArrayUtilsWraps;


/**
 * Utilities for stereotype beans and classes
 *
 * @author David Hsing
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class StereotypeBeanWraps {
    /**
     * Return whether all the given classes are annotated as mergedcontrollers.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean allMergedController(@Nullable Class<?>... classes) {
        return allMergedController(ArrayUtilsWraps.asList(classes));
    }

    /**
     * Return whether all the given classes are annotated as mergedcontrollers.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean allMergedController(@Nullable Collection<Class<?>> classes) {
        return !CollectionUtils.isEmpty(classes) && classes.stream().allMatch(StereotypeBeanWraps::isMergedController);
    }
    /**
     * Return whether all the given classes are annotated as controllers.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean allController(@Nullable Class<?>... classes) {
        return allController(ArrayUtilsWraps.asList(classes));
    }

    /**
     * Return whether all the given classes are annotated as controllers.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean allController(@Nullable Collection<Class<?>> classes) {
        return !CollectionUtils.isEmpty(classes) && classes.stream().allMatch(StereotypeBeanWraps::isController);
    }

    /**
     * Return whether all the given classes are annotated as controlleradvices.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean allControllerAdvice(@Nullable Class<?>... classes) {
        return allControllerAdvice(ArrayUtilsWraps.asList(classes));
    }

    /**
     * Return whether all the given classes are annotated as controlleradvices.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean allControllerAdvice(@Nullable Collection<Class<?>> classes) {
        return !CollectionUtils.isEmpty(classes) && classes.stream().allMatch(StereotypeBeanWraps::isControllerAdvice);
    }

    /**
     * Return whether all the given classes are annotated as restcontrollers.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean allRestController(@Nullable Class<?>... classes) {
        return allRestController(ArrayUtilsWraps.asList(classes));
    }

    /**
     * Return whether all the given classes are annotated as restcontrollers.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean allRestController(@Nullable Collection<Class<?>> classes) {
        return !CollectionUtils.isEmpty(classes) && classes.stream().allMatch(StereotypeBeanWraps::isRestController);
    }

    /**
     * Return whether all the given classes are annotated as restcontrolleradvices.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean allRestControllerAdvice(@Nullable Class<?>... classes) {
        return allRestControllerAdvice(ArrayUtilsWraps.asList(classes));
    }

    /**
     * Return whether all the given classes are annotated as restcontrolleradvices.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean allRestControllerAdvice(@Nullable Collection<Class<?>> classes) {
        return !CollectionUtils.isEmpty(classes) && classes.stream().allMatch(StereotypeBeanWraps::isRestControllerAdvice);
    }

    /**
     * Return whether all the given classes are annotated as services.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean allService(@Nullable Class<?>... classes) {
        return allService(ArrayUtilsWraps.asList(classes));
    }

    /**
     * Return whether all the given classes are annotated as services.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean allService(@Nullable Collection<Class<?>> classes) {
        return !CollectionUtils.isEmpty(classes) && classes.stream().allMatch(StereotypeBeanWraps::isService);
    }

    /**
     * Return whether all the given classes are annotated as repositorys.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean allRepository(@Nullable Class<?>... classes) {
        return allRepository(ArrayUtilsWraps.asList(classes));
    }

    /**
     * Return whether all the given classes are annotated as repositorys.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean allRepository(@Nullable Collection<Class<?>> classes) {
        return !CollectionUtils.isEmpty(classes) && classes.stream().allMatch(StereotypeBeanWraps::isRepository);
    }

    /**
     * Return whether any the given classes are annotated as mergedcontrollers.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean anyMergedController(@Nullable Class<?>... classes) {
        return anyMergedController(ArrayUtilsWraps.asList(classes));
    }

    /**
     * Return whether any the given classes are annotated as mergedcontrollers.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean anyMergedController(@Nullable Collection<Class<?>> classes) {
        return !CollectionUtils.isEmpty(classes) && classes.stream().anyMatch(StereotypeBeanWraps::isMergedController);
    }

    /**
     * Return whether any the given classes are annotated as controllers.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean anyController(@Nullable Class<?>... classes) {
        return anyController(ArrayUtilsWraps.asList(classes));
    }

    /**
     * Return whether any the given classes are annotated as controllers.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean anyController(@Nullable Collection<Class<?>> classes) {
        return !CollectionUtils.isEmpty(classes) && classes.stream().anyMatch(StereotypeBeanWraps::isController);
    }

    /**
     * Return whether any the given classes are annotated as controlleradvices.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean anyControllerAdvice(@Nullable Class<?>... classes) {
        return anyControllerAdvice(ArrayUtilsWraps.asList(classes));
    }

    /**
     * Return whether any the given classes are annotated as controlleradvices.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean anyControllerAdvice(@Nullable Collection<Class<?>> classes) {
        return !CollectionUtils.isEmpty(classes) && classes.stream().anyMatch(StereotypeBeanWraps::isControllerAdvice);
    }

    /**
     * Return whether any the given classes are annotated as restcontrollers.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean anyRestController(@Nullable Class<?>... classes) {
        return anyRestController(ArrayUtilsWraps.asList(classes));
    }

    /**
     * Return whether any the given classes are annotated as restcontrollers.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean anyRestController(@Nullable Collection<Class<?>> classes) {
        return !CollectionUtils.isEmpty(classes) && classes.stream().anyMatch(StereotypeBeanWraps::isRestController);
    }

    /**
     * Return whether any the given classes are annotated as restcontrolleradvices.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean anyRestControllerAdvice(@Nullable Class<?>... classes) {
        return anyRestControllerAdvice(ArrayUtilsWraps.asList(classes));
    }

    /**
     * Return whether any the given classes are annotated as restcontrolleradvices.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean anyRestControllerAdvice(@Nullable Collection<Class<?>> classes) {
        return !CollectionUtils.isEmpty(classes) && classes.stream().anyMatch(StereotypeBeanWraps::isRestControllerAdvice);
    }

    /**
     * Return whether any the given classes are annotated as services.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean anyService(@Nullable Class<?>... classes) {
        return anyService(ArrayUtilsWraps.asList(classes));
    }

    /**
     * Return whether any the given classes are annotated as services.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean anyService(@Nullable Collection<Class<?>> classes) {
        return !CollectionUtils.isEmpty(classes) && classes.stream().anyMatch(StereotypeBeanWraps::isService);
    }

    /**
     * Return whether any the given classes are annotated as repositorys.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean anyRepository(@Nullable Class<?>... classes) {
        return anyRepository(ArrayUtilsWraps.asList(classes));
    }

    /**
     * Return whether any the given classes are annotated as repositorys.
     *
     * @param classes The given classes
     *
     * @return Whether the condition holds
     */
    public static boolean anyRepository(@Nullable Collection<Class<?>> classes) {
        return !CollectionUtils.isEmpty(classes) && classes.stream().anyMatch(StereotypeBeanWraps::isRepository);
    }

    /**
     * Returns whether the given class is annotated with {@link org.springframework.stereotype.Controller} or {@link org.springframework.web.bind.annotation.RestController}
     *
     * @param clazz The target class to check
     *
     * @return whether the given class is annotated with {@link org.springframework.stereotype.Controller} or {@link org.springframework.web.bind.annotation.RestController}
     */
    public static boolean isMergedController(@Nullable Class<?> clazz) {
        return clazz != null && AnnotatedElementUtils.findMergedAnnotation(clazz, Controller.class) != null;
    }

    /**
     * Returns whether the given class is annotated with {@link org.springframework.stereotype.Controller}
     *
     * @param clazz The target class to check
     *
     * @return whether the given class is annotated with {@link org.springframework.stereotype.Controller}
     */
    public static boolean isController(@Nullable Class<?> clazz) {
        return clazz != null && AnnotatedElementUtils.hasAnnotation(clazz, Controller.class);
    }

    /**
     * Returns whether the given class is annotated with {@link org.springframework.web.bind.annotation.ControllerAdvice}
     *
     * @param clazz The target class to check
     *
     * @return whether the given class is annotated with {@link org.springframework.web.bind.annotation.ControllerAdvice}
     */
    public static boolean isControllerAdvice(@Nullable Class<?> clazz) {
        return clazz != null && AnnotatedElementUtils.hasAnnotation(clazz, ControllerAdvice.class);
    }

    /**
     * Returns whether the given class is annotated with {@link org.springframework.web.bind.annotation.RestController}
     *
     * @param clazz The target class to check
     *
     * @return whether the given class is annotated with {@link org.springframework.web.bind.annotation.RestController}
     */
    public static boolean isRestController(@Nullable Class<?> clazz) {
        return clazz != null && AnnotatedElementUtils.hasAnnotation(clazz, RestController.class);
    }

    /**
     * Returns whether the given class is annotated with {@link org.springframework.web.bind.annotation.RestControllerAdvice}
     *
     * @param clazz The target class to check
     *
     * @return whether the given class is annotated with {@link org.springframework.web.bind.annotation.RestControllerAdvice}
     */
    public static boolean isRestControllerAdvice(@Nullable Class<?> clazz) {
        return clazz != null && AnnotatedElementUtils.hasAnnotation(clazz, RestControllerAdvice.class);
    }

    /**
     * Returns whether the given class is annotated with {@link org.springframework.stereotype.Service}
     *
     * @param clazz The target class to check
     *
     * @return whether the given class is annotated with {@link org.springframework.stereotype.Service}
     */
    public static boolean isService(@Nullable Class<?> clazz) {
        return clazz != null && AnnotatedElementUtils.hasAnnotation(clazz, Service.class);
    }

    /**
     * Returns whether the given class is annotated with {@link org.springframework.stereotype.Repository}
     *
     * @param clazz The target class to check
     *
     * @return whether the given class is annotated with {@link org.springframework.stereotype.Repository}
     */
    public static boolean isRepository(@Nullable Class<?> clazz) {
        return clazz != null && AnnotatedElementUtils.hasAnnotation(clazz, Repository.class);
    }
}
