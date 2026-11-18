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


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.FileCopyUtils;


/**
 * Utilities for {@link org.springframework.util.FileCopyUtils}
 *
 * @author David Hsing
 *
 * @see org.springframework.util.FileCopyUtils
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class FileCopyWraps {
    /**
     * Copy content from the input to the output, ignoring failures.
     *
     * @param input The input source, may be {@code null}
     * @param output The output target, may be {@code null}
     *
     * @return The number of copied items, or zero on failure
     */
    public static int copy(@Nullable File input, @Nullable File output) {
        if (ObjectUtils.anyNull(input, output)) {
            return 0;
        }
        try {
            return FileCopyUtils.copy(input, output);
        } catch (Exception ignored) {
        }
        return 0;
    }

    /**
     * Copy content from the input to the output, ignoring failures.
     *
     * @param input The input source, may be {@code null}
     * @param output The output target, may be {@code null}
     */
    public static void copy(@Nullable byte[] input, @Nullable OutputStream output) {
        if (ObjectUtils.anyNull(input, output)) {
            return;
        }
        try {
            FileCopyUtils.copy(input, output);
        } catch (Exception ignored) {
        }
    }

    /**
     * Copy content from the input to the output, ignoring failures.
     *
     * @param input The input source, may be {@code null}
     * @param output The output target, may be {@code null}
     *
     * @return The number of copied items, or zero on failure
     */
    public static int copy(@Nullable Reader input, @Nullable Writer output) {
        if (ObjectUtils.anyNull(input, output)) {
            return 0;
        }
        try {
            return FileCopyUtils.copy(input, output);
        } catch (Exception ignored) {
        }
        return 0;
    }

    /**
     * Copy content from the input to the output, ignoring failures.
     *
     * @param input The input source, may be {@code null}
     * @param output The output target, may be {@code null}
     */
    public static void copy(@Nullable String input, @Nullable Writer output) {
        if (ObjectUtils.anyNull(input, output)) {
            return;
        }
        try {
            FileCopyUtils.copy(input, output);
        } catch (Exception ignored) {
        }
    }

    /**
     * Copy content from the input to the output, ignoring failures.
     *
     * @param input The input source, may be {@code null}
     * @param output The output target, may be {@code null}
     */
    public static void copy(@Nullable byte[] input, @Nullable File output) {
        if (ObjectUtils.anyNull(input, output)) {
            return;
        }
        try {
            FileCopyUtils.copy(input, output);
        } catch (Exception ignored) {
        }
    }

    /**
     * Copy content from the input to the output, ignoring failures.
     *
     * @param input The input source, may be {@code null}
     * @param output The output target, may be {@code null}
     *
     * @return The number of copied items, or zero on failure
     */
    public static int copy(@Nullable InputStream input, @Nullable OutputStream output) {
        if (ObjectUtils.anyNull(input, output)) {
            return 0;
        }
        try {
            return FileCopyUtils.copy(input, output);
        } catch (Exception ignored) {
        }
        return 0;
    }

    /**
     * Read the entire reader content into a string.
     *
     * @param input The input source, may be {@code null}
     *
     * @return The reader content, or {@code null}
     *
     * @throws IOException If the operation fails
     */
    @Nullable
    public static String copyToString(@Nullable Reader input) throws IOException {
        return (input == null) ? null : FileCopyUtils.copyToString(input);
    }

    /**
     * Read the reader content into a string, returning {@code null} on failure.
     *
     * @param input The input source, may be {@code null}
     *
     * @return The reader content, or {@code null}
     */
    @Nullable
    public static String copyToStringQuietly(@Nullable Reader input) {
        try {
            return copyToString(input);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Read the entire input stream into a byte array.
     *
     * @param input The input source, may be {@code null}
     *
     * @return The stream content, or {@code null}
     *
     * @throws IOException If the operation fails
     */
    @Nullable
    public static byte[] copyToByteArray(@Nullable InputStream input) throws IOException {
        return (input == null) ? null : FileCopyUtils.copyToByteArray(input);
    }

    /**
     * Read the input into a byte array, returning {@code null} on failure.
     *
     * @param input The input source, may be {@code null}
     *
     * @return The input content, or {@code null}
     */
    @Nullable
    public static byte[] copyToByteArrayQuietly(@Nullable InputStream input) {
        try {
            return copyToByteArray(input);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Read the input into a byte array, returning {@code null} on failure.
     *
     * @param input The input source, may be {@code null}
     *
     * @return The input content, or {@code null}
     */
    @Nullable
    public static byte[] copyToByteArrayQuietly(@Nullable File input) {
        if (input == null) {
            return null;
        }
        try {
            return FileCopyUtils.copyToByteArray(input);
        } catch (Exception ignored) {
        }
        return null;
    }
}
