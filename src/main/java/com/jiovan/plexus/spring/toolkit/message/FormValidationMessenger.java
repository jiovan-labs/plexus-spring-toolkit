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

package com.jiovan.plexus.spring.toolkit.message;


import java.util.Locale;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import com.jiovan.plexus.spring.toolkit.structure.FormValidationStruct;
import com.jiovan.plexus.spring.toolkit.util.MessageSourceWraps;
import lombok.RequiredArgsConstructor;


/**
 * {@link org.springframework.context.support.MessageSourceAccessor} for resolving FormValidation messages
 *
 * @author David Hsing
 *
 * @see org.springframework.context.MessageSource
 * @see org.springframework.context.support.MessageSourceAccessor
 *
 * @see "https://formvalidation.io/"
 * @see "https://old.formvalidation.io/"
 */
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class FormValidationMessenger {
    /**
     * The message source used for resolving localized messages.
     */
    private final MessageSource messageSource;

    /**
     * Create a valid form validation response with no message.
     *
     * @return A valid form validation struct with an unset message
     */
    @Nonnull
    public FormValidationStruct newValid() {
        return newValid(null, null, null, null);
    }

    /**
     * Create a valid form validation response using the given message code.
     *
     * @param code The localized message code key to look up, may be {@code null}
     *
     * @return A valid form validation struct containing the resolved message
     */
    @Nonnull
    public FormValidationStruct newValid(@Nullable String code) {
        return newValid(code, null, null, null);
    }

    /**
     * Create a valid form validation response with a message code and arguments.
     *
     * @param code The localized message code key to look up, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     *
     * @return A valid form validation struct containing the resolved message
     */
    @Nonnull
    public FormValidationStruct newValid(@Nullable String code, @Nullable Object[] args) {
        return newValid(code, args, null, null);
    }

    /**
     * Create a valid form validation response with a fallback default message.
     *
     * @param code The localized message code key to look up, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     *
     * @return A valid form validation struct containing the resolved message
     */
    @Nonnull
    public FormValidationStruct newValid(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        return newValid(code, args, defaultMessage, null);
    }

    /**
     * Create a valid form validation response with full customization.
     *
     * @param code The localized message code key to look up
     * @param args Arguments for message formatting
     * @param defaultMessage Fallback default message if lookup fails
     * @param locale The locale for message resolution
     *
     * @return A valid form validation struct containing the resolved message
     */
    @Nonnull
    public FormValidationStruct newValid(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        FormValidationStruct struct = new FormValidationStruct();
        setValid(struct, code, args, defaultMessage, locale);
        return struct;
    }

    /**
     * Create an invalid form validation response with no message.
     *
     * @return An invalid form validation struct with an unset message
     */
    @Nonnull
    public FormValidationStruct newInvalid() {
        return newInvalid(null, null, null, null);
    }

    /**
     * Create an invalid form validation response using the given message code.
     *
     * @param code The localized message code key to look up, may be {@code null}
     *
     * @return An invalid form validation struct containing the resolved message
     */
    @Nonnull
    public FormValidationStruct newInvalid(@Nullable String code) {
        return newInvalid(code, null, null, null);
    }

    /**
     * Create an invalid form validation response with a message code and arguments.
     *
     * @param code The localized message code key to look up, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     *
     * @return An invalid form validation struct containing the resolved message
     */
    @Nonnull
    public FormValidationStruct newInvalid(@Nullable String code, @Nullable Object[] args) {
        return newInvalid(code, args, null, null);
    }

    /**
     * Create an invalid form validation response with a fallback default message.
     *
     * @param code The localized message code key to look up, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     *
     * @return An invalid form validation struct containing the resolved message
     */
    @Nonnull
    public FormValidationStruct newInvalid(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        return newInvalid(code, args, defaultMessage, null);
    }

    /**
     * Create an invalid form validation response with full customization.
     *
     * @param code The localized message code key to look up
     * @param args Arguments for message formatting
     * @param defaultMessage Fallback default message if lookup fails
     * @param locale The locale for message resolution
     *
     * @return An invalid form validation struct containing the resolved message
     */
    @Nonnull
    public FormValidationStruct newInvalid(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        FormValidationStruct struct = new FormValidationStruct();
        setInvalid(struct, code, args, defaultMessage, locale);
        return struct;
    }

    /**
     * Set a form validation struct to valid state with no message resolution.
     *
     * @param struct The struct to update
     */
    public void setValid(@Nonnull FormValidationStruct struct) {
        setValid(struct, null, null, null, null);
    }

    /**
     * Set a form validation struct to valid state using the given message code.
     *
     * @param struct The struct to update
     * @param code The localized message code key to look up, may be {@code null}
     */
    public void setValid(@Nonnull FormValidationStruct struct, @Nullable String code) {
        setValid(struct, code, null, null, null);
    }

    /**
     * Set a form validation struct to valid state with a message code and arguments.
     *
     * @param struct The struct to update
     * @param code The localized message code key to look up, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     */
    public void setValid(@Nonnull FormValidationStruct struct, @Nullable String code, @Nullable Object[] args) {
        setValid(struct, code, args, null, null);
    }

    /**
     * Set a form validation struct to valid state with a fallback default message.
     *
     * @param struct The struct to update
     * @param code The localized message code key to look up, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     */
    public void setValid(@Nonnull FormValidationStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        setValid(struct, code, args, defaultMessage, null);
    }

    /**
     * Set a form validation struct to valid state and resolve its message.
     *
     * @param struct The struct to update
     * @param code The localized message code key to look up
     * @param args Arguments for message formatting
     * @param defaultMessage Fallback default message if lookup fails
     * @param locale The locale for message resolution
     */
    public void setValid(@Nonnull FormValidationStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        struct.setValid(true);
        setMessage(struct, code, args, defaultMessage, locale);
    }

    /**
     * Set a form validation struct to invalid state with no message resolution.
     *
     * @param struct The struct to update
     */
    public void setInvalid(@Nonnull FormValidationStruct struct) {
        setInvalid(struct, null, null, null, null);
    }

    /**
     * Set a form validation struct to invalid state using the given message code.
     *
     * @param struct The struct to update
     * @param code The localized message code key to look up, may be {@code null}
     */
    public void setInvalid(@Nonnull FormValidationStruct struct, @Nullable String code) {
        setInvalid(struct, code, null, null, null);
    }

    /**
     * Set a form validation struct to invalid state with a message code and arguments.
     *
     * @param struct The struct to update
     * @param code The localized message code key to look up, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     */
    public void setInvalid(@Nonnull FormValidationStruct struct, @Nullable String code, @Nullable Object[] args) {
        setInvalid(struct, code, args, null, null);
    }

    /**
     * Set a form validation struct to invalid state with a fallback default message.
     *
     * @param struct The struct to update
     * @param code The localized message code key to look up, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     */
    public void setInvalid(@Nonnull FormValidationStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        setInvalid(struct, code, args, defaultMessage, null);
    }

    /**
     * Set a form validation struct to invalid state and resolve its message.
     *
     * @param struct The struct to update
     * @param code The localized message code key to look up
     * @param args Arguments for message formatting
     * @param defaultMessage Fallback default message if lookup fails
     * @param locale The locale for message resolution
     */
    public void setInvalid(@Nonnull FormValidationStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        struct.setValid(false);
        setMessage(struct, code, args, defaultMessage, locale);
    }

    /**
     * Resolve and set the localized message using the given code.
     *
     * @param struct The struct whose message field will be updated
     * @param code The localized message code key to look up, may be {@code null}
     */
    public void setMessage(@Nonnull FormValidationStruct struct, @Nullable String code) {
        setMessage(struct, code, null, null);
    }

    /**
     * Resolve and set the localized message using the given code and arguments.
     *
     * @param struct The struct whose message field will be updated
     * @param code The localized message code key to look up, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     */
    public void setMessage(@Nonnull FormValidationStruct struct, @Nullable String code, @Nullable Object[] args) {
        setMessage(struct, code, args, null);
    }

    /**
     * Resolve and set the localized message with a fallback default message.
     *
     * @param struct The struct whose message field will be updated
     * @param code The localized message code key to look up, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message when code is blank, may be {@code null}
     */
    public void setMessage(@Nonnull FormValidationStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        setMessage(struct, code, args, defaultMessage, null);
    }

    /**
     * Resolve and set the localized message into a form validation struct.
     *
     * @param struct The struct whose message field will be updated
     * @param code The localized message code key to look up
     * @param args Arguments for message formatting
     * @param defaultMessage Fallback default message when code is blank or lookup fails
     * @param locale The locale for message resolution
     */
    public void setMessage(@Nonnull FormValidationStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        if (StringUtils.isBlank(code)) {
            struct.setMessage(defaultMessage);
        } else {
            struct.setMessage(MessageSourceWraps.getMessageLookup(messageSource, code, args, defaultMessage, locale));
        }
    }
}
