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


import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.ObjectProvider;
import com.jiovan.plexus.common.toolkit.util.IteratorPlainWraps;


/**
 * Utilities for {@link org.springframework.beans.factory.ObjectProvider}
 *
 * @author David Hsing
 *
 * @see org.springframework.beans.factory.ObjectProvider
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class ObjectProviderWraps {
    /**
     * Return the object if one is available.
     *
     * @param provider The given provider
     *
     * @return The result of the operation
     */
    @Nullable
    public static <E> E getIfAvailable(@Nullable ObjectProvider<E> provider) {
        return getIfAvailable(provider, null);
    }

    /**
     * Return the object if one is available.
     *
     * @param provider The given provider
     * @param supplier The value supplier, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static <E> E getIfAvailable(@Nullable ObjectProvider<E> provider, @Nullable Supplier<E> supplier) {
        if (provider == null) {
            return null;
        }
        try {
            return (supplier == null) ? provider.getIfAvailable() : provider.getIfAvailable(supplier);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Return the object if it is unique.
     *
     * @param provider The given provider
     *
     * @return The result of the operation
     */
    @Nullable
    public static <E> E getIfUnique(@Nullable ObjectProvider<E> provider) {
        return getIfUnique(provider, null);
    }

    /**
     * Return the object if it is unique.
     *
     * @param provider The given provider
     * @param supplier The value supplier, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static <E> E getIfUnique(@Nullable ObjectProvider<E> provider, @Nullable Supplier<E> supplier) {
        if (provider == null) {
            return null;
        }
        try {
            return (supplier == null) ? provider.getIfUnique() : provider.getIfUnique(supplier);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Return an iterator over the available objects.
     *
     * @param provider The given provider
     *
     * @return An iterator over the available objects, or {@code null}
     */
    @Nullable
    public static <E> Iterator<E> getIterator(@Nullable ObjectProvider<E> provider) {
        try {
            return (provider == null) ? null : provider.iterator();
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Run the action when an object is available.
     *
     * @param provider The given provider
     * @param action The action, may be {@code null}
     */
    public static <E, T extends ObjectProvider<E>> void ifAvailable(@Nullable T provider, @Nullable Consumer<E> action) {
        ifAvailable(provider, action, null);
    }

    /**
     * Run the action when an object is available.
     *
     * @param provider The given provider
     * @param action The action, may be {@code null}
     * @param filter The filter predicate, may be {@code null}
     */
    public static <E, T extends ObjectProvider<E>> void ifAvailable(@Nullable T provider, @Nullable Consumer<E> action, @Nullable Predicate<E> filter) {
        if (provider == null || action == null) {
            return;
        }
        E value = getIfAvailable(provider);
        if (value != null && (filter == null || filter.test(value))) {
            action.accept(value);
        }
    }

    /**
     * Run the action when an object is available.
     *
     * @param provider The given provider
     * @param action The action, may be {@code null}
     */
    public static <T extends ObjectProvider<?>> void ifAvailable(@Nullable T provider, @Nullable Runnable action) {
        if (isAvailable(provider) && action != null) {
            action.run();
        }
    }

    /**
     * If a value is available, performs the given action with the value, otherwise performs the given empty-based action
     *
     * @param presentAction The action to be performed, if a value is available
     * @param absentAction The empty-based action to be performed, if no value is available
     */
    public static <E, T extends ObjectProvider<E>> void ifAvailableOrElse(@Nullable T provider, @Nullable Consumer<E> presentAction, @Nullable Runnable absentAction) {
        E value = getIfAvailable(provider);
        if (value != null) {
              if (presentAction != null) {
                 presentAction.accept(value);
             }
        } else {
             if (absentAction != null) {
                absentAction.run();
            }
        }
    }

    /**
     * Run the present or absent action based on availability.
     *
     * @param provider The given provider
     * @param presentAction The given presentAction
     * @param absentAction The given absentAction
     */
    public static <T extends ObjectProvider<?>> void ifAvailableOrElse(@Nullable T provider, @Nullable Runnable presentAction, @Nullable Runnable absentAction) {
        if (isAvailable(provider)) {
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
     * Run the action when no object is available.
     *
     * @param provider The given provider
     * @param action The action, may be {@code null}
     */
    public static <T extends ObjectProvider<?>> void ifNotAvailable(@Nullable T provider, @Nullable Runnable action) {
        if (!isAvailable(provider) && action != null) {
            action.run();
        }
    }

    /**
     * Run the action when multiple objects are available.
     *
     * @param provider The given provider
     * @param action The action, may be {@code null}
     */
    public static <E, T extends ObjectProvider<E>> void ifMultitude(@Nullable T provider, @Nullable Consumer<Iterator<E>> action) {
        ifMultitudeOrElse(provider, action, null);
    }

    /**
     * Run the action when multiple objects are available.
     *
     * @param provider The given provider
     * @param action The action, may be {@code null}
     */
    public static <E, T extends ObjectProvider<E>> void ifMultitude(@Nullable T provider, @Nullable Runnable action) {
        ifMultitudeOrElse(provider, action, null);
    }

    /**
     * Run the present or absent action based on multiplicity.
     *
     * @param provider The given provider
     * @param presentAction The given presentAction
     * @param absentAction The given absentAction
     */
    public static <E, T extends ObjectProvider<E>> void ifMultitudeOrElse(@Nullable T provider, @Nullable Consumer<Iterator<E>> presentAction, @Nullable Consumer<E> absentAction) {
        if (provider == null) {
            return;
        }
        if (isMultitude(provider)) {
             if (presentAction != null) {
                presentAction.accept(provider.iterator());
            }
        } else {
            if (absentAction != null) {
                absentAction.accept(getIfAvailable(provider));
            }
        }
    }

    /**
     * Run the present or absent action based on multiplicity.
     *
     * @param provider The given provider
     * @param presentAction The given presentAction
     * @param absentAction The given absentAction
     */
    public static <E, T extends ObjectProvider<E>> void ifMultitudeOrElse(@Nullable T provider, @Nullable Runnable presentAction, @Nullable Runnable absentAction) {
        if (provider == null) {
            return;
        }
        if (isMultitude(provider)) {
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
     * Run the action when the object is unique.
     *
     * @param provider The given provider
     * @param action The action, may be {@code null}
     */
    public static <E, T extends ObjectProvider<E>> void ifUnique(@Nullable T provider, @Nullable Consumer<E> action) {
        ifUnique(provider, action, null);
    }

    /**
     * Run the action when the object is unique.
     *
     * @param provider The given provider
     * @param action The action, may be {@code null}
     * @param filter The filter predicate, may be {@code null}
     */
    public static <E, T extends ObjectProvider<E>> void ifUnique(@Nullable T provider, @Nullable Consumer<E> action, @Nullable Predicate<E> filter) {
        if (provider == null || action == null) {
            return;
        }
        E value = getIfUnique(provider);
        if (value != null && (filter == null || filter.test(value))) {
            action.accept(value);
        }
    }

    /**
     * Run the action when the object is unique.
     *
     * @param provider The given provider
     * @param action The action, may be {@code null}
     */
    public static <T extends ObjectProvider<?>> void ifUnique(@Nullable T provider, @Nullable Runnable action) {
        if (isUnique(provider) && action != null) {
            action.run();
        }
    }

    /**
     * If a value is unique, performs the given action with the value, otherwise performs the given empty-based action
     *
     * @param presentAction The action to be performed, if a value is unique
     * @param absentAction The empty-based action to be performed, if no value is unique
     */
    public static <E, T extends ObjectProvider<E>> void ifUniqueOrElse(@Nullable T provider, @Nullable Consumer<E> presentAction, @Nullable Consumer<Iterator<E>> absentAction) {
        E value = getIfUnique(provider);
        if (value != null) {
             if (presentAction != null) {
                presentAction.accept(value);
            }
        } else {
             if (absentAction != null) {
                absentAction.accept(getIterator(provider));
            }
        }
    }

    /**
     * Run the present or absent action based on uniqueness.
     *
     * @param provider The given provider
     * @param presentAction The given presentAction
     * @param absentAction The given absentAction
     */
    public static <T extends ObjectProvider<?>> void ifUniqueOrElse(@Nullable T provider, @Nullable Runnable presentAction, @Nullable Runnable absentAction) {
        if (isUnique(provider)) {
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
     * Run the action when the object is not unique.
     *
     * @param provider The given provider
     * @param action The action, may be {@code null}
     */
    public static <E, T extends ObjectProvider<E>> void ifNotUnique(@Nullable T provider, @Nullable Consumer<T> action) {
        if (!isUnique(provider) && action != null) {
            action.accept(provider);
        }
    }

    /**
     * Run the action when the object is not unique.
     *
     * @param provider The given provider
     * @param action The action, may be {@code null}
     */
    public static <T extends ObjectProvider<?>> void ifNotUnique(@Nullable T provider, @Nullable Runnable action) {
        if (!isUnique(provider) && action != null) {
            action.run();
        }
    }

    /**
     * Return whether an object is available.
     *
     * @param provider The given provider
     *
     * @return Whether the condition holds
     */
    public static boolean isAvailable(@Nullable ObjectProvider<?> provider) {
        if (provider == null) {
            return false;
        }
        try {
            return provider.getIfAvailable() != null;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Return whether the provider is empty.
     *
     * @param provider The given provider
     *
     * @return Whether the condition holds
     */
    public static boolean isEmpty(@Nullable ObjectProvider<?> provider) {
        try {
            return provider == null || IteratorPlainWraps.isEmpty(provider.iterator());
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Return whether the provider is not empty.
     *
     * @param provider The given provider
     *
     * @return Whether the condition holds
     */
    public static boolean isNotEmpty(@Nullable ObjectProvider<?> provider) {
        return !isEmpty(provider);
    }

    /**
     * Return whether the provider holds multiple objects.
     *
     * @param provider The given provider
     *
     * @return Whether the condition holds
     */
    public static boolean isMultitude(@Nullable ObjectProvider<?> provider) {
        if (provider == null) {
            return false;
        }
        try {
            return IteratorPlainWraps.isMultitude(provider.iterator());
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * Return whether the object is unique.
     *
     * @param provider The given provider
     *
     * @return Whether the condition holds
     */
    public static boolean isUnique(@Nullable ObjectProvider<?> provider) {
        if (provider == null) {
            return false;
        }
        try {
            return provider.getIfUnique() != null;
        } catch (Exception ignored) {
        }
        return false;
    }
}
