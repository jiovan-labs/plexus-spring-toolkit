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
import java.util.Objects;
import java.util.function.Consumer;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import com.jiovan.plexus.common.toolkit.util.ArrayUtilsWraps;


/**
 * Utilities for {@link org.springframework.util.ClassUtils}
 *
 * @author David Hsing
 *
 * @see org.springframework.util.ClassUtils
 * @see org.springframework.util.TypeUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class ClassUtilsWraps {
    /**
     * Return whether the subclass is assignable to all the given superclasses.
     *
     * @param subclass The subclass to check, may be {@code null}
     * @param superclasses The superclasses to check, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean allAssignable(@Nullable Class<?> subclass, @Nullable Class<?>... superclasses) {
        return allAssignable(subclass, ArrayUtilsWraps.asList(superclasses));
    }

    /**
     * Return whether the subclass is assignable to all the given superclasses.
     *
     * @param subclass The subclass to check, may be {@code null}
     * @param superclasses The superclasses to check, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean allAssignable(@Nullable Class<?> subclass, @Nullable Collection<Class<?>> superclasses) {
        return subclass != null && !CollectionUtils.isEmpty(superclasses) && superclasses.stream().allMatch(superclass -> superclass != null && ClassUtils.isAssignable(superclass, subclass));
    }

    /**
     * Return whether the subclass is assignable to any of the given superclasses.
     *
     * @param subclass The subclass to check, may be {@code null}
     * @param superclasses The superclasses to check, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean anyAssignable(@Nullable Class<?> subclass, @Nullable Class<?>... superclasses) {
        return anyAssignable(subclass, ArrayUtilsWraps.asList(superclasses));
    }

    /**
     * Return whether the subclass is assignable to any of the given superclasses.
     *
     * @param subclass The subclass to check, may be {@code null}
     * @param superclasses The superclasses to check, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean anyAssignable(@Nullable Class<?> subclass, @Nullable Collection<Class<?>> superclasses) {
        return subclass != null && !CollectionUtils.isEmpty(superclasses) && superclasses.stream().filter(Objects::nonNull).anyMatch(superclass -> ClassUtils.isAssignable(superclass, subclass));
    }

    /**
     * Return whether all the values are assignable to the given class.
     *
     * @param clazz The class to use, may be {@code null}
     * @param values The values to check, may be {@code null}
     *
     * @return Whether the condition holds
     */
    @SafeVarargs
    public static <T> boolean allAssignableValue(@Nullable Class<?> clazz, @Nullable T... values) {
        return allAssignableValue(clazz, ArrayUtilsWraps.asList(values));
    }

    /**
     * Return whether all the values are assignable to the given class.
     *
     * @param clazz The class to use, may be {@code null}
     * @param values The values to check, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static <T> boolean allAssignableValue(@Nullable Class<?> clazz, @Nullable Collection<T> values) {
        return clazz != null && !CollectionUtils.isEmpty(values) && values.stream().allMatch(value -> value != null && ClassUtils.isAssignableValue(clazz, value));
    }

    /**
     * Return whether any of the values is assignable to the given class.
     *
     * @param clazz The class to use, may be {@code null}
     * @param values The values to check, may be {@code null}
     *
     * @return Whether the condition holds
     */
    @SafeVarargs
    public static <T> boolean anyAssignableValue(@Nullable Class<?> clazz, @Nullable T... values) {
        return anyAssignableValue(clazz, ArrayUtilsWraps.asList(values));
    }

    /**
     * Return whether any of the values is assignable to the given class.
     *
     * @param clazz The class to use, may be {@code null}
     * @param values The values to check, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static <T> boolean anyAssignableValue(@Nullable Class<?> clazz, @Nullable Collection<T> values) {
        return clazz != null && !CollectionUtils.isEmpty(values) && values.stream().filter(Objects::nonNull).anyMatch(value -> ClassUtils.isAssignableValue(clazz, value));
    }

    /**
     * Return whether all the named classes are present on the class path.
     *
     * @param classLoader The class loader, may be {@code null}
     * @param classNames The class names to check, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean allPresent(@Nullable ClassLoader classLoader, @Nullable String... classNames) {
        return allPresent(classLoader, ArrayUtilsWraps.asList(classNames));
    }

    /**
     * Return whether all the named classes are present on the class path.
     *
     * @param classLoader The class loader, may be {@code null}
     * @param classNames The class names to check, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean allPresent(@Nullable ClassLoader classLoader, @Nullable Collection<String> classNames) {
        return !CollectionUtils.isEmpty(classNames) && classNames.stream().allMatch(className -> ClassUtilsWraps.isPresent(className, classLoader));
    }

    /**
     * Return whether any of the named classes is present on the class path.
     *
     * @param classLoader The class loader, may be {@code null}
     * @param classNames The class names to check, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean anyPresent(@Nullable ClassLoader classLoader, @Nullable String... classNames) {
        return anyPresent(classLoader, ArrayUtilsWraps.asList(classNames));
    }

    /**
     * Return whether any of the named classes is present on the class path.
     *
     * @param classLoader The class loader, may be {@code null}
     * @param classNames The class names to check, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean anyPresent(@Nullable ClassLoader classLoader, @Nullable Collection<String> classNames) {
        return !CollectionUtils.isEmpty(classNames) && classNames.stream().filter(StringUtils::isNotBlank).anyMatch(className -> ClassUtils.isPresent(className, classLoader));
    }

    /**
     * Return whether either object is assignable from the other.
     *
     * @param one The first object, may be {@code null}
     * @param two The second object, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static <T> boolean eachAnyAssignable(@Nullable Object one, @Nullable Object two) {
        return (one != null && ClassUtils.isAssignableValue(one.getClass(), two)) || (two != null && ClassUtils.isAssignableValue(two.getClass(), one));
    }

    /**
     * Load the class with the given name.
     *
     * @param className The class name to load, may be {@code null}
     *
     * @return The loaded class, or {@code null}
     *
     * @throws ClassNotFoundException If the operation fails
     * @throws LinkageError If the operation fails
     */
    @Nullable
    public static Class<?> forName(@Nullable String className) throws ClassNotFoundException, LinkageError {
        return forName(className, null);
    }

    /**
     * @see org.springframework.beans.BeanUtils#instantiateClass(Class)
     */
    @Nullable
    public static Class<?> forName(@Nullable String className, @Nullable ClassLoader classLoader) throws ClassNotFoundException, LinkageError {
        return StringUtils.isBlank(className) ? null : ClassUtils.forName(className, classLoader);
    }

    /**
     * Load the class and return it only when assignable to the expected type.
     *
     * @param className The class name to load, may be {@code null}
     * @param expectType The expected type, may be {@code null}
     *
     * @return The loaded class, or {@code null}
     *
     * @throws ClassNotFoundException If the operation fails
     * @throws LinkageError If the operation fails
     */
    @Nullable
    public static <T> Class<T> forNameAs(@Nullable String className, @Nullable Class<T> expectType) throws ClassNotFoundException, LinkageError {
        return forNameAs(className, null, expectType);
    }

    /**
     * Load the class and return it only when assignable to the expected type.
     *
     * @param className The class name to load, may be {@code null}
     * @param classLoader The class loader, may be {@code null}
     * @param expectType The expected type, may be {@code null}
     *
     * @return The loaded class, or {@code null}
     *
     * @throws ClassNotFoundException If the operation fails
     * @throws LinkageError If the operation fails
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> Class<T> forNameAs(@Nullable String className, @Nullable ClassLoader classLoader, @Nullable Class<T> expectType) throws ClassNotFoundException, LinkageError {
        if (StringUtils.isBlank(className) || expectType == null) {
            return null;
        }
        Class<?> result = ClassUtils.forName(className, classLoader);
        return ClassUtils.isAssignable(expectType, result) ? (Class<T>) result : null;
    }

    /**
     * Load the class by name, returning {@code null} on failure.
     *
     * @param className The class name to load, may be {@code null}
     *
     * @return The loaded class, or {@code null}
     */
    @Nullable
    public static Class<?> forNameQuietly(@Nullable String className) {
        return forNameQuietly(className, null);
    }

    /**
     * Load the class by name, returning {@code null} on failure.
     *
     * @param className The class name to load, may be {@code null}
     * @param classLoader The class loader, may be {@code null}
     *
     * @return The loaded class, or {@code null}
     */
    @Nullable
    public static Class<?> forNameQuietly(@Nullable String className, @Nullable ClassLoader classLoader) {
        try {
            return forName(className, classLoader);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Load the class as the expected type, returning {@code null} on failure.
     *
     * @param className The class name to load, may be {@code null}
     * @param expectType The expected type, may be {@code null}
     */
    @Nullable
    public static <T> Class<? extends T> forNameAsQuietly(@Nullable String className, @Nullable Class<T> expectType) {
        return forNameAsQuietly(className, null, expectType);
    }

    /**
     * Load the class as the expected type, returning {@code null} on failure.
     *
     * @param className The class name to load, may be {@code null}
     * @param classLoader The class loader, may be {@code null}
     * @param expectType The expected type, may be {@code null}
     *
     * @return The loaded class, or {@code null}
     */
    @Nullable
    public static <T> Class<T> forNameAsQuietly(@Nullable String className, @Nullable ClassLoader classLoader, @Nullable Class<T> expectType) {
        try {
            return forNameAs(className, classLoader, expectType);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Return the runtime class of the given object.
     *
     * @param object The object, may be {@code null}
     *
     * @return The runtime class, or {@code null}
     */
    public static Class<?> getObjectClass(@Nullable Object object) {
        return (object == null) ? null : object.getClass();
    }

    /**
     * Return the fully qualified name of the given class or object.
     *
     * @param clazz The class to use, may be {@code null}
     *
     * @return The qualified name, or {@code null}
     */
    public static String getQualifiedName(@Nullable Class<?> clazz) {
        return (clazz == null) ? null : ClassUtils.getQualifiedName(clazz);
    }

    /**
     * Return the fully qualified name of the given class or object.
     *
     * @param object The object, may be {@code null}
     *
     * @return The qualified name, or {@code null}
     */
    public static String getQualifiedName(@Nullable Object object) {
        return (object == null) ? null : ClassUtils.getQualifiedName(object.getClass());
    }

    /**
     * Run the action when the named class is present.
     *
     * @param className The class name to load, may be {@code null}
     * @param action The action to run, may be {@code null}
     */
    public static void ifPresent(@Nullable String className, @Nullable Consumer<String> action) {
        ifPresent(className, null, action);
    }

    /**
     * Run the action when the named class is present.
     *
     * @param className The class name to load, may be {@code null}
     * @param action The action to run, may be {@code null}
     */
    public static void ifPresent(@Nullable String className, @Nullable Runnable action) {
        ifPresent(className, null, action);
    }

    /**
     * Run the action when the named class is present.
     *
     * @param className The class name to load, may be {@code null}
     * @param classLoader The class loader, may be {@code null}
     * @param action The action to run, may be {@code null}
     */
    public static void ifPresent(@Nullable String className, @Nullable ClassLoader classLoader, @Nullable Consumer<String> action) {
        ifPresentOrElse(className, classLoader, action, null);
    }

    /**
     * Run the action when the named class is present.
     *
     * @param className The class name to load, may be {@code null}
     * @param classLoader The class loader, may be {@code null}
     * @param action The action to run, may be {@code null}
     */
    public static void ifPresent(@Nullable String className, @Nullable ClassLoader classLoader, @Nullable Runnable action) {
        ifPresentOrElse(className, classLoader, action, null);
    }

    /**
     * Run the present or absent action depending on class availability.
     *
     * @param className The class name to load, may be {@code null}
     * @param presentAction The action to run when the condition holds, may be {@code null}
     * @param absentAction The action to run when the condition does not hold, may be {@code null}
     */
    public static void ifPresentOrElse(@Nullable String className, @Nullable Consumer<String> presentAction, @Nullable Consumer<String> absentAction) {
        ifPresentOrElse(className, null, presentAction, absentAction);
    }

    /**
     * Run the present or absent action depending on class availability.
     *
     * @param className The class name to load, may be {@code null}
     * @param presentAction The action to run when the condition holds, may be {@code null}
     * @param absentAction The action to run when the condition does not hold, may be {@code null}
     */
    public static void ifPresentOrElse(@Nullable String className, @Nullable Runnable presentAction, @Nullable Runnable absentAction) {
        ifPresentOrElse(className, null, presentAction, absentAction);
    }

    /**
     * Run the present or absent action depending on class availability.
     *
     * @param className The class name to load, may be {@code null}
     * @param classLoader The class loader, may be {@code null}
     * @param presentAction The action to run when the condition holds, may be {@code null}
     * @param absentAction The action to run when the condition does not hold, may be {@code null}
     */
    public static void ifPresentOrElse(@Nullable String className, @Nullable ClassLoader classLoader, @Nullable Consumer<String> presentAction, @Nullable Consumer<String> absentAction) {
        if (isPresent(className, classLoader)) {
            if (presentAction != null) {
                presentAction.accept(className);
            }
        } else {
            if (absentAction != null) {
                absentAction.accept(className);
            }
        }
    }

    /**
     * Run the present or absent action depending on class availability.
     *
     * @param className The class name to load, may be {@code null}
     * @param classLoader The class loader, may be {@code null}
     * @param presentAction The action to run when the condition holds, may be {@code null}
     * @param absentAction The action to run when the condition does not hold, may be {@code null}
     */
    public static void ifPresentOrElse(@Nullable String className, @Nullable ClassLoader classLoader, @Nullable Runnable presentAction, @Nullable Runnable absentAction) {
        if (isPresent(className, classLoader)) {
            if (presentAction != null) {
                presentAction.run();
            }
        } else {
            if (absentAction != null) {
                absentAction.run();
            }
        }
    }

    /**
     * Run the action when the named class is not present.
     *
     * @param className The class name to load, may be {@code null}
     * @param action The action to run, may be {@code null}
     */
    public static void ifNotPresent(@Nullable String className, @Nullable Consumer<String> action) {
        ifNotPresent(className, null, action);
    }

    /**
     * Run the action when the named class is not present.
     *
     * @param className The class name to load, may be {@code null}
     * @param action The action to run, may be {@code null}
     */
    public static void ifNotPresent(@Nullable String className, @Nullable Runnable action) {
        ifNotPresent(className, null, action);
    }

    /**
     * Run the action when the named class is not present.
     *
     * @param className The class name to load, may be {@code null}
     * @param classLoader The class loader, may be {@code null}
     * @param action The action to run, may be {@code null}
     */
    public static void ifNotPresent(@Nullable String className, @Nullable ClassLoader classLoader, @Nullable Consumer<String> action) {
        if (isNotPresent(className, classLoader) && action != null) {
            action.accept(className);
        }
    }

    /**
     * Run the action when the named class is not present.
     *
     * @param className The class name to load, may be {@code null}
     * @param classLoader The class loader, may be {@code null}
     * @param action The action to run, may be {@code null}
     */
    public static void ifNotPresent(@Nullable String className, @Nullable ClassLoader classLoader, @Nullable Runnable action) {
        if (isNotPresent(className, classLoader) && action != null) {
            action.run();
        }
    }

    /**
     * Run the action when the class is primitive.
     *
     * @param clazz The class to use, may be {@code null}
     * @param action The action to run, may be {@code null}
     */
    public static void ifPrimitive(@Nullable Class<?> clazz, @Nullable Consumer<Class<?>> action) {
        ifPrimitiveOrElse(clazz, action, null);
    }

    /**
     * Run the action when the class is primitive.
     *
     * @param clazz The class to use, may be {@code null}
     * @param action The action to run, may be {@code null}
     */
    public static void ifPrimitive(@Nullable Class<?> clazz, @Nullable Runnable action) {
        ifPrimitiveOrElse(clazz, action, null);
    }

    /**
     * Run the present or absent action depending on whether the class is primitive.
     *
     * @param clazz The class to use, may be {@code null}
     * @param presentAction The action to run when the condition holds, may be {@code null}
     * @param absentAction The action to run when the condition does not hold, may be {@code null}
     */
    public static void ifPrimitiveOrElse(@Nullable Class<?> clazz, @Nullable Consumer<Class<?>> presentAction, @Nullable Consumer<Class<?>> absentAction) {
        if (isPrimitive(clazz)) {
            if (presentAction != null) {
                presentAction.accept(clazz);
            }
        } else {
            if (absentAction != null) {
                absentAction.accept(clazz);
            }
        }
    }

    /**
     * Run the present or absent action depending on whether the class is primitive.
     *
     * @param clazz The class to use, may be {@code null}
     * @param presentAction The action to run when the condition holds, may be {@code null}
     * @param absentAction The action to run when the condition does not hold, may be {@code null}
     */
    public static void ifPrimitiveOrElse(@Nullable Class<?> clazz, @Nullable Runnable presentAction, @Nullable Runnable absentAction) {
        if (isPrimitive(clazz)) {
            if (presentAction != null) {
                presentAction.run();
            }
        } else {
            if (absentAction != null) {
                absentAction.run();
            }
        }
    }

    /**
     * Run the action when the class is not primitive.
     *
     * @param clazz The class to use, may be {@code null}
     * @param action The action to run, may be {@code null}
     */
    public static void ifNotPrimitive(@Nullable Class<?> clazz, @Nullable Consumer<Class<?>> action) {
        if (isNotPrimitive(clazz) && action != null) {
            action.accept(clazz);
        }
    }

    /**
     * Run the action when the class is not primitive.
     *
     * @param clazz The class to use, may be {@code null}
     * @param action The action to run, may be {@code null}
     */
    public static void ifNotPrimitive(@Nullable Class<?> clazz, @Nullable Runnable action) {
        if (isNotPrimitive(clazz) && action != null) {
            action.run();
        }
    }

    /**
     * Return whether the named class is present on the class path.
     *
     * @param className The class name to load, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isPresent(@Nullable String className) {
        return isPresent(className, null);
    }

    /**
     * Return whether the named class is present on the class path.
     *
     * @param className The class name to load, may be {@code null}
     * @param classLoader The class loader, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isPresent(@Nullable String className, @Nullable ClassLoader classLoader) {
        return StringUtils.isNotBlank(className) && ClassUtils.isPresent(className, classLoader);
    }

    /**
     * Return whether the named class is not present on the class path.
     *
     * @param className The class name to load, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isNotPresent(@Nullable String className) {
        return !isPresent(className);
    }

    /**
     * Return whether the named class is not present on the class path.
     *
     * @param className The class name to load, may be {@code null}
     * @param classLoader The class loader, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isNotPresent(@Nullable String className, @Nullable ClassLoader classLoader) {
        return !isPresent(className, classLoader);
    }

    /**
     * Return whether the class is primitive.
     *
     * @param clazz The class to use, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isPrimitive(@Nullable Class<?> clazz) {
        return clazz != null && clazz.isPrimitive();
    }

    /**
     * Return whether the class is not primitive.
     *
     * @param clazz The class to use, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isNotPrimitive(@Nullable Class<?> clazz) {
        return !isPrimitive(clazz);
    }

    /**
     * Return whether one type is assignable to the other.
     *
     * @param superclass The superclass to check, may be {@code null}
     * @param subclass The subclass to check, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isAssignable(@Nullable Class<?> superclass, @Nullable Class<?> subclass) {
        return ObjectUtils.allNotNull(superclass, subclass) && ClassUtils.isAssignable(superclass, subclass);
    }

    /**
     * Return whether one type is assignable to the other.
     *
     * @param superclassName The given superclassName
     * @param subclassName The given subclassName
     *
     * @return Whether the condition holds
     */
    public static boolean isAssignable(@Nullable String superclassName, @Nullable String subclassName) {
        return isAssignable(superclassName, subclassName, null);
    }

    /**
     * Return whether one type is assignable to the other.
     *
     * @param superclassName The given superclassName
     * @param subclassName The given subclassName
     * @param classLoader The class loader, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isAssignable(@Nullable String superclassName, @Nullable String subclassName, @Nullable ClassLoader classLoader) {
        return StringUtils.isNoneBlank(superclassName, subclassName) && isAssignable(forNameQuietly(superclassName, classLoader), forNameQuietly(subclassName, classLoader));
    }

    /**
     * Return whether one type is assignable to the other.
     *
     * @param superclasses The superclasses to check, may be {@code null}
     * @param subclasses The given subclasses
     *
     * @return Whether the condition holds
     */
    @SuppressWarnings("DataFlowIssue")
    public static boolean isAssignable(@Nullable Class<?>[] superclasses, @Nullable Class<?>[] subclasses) {
        if (ArrayUtils.isEmpty(superclasses) && ArrayUtils.isEmpty(subclasses)) {
            return true;
        }
        if (!ArrayUtils.isSameLength(superclasses, subclasses)) {
            return false;
        }
        for (int i = 0; i < superclasses.length; i++) {
            if (!isAssignable(superclasses[i], subclasses[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return whether the given value is assignable to the expected type.
     *
     * @param superclass The superclass to check, may be {@code null}
     * @param value The property value, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isAssignableValue(@Nullable Class<?> superclass, @Nullable Object value) {
        return superclass != null && ClassUtils.isAssignableValue(superclass, value);
    }

    /**
     * Return whether the given value is assignable to the expected type.
     *
     * @param superclassName The given superclassName
     * @param value The property value, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isAssignableValue(@Nullable String superclassName, @Nullable Object value) {
        return isAssignableValue(superclassName, value, null);
    }

    /**
     * Return whether the given value is assignable to the expected type.
     *
     * @param superclassName The given superclassName
     * @param value The property value, may be {@code null}
     * @param classLoader The class loader, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isAssignableValue(@Nullable String superclassName, @Nullable Object value, @Nullable ClassLoader classLoader) {
        Class<?> superclass = forNameQuietly(superclassName, classLoader);
        return superclass != null && ClassUtils.isAssignableValue(superclass, value);
    }

    /**
     * Return whether the class is an inner class.
     *
     * @param clazz The class to use, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isInnerClass(@Nullable Class<?> clazz) {
        return clazz != null && ClassUtils.isInnerClass(clazz);
    }

    /**
     * Return whether the class is a primitive wrapper.
     *
     * @param clazz The class to use, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isPrimitiveWrapper(@Nullable Class<?> clazz) {
        return clazz != null && ClassUtils.isPrimitiveWrapper(clazz);
    }

    /**
     * Return whether the class is primitive or a primitive wrapper.
     *
     * @param clazz The class to use, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isPrimitiveOrWrapper(@Nullable Class<?> clazz) {
        return clazz != null && ClassUtils.isPrimitiveOrWrapper(clazz);
    }

    /**
     * Return whether the class is an array of primitives.
     *
     * @param clazz The class to use, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isPrimitiveArray(@Nullable Class<?> clazz) {
        return clazz != null && ClassUtils.isPrimitiveArray(clazz);
    }

    /**
     * Return whether the class is an array of primitive wrappers.
     *
     * @param clazz The class to use, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isPrimitiveWrapperArray(@Nullable Class<?> clazz) {
        return clazz != null && ClassUtils.isPrimitiveWrapperArray(clazz);
    }
}
