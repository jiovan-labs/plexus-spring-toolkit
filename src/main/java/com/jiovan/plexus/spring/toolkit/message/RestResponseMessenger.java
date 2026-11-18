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


import java.util.List;
import java.util.Locale;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import com.jiovan.plexus.common.toolkit.constant.AssertMessageConst;
import com.jiovan.plexus.common.toolkit.constant.SymbolVariantConst;
import com.jiovan.plexus.common.toolkit.structure.BooleanDataStruct;
import com.jiovan.plexus.common.toolkit.structure.BooleanTextStruct;
import com.jiovan.plexus.common.toolkit.util.HtmlEscapeWraps;
import com.jiovan.plexus.spring.toolkit.constant.ResponseBodyConst;
import com.jiovan.plexus.spring.toolkit.constant.RestMessageConst;
import com.jiovan.plexus.spring.toolkit.structure.RestResponseStruct;
import com.jiovan.plexus.spring.toolkit.util.MessageSourceWraps;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


/**
 * {@link org.springframework.context.support.MessageSourceAccessor} for resolving REST response messages
 *
 * @author David Hsing
 *
 * @see org.springframework.context.MessageSource
 * @see org.springframework.context.support.MessageSourceAccessor
 * @see com.jiovan.plexus.spring.toolkit.structure.RestResponseStruct
 */
@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
@SuppressWarnings("unused")
public class RestResponseMessenger {
    /**
     * The message source used for resolving localized messages.
     */
    private final MessageSource messageSource;

    /**
     * Optional helper for resolving bean validation binding errors.
     */
    private BeanValidationMessenger messenger;

    /**
     * The status code key used for illegal request responses.
     */
    @Getter
    @Setter
    private String illegalCode = RestMessageConst.ILLEGAL_REQUEST;

    /**
     * The status code key used for successful operation responses.
     */
    @Getter
    @Setter
    private String successCode = RestMessageConst.OPERATE_SUCCESS;

    /**
     * The status code key used for failed operation responses.
     */
    @Getter
    @Setter
    private String failureCode = RestMessageConst.OPERATE_FAILURE;

    /**
     * The status code key used for refused operation responses.
     */
    @Getter
    @Setter
    private String refusalCode = RestMessageConst.OPERATE_REFUSAL;

    /**
     * The status code key used for timed-out operation responses.
     */
    @Getter
    @Setter
    private String timeoutCode = RestMessageConst.OPERATE_TIMEOUT;

    /**
     * Create a messenger with an explicit bean validation helper instance.
     *
     * @param source The message source for localized messages
     * @param messenger A pre-configured bean validation messenger, may be {@code null} to disable binding error resolution
     */
    public RestResponseMessenger(@Nonnull MessageSource source, @Nullable BeanValidationMessenger messenger) {
        this.messageSource = source;
        this.messenger = messenger;
    }

    /**
     * Create a messenger that internally configures a bean validation helper from the given validator.
     *
     * @param source The message source for localized messages
     * @param validator The Jakarta validator to use for bean validation, may be {@code null} to disable binding error resolution
     */
    public RestResponseMessenger(@Nonnull MessageSource source, @Nullable Validator validator) {
        this.messageSource = source;
        if (validator != null) {
            messenger = new BeanValidationMessenger(source, validator);
        }
    }

    /**
     * Convert a boolean data struct into a REST response using default success/failure codes.
     *
     * @param struct The boolean data struct containing status and optional data
     *
     * @return A REST response struct populated with status and resolved message
     */
    @Nonnull
    public RestResponseStruct fromBooleanData(@Nonnull BooleanDataStruct<?> struct) {
        RestResponseStruct result = RestResponseStruct.ofBooleanData(struct);
        if (StringUtils.isEmpty(result.getMessage())) {
            setMessage(result, struct.isSuccess() ? successCode : failureCode);
        }
        return result;
    }

    /**
     * Convert a boolean data struct into a REST response using a char delimiter for multiple messages.
     *
     * @param struct The boolean data struct containing status and optional data
     * @param delimiter A character used to join multiple messages
     *
     * @return A REST response struct populated with status and resolved message
     */
    @Nonnull
    public RestResponseStruct fromBooleanData(@Nonnull BooleanDataStruct<?> struct, char delimiter) {
        RestResponseStruct result = RestResponseStruct.ofBooleanData(struct, delimiter);
        if (StringUtils.isEmpty(result.getMessage())) {
            setMessage(result, struct.isSuccess() ? successCode : failureCode);
        }
        return result;
    }

    /**
     * Convert a boolean data struct into a REST response using a string delimiter for multiple messages.
     *
     * @param struct The boolean data struct containing status and optional data
     * @param delimiter A string used to join multiple messages, may be {@code null}
     *
     * @return A REST response struct populated with status and resolved message
     */
    @Nonnull
    public RestResponseStruct fromBooleanData(@Nonnull BooleanDataStruct<?> struct, @Nullable String delimiter) {
        RestResponseStruct result = RestResponseStruct.ofBooleanData(struct, delimiter);
        if (StringUtils.isEmpty(result.getMessage())) {
            setMessage(result, struct.isSuccess() ? successCode : failureCode);
        }
        return result;
    }

    /**
     * Convert a boolean data struct without embedded data into a REST response.
     *
     * @param struct The boolean data struct containing only status information
     *
     * @return A REST response struct populated with status and resolved message, without data
     */
    @Nonnull
    public RestResponseStruct fromBooleanDataWithout(@Nonnull BooleanDataStruct<?> struct) {
        RestResponseStruct result = RestResponseStruct.ofBooleanDataWithout(struct);
        if (StringUtils.isEmpty(result.getMessage())) {
            setMessage(result, struct.isSuccess() ? successCode : failureCode);
        }
        return result;
    }

    /**
     * Convert a boolean data struct without embedded data into a REST response using a char delimiter.
     *
     * @param struct The boolean data struct containing only status information
     * @param delimiter A character used to join multiple messages
     *
     * @return A REST response struct populated with status and resolved message, without data
     */
    @Nonnull
    public RestResponseStruct fromBooleanDataWithout(@Nonnull BooleanDataStruct<?> struct, char delimiter) {
        RestResponseStruct result = RestResponseStruct.ofBooleanDataWithout(struct, delimiter);
        if (StringUtils.isEmpty(result.getMessage())) {
            setMessage(result, struct.isSuccess() ? successCode : failureCode);
        }
        return result;
    }

    /**
     * Convert a boolean data struct without embedded data into a REST response using a string delimiter.
     *
     * @param struct The boolean data struct containing only status information
     * @param delimiter A string used to join multiple messages, may be {@code null}
     *
     * @return A REST response struct populated with status and resolved message, without data
     */
    @Nonnull
    public RestResponseStruct fromBooleanDataWithout(@Nonnull BooleanDataStruct<?> struct, @Nullable String delimiter) {
        RestResponseStruct result = RestResponseStruct.ofBooleanDataWithout(struct, delimiter);
        if (StringUtils.isEmpty(result.getMessage())) {
            setMessage(result, struct.isSuccess() ? successCode : failureCode);
        }
        return result;
    }

    /**
     * Convert a boolean text struct into a REST response using default success/failure codes.
     *
     * @param struct The boolean text struct containing status and optional message
     *
     * @return A REST response struct populated with status and resolved message
     */
    @Nonnull
    public RestResponseStruct fromBooleanText(@Nonnull BooleanTextStruct struct) {
        RestResponseStruct result = RestResponseStruct.ofBooleanText(struct);
        if (StringUtils.isEmpty(result.getMessage())) {
            setMessage(result, struct.isSuccess() ? successCode : failureCode);
        }
        return result;
    }

    /**
     * Convert a boolean text struct into a REST response using a char delimiter for multiple messages.
     *
     * @param struct The boolean text struct containing status and optional message
     * @param delimiter A character used to join multiple messages
     *
     * @return A REST response struct populated with status and resolved message
     */
    @Nonnull
    public RestResponseStruct fromBooleanText(@Nonnull BooleanTextStruct struct, char delimiter) {
        RestResponseStruct result = RestResponseStruct.ofBooleanText(struct, delimiter);
        if (StringUtils.isEmpty(result.getMessage())) {
            setMessage(result, struct.isSuccess() ? successCode : failureCode);
        }
        return result;
    }

    /**
     * Convert a boolean text struct into a REST response using a string delimiter for multiple messages.
     *
     * @param struct The boolean text struct containing status and optional message
     * @param delimiter A string used to join multiple messages, may be {@code null}
     *
     * @return A REST response struct populated with status and resolved message
     */
    @Nonnull
    public RestResponseStruct fromBooleanText(@Nonnull BooleanTextStruct struct, @Nullable String delimiter) {
        RestResponseStruct result = RestResponseStruct.ofBooleanText(struct, delimiter);
        if (StringUtils.isEmpty(result.getMessage())) {
            setMessage(result, struct.isSuccess() ? successCode : failureCode);
        }
        return result;
    }

    /**
     * Create an illegal response using the default illegal code.
     *
     * @return A REST response struct with illegal status
     */
    @Nonnull
    public RestResponseStruct newIllegal() {
        return newIllegal(illegalCode, null, null, null);
    }

    /**
     * Create an illegal response with a custom message code.
     *
     * @param code The localized message code key, may be {@code null}
     *
     * @return A REST response struct with illegal status
     */
    @Nonnull
    public RestResponseStruct newIllegal(@Nullable String code) {
        return newIllegal(code, null, null, null);
    }

    /**
     * Create an illegal response with a message code and arguments.
     *
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     *
     * @return A REST response struct with illegal status
     */
    @Nonnull
    public RestResponseStruct newIllegal(@Nullable String code, @Nullable Object[] args) {
        return newIllegal(code, args, null, null);
    }

    /**
     * Create an illegal response with a fallback default message.
     *
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     *
     * @return A REST response struct with illegal status
     */
    @Nonnull
    public RestResponseStruct newIllegal(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        return newIllegal(code, args, defaultMessage, null);
    }

    /**
     * Create an illegal response with full message customization.
     *
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     * @param locale The locale for message resolution, may be {@code null}
     *
     * @return A REST response struct with illegal status
     */
    @Nonnull
    public RestResponseStruct newIllegal(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        RestResponseStruct struct = new RestResponseStruct();
        setIllegal(struct, code, args, defaultMessage, locale);
        return struct;
    }

    /**
     * Create a success response using the default success code.
     *
     * @return A REST response struct with success status
     */
    @Nonnull
    public RestResponseStruct newSuccess() {
        return newSuccess(successCode, null, null, null);
    }

    /**
     * Create a success response with a custom message code.
     *
     * @param code The localized message code key, may be {@code null}
     *
     * @return A REST response struct with success status
     */
    @Nonnull
    public RestResponseStruct newSuccess(@Nullable String code) {
        return newSuccess(code, null, null, null);
    }

    /**
     * Create a success response with a message code and arguments.
     *
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     *
     * @return A REST response struct with success status
     */
    @Nonnull
    public RestResponseStruct newSuccess(@Nullable String code, @Nullable Object[] args) {
        return newSuccess(code, args, null, null);
    }

    /**
     * Create a success response with a fallback default message.
     *
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     *
     * @return A REST response struct with success status
     */
    @Nonnull
    public RestResponseStruct newSuccess(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        return newSuccess(code, args, defaultMessage, null);
    }

    /**
     * Create a success response with full message customization.
     *
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     * @param locale The locale for message resolution, may be {@code null}
     *
     * @return A REST response struct with success status
     */
    @Nonnull
    public RestResponseStruct newSuccess(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        RestResponseStruct struct = new RestResponseStruct();
        setSuccess(struct, code, args, defaultMessage, locale);
        return struct;
    }

    /**
     * Create a success response containing data, using the default success code.
     *
     * @param data The payload data to include, may be {@code null}
     *
     * @return A REST response struct with success status and data populated
     */
    @Nonnull
    public RestResponseStruct newSuccessWithin(@Nullable Object data) {
        return newSuccessWithin(data, successCode, null, null, null);
    }

    /**
     * Create a success response containing data with a custom success code.
     *
     * @param data The payload data to include, may be {@code null}
     * @param successCode The localized message code key for the success message
     *
     * @return A REST response struct with success status and data populated
     */
    @Nonnull
    public RestResponseStruct newSuccessWithin(@Nullable Object data, @Nonnull String successCode) {
        return newSuccessWithin(data, successCode, null, null, null);
    }

    /**
     * Create a success response containing data with a success code and arguments.
     *
     * @param data The payload data to include, may be {@code null}
     * @param successCode The localized message code key for the success message
     * @param successArgs Arguments for message formatting, may be {@code null}
     *
     * @return A REST response struct with success status and data populated
     */
    @Nonnull
    public RestResponseStruct newSuccessWithin(@Nullable Object data, @Nonnull String successCode, @Nullable Object[] successArgs) {
        return newSuccessWithin(data, successCode, successArgs, null, null);
    }

    /**
     * Create a success response containing data with a fallback default message.
     *
     * @param data The payload data to include, may be {@code null}
     * @param successCode The localized message code key for the success message
     * @param successArgs Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     *
     * @return A REST response struct with success status and data populated
     */
    @Nonnull
    public RestResponseStruct newSuccessWithin(@Nullable Object data, @Nonnull String successCode, @Nullable Object[] successArgs, @Nullable String defaultMessage) {
        return newSuccessWithin(data, successCode, successArgs, defaultMessage, null);
    }

    /**
     * Create a success response containing data with full message customization.
     *
     * @param data The payload data to include, may be {@code null}
     * @param successCode The localized message code key for the success message
     * @param successArgs Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     * @param locale The locale for message resolution, may be {@code null}
     *
     * @return A REST response struct with success status and data populated
     */
    @Nonnull
    public RestResponseStruct newSuccessWithin(@Nullable Object data, @Nonnull String successCode, @Nullable Object[] successArgs, @Nullable String defaultMessage, @Nullable Locale locale) {
        RestResponseStruct struct = new RestResponseStruct();
        setSuccessWithin(struct, data, successCode, successArgs, defaultMessage, locale);
        return struct;
    }

    /**
     * Create a failure response using the default failure code.
     *
     * @return A REST response struct with failure status
     */
    @Nonnull
    public RestResponseStruct newFailure() {
        return newFailure(failureCode, null, null, null);
    }

    /**
     * Create a failure response with a custom message code.
     *
     * @param code The localized message code key, may be {@code null}
     *
     * @return A REST response struct with failure status
     */
    @Nonnull
    public RestResponseStruct newFailure(@Nullable String code) {
        return newFailure(code, null, null, null);
    }

    /**
     * Create a failure response with a message code and arguments.
     *
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     *
     * @return A REST response struct with failure status
     */
    @Nonnull
    public RestResponseStruct newFailure(@Nullable String code, @Nullable Object[] args) {
        return newFailure(code, args, null, null);
    }

    /**
     * Create a failure response with a fallback default message.
     *
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     *
     * @return A REST response struct with failure status
     */
    @Nonnull
    public RestResponseStruct newFailure(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        return newFailure(code, args, defaultMessage, null);
    }

    /**
     * Create a failure response with full message customization.
     *
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     * @param locale The locale for message resolution, may be {@code null}
     *
     * @return A REST response struct with failure status
     */
    @Nonnull
    public RestResponseStruct newFailure(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        RestResponseStruct struct = new RestResponseStruct();
        setFailure(struct, code, args, defaultMessage, locale);
        return struct;
    }

    /**
     * Create a failure response from binding validation errors.
     *
     * @param binding The binding result containing validation errors
     *
     * @return A REST response struct with failure status and binding error messages
     */
    @Nonnull
    public RestResponseStruct newFailureBinding(@Nonnull BindingResult binding) {
        return newFailureBinding(binding, HtmlEscapeWraps.getBrAsString());
    }

    /**
     * Create a failure response from binding errors using a custom delimiter.
     *
     * @param binding The binding result containing validation errors
     * @param delimiter Delimiter for joining multiple error messages
     *
     * @return A REST response struct with failure status and binding error messages
     */
    @Nonnull
    public RestResponseStruct newFailureBinding(@Nonnull BindingResult binding, @Nullable String delimiter) {
        return newFailureBinding(binding, delimiter, true, false, SymbolVariantConst.ORDER_SQUARES_SPACE, null);
    }

    /**
     * Create a failure response from binding errors with configurable field naming.
     *
     * @param binding The binding result containing validation errors
     * @param delimiter Delimiter for joining multiple error messages
     * @param fieldName Whether to include field names in the output
     * @param nameLocalized Whether to use localized field names
     * @param orderFormat Template format for numbering errors, may be {@code null}
     *
     * @return A REST response struct with failure status and binding error messages
     */
    @Nonnull
    public RestResponseStruct newFailureBinding(@Nonnull BindingResult binding, @Nullable String delimiter, boolean fieldName, boolean nameLocalized, @Nullable String orderFormat) {
        return newFailureBinding(binding, delimiter, fieldName, nameLocalized, orderFormat, null);
    }

    /**
     * Create a failure response from binding errors with full customization.
     *
     * @param binding The binding result containing validation errors
     * @param delimiter Delimiter for joining multiple error messages
     * @param fieldName Whether to include field names in the output
     * @param nameLocalized Whether to use localized field names
     * @param orderFormat Template format for numbering errors, may be {@code null}
     * @param locale The locale for message resolution, may be {@code null}
     *
     * @return A REST response struct with failure status and binding error messages
     */
    @Nonnull
    public RestResponseStruct newFailureBinding(@Nonnull BindingResult binding, @Nullable String delimiter, boolean fieldName, boolean nameLocalized, @Nullable String orderFormat, @Nullable Locale locale) {
        Assert.isTrue(binding.hasErrors(), AssertMessageConst.IS_TRUE);
        RestResponseStruct struct = new RestResponseStruct();
        setFailure(struct, binding, delimiter, fieldName, nameLocalized, orderFormat, locale);
        return struct;
    }

    /**
     * Create a refusal response using the default refusal code.
     *
     * @return A REST response struct with refusal status
     */
    @Nonnull
    public RestResponseStruct newRefusal() {
        return newRefusal(refusalCode, null, null, null);
    }

    /**
     * Create a refusal response with a custom message code.
     *
     * @param code The localized message code key, may be {@code null}
     *
     * @return A REST response struct with refusal status
     */
    @Nonnull
    public RestResponseStruct newRefusal(@Nullable String code) {
        return newRefusal(code, null, null, null);
    }

    /**
     * Create a refusal response with a message code and arguments.
     *
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     *
     * @return A REST response struct with refusal status
     */
    @Nonnull
    public RestResponseStruct newRefusal(@Nullable String code, @Nullable Object[] args) {
        return newRefusal(code, args, null, null);
    }

    /**
     * Create a refusal response with a fallback default message.
     *
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     *
     * @return A REST response struct with refusal status
     */
    @Nonnull
    public RestResponseStruct newRefusal(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        return newRefusal(code, args, defaultMessage, null);
    }

    /**
     * Create a refusal response with full message customization.
     *
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     * @param locale The locale for message resolution, may be {@code null}
     *
     * @return A REST response struct with refusal status
     */
    @Nonnull
    public RestResponseStruct newRefusal(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        RestResponseStruct struct = new RestResponseStruct();
        setRefusal(struct, code, args, defaultMessage);
        return struct;
    }

    /**
     * Create a timeout response using the default timeout code.
     *
     * @return A REST response struct with timeout status
     */
    @Nonnull
    public RestResponseStruct newTimeout() {
        return newTimeout(timeoutCode, null, null, null);
    }

    /**
     * Create a timeout response with a custom message code.
     *
     * @param code The localized message code key, may be {@code null}
     *
     * @return A REST response struct with timeout status
     */
    @Nonnull
    public RestResponseStruct newTimeout(@Nullable String code) {
        return newTimeout(code, null, null, null);
    }

    /**
     * Create a timeout response with a message code and arguments.
     *
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     *
     * @return A REST response struct with timeout status
     */
    @Nonnull
    public RestResponseStruct newTimeout(@Nullable String code, @Nullable Object[] args) {
        return newTimeout(code, args, null, null);
    }

    /**
     * Create a timeout response with a fallback default message.
     *
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     *
     * @return A REST response struct with timeout status
     */
    @Nonnull
    public RestResponseStruct newTimeout(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        return newTimeout(code, args, defaultMessage, null);
    }

    /**
     * Create a timeout response with full message customization.
     *
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     * @param locale The locale for message resolution, may be {@code null}
     *
     * @return A REST response struct with timeout status
     */
    @Nonnull
    public RestResponseStruct newTimeout(@Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        RestResponseStruct struct = new RestResponseStruct();
        setTimeout(struct, code, args, defaultMessage, locale);
        return struct;
    }

    /**
     * Set illegal status on a REST response struct using the default message.
     *
     * @param struct The response struct to modify
     */
    public void setIllegal(@Nonnull RestResponseStruct struct) {
        setIllegal(struct, true);
    }

    /**
     * Set illegal status on a REST response struct, optionally adjusting the message.
     *
     * @param struct The response struct to modify
     * @param adjustMessage Whether to also set the message using the default illegal code
     */
    public void setIllegal(@Nonnull RestResponseStruct struct, boolean adjustMessage) {
        struct.setStatus(ResponseBodyConst.CODE_ILLEGAL);
        if (adjustMessage) {
            setMessage(struct, illegalCode, null, null, null);
        }
    }

    /**
     * Set illegal status on a REST response struct with a custom message code.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key, may be {@code null}
     */
    public void setIllegal(@Nonnull RestResponseStruct struct, @Nullable String code) {
        setIllegal(struct, code, null, null, null);
    }

    /**
     * Set illegal status with a message code and arguments.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     */
    public void setIllegal(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args) {
        setIllegal(struct, code, args, null, null);
    }

    /**
     * Set illegal status with a fallback default message.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     */
    public void setIllegal(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        setIllegal(struct, code, args, defaultMessage, null);
    }

    /**
     * Set illegal status with full message customization.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     * @param locale The locale for message resolution, may be {@code null}
     */
    public void setIllegal(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        struct.setStatus(ResponseBodyConst.CODE_ILLEGAL);
        setMessage(struct, code, args, defaultMessage, locale);
    }

    /**
     * Set success status on a REST response struct using the default message.
     *
     * @param struct The response struct to modify
     */
    public void setSuccess(@Nonnull RestResponseStruct struct) {
        setSuccess(struct, true);
    }

    /**
     * Set success status on a REST response struct, optionally adjusting the message.
     *
     * @param struct The response struct to modify
     * @param adjustMessage Whether to also set the message using the default success code
     */
    public void setSuccess(@Nonnull RestResponseStruct struct, boolean adjustMessage) {
        struct.setStatus(ResponseBodyConst.CODE_SUCCESS);
        if (adjustMessage) {
            setMessage(struct, successCode, null, null, null);
        }
    }

    /**
     * Set success status on a REST response struct with a custom message code.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key, may be {@code null}
     */
    public void setSuccess(@Nonnull RestResponseStruct struct, @Nullable String code) {
        setSuccess(struct, code, null, null, null);
    }

    /**
     * Set success status with a message code and arguments.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     */
    public void setSuccess(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args) {
        setSuccess(struct, code, args, null, null);
    }

    /**
     * Set success status with a fallback default message.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     */
    public void setSuccess(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        setSuccess(struct, code, args, defaultMessage, null);
    }

    /**
     * Set success status with full message customization.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     * @param locale The locale for message resolution, may be {@code null}
     */
    public void setSuccess(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        struct.setStatus(ResponseBodyConst.CODE_SUCCESS);
        setMessage(struct, code, args, defaultMessage, locale);
    }

    /**
     * Set success status on a REST response struct and include payload data.
     *
     * @param struct The response struct to modify
     * @param data The payload data to include, may be {@code null}
     */
    public void setSuccessWithin(@Nonnull RestResponseStruct struct, @Nullable Object data) {
        setSuccess(struct);
        struct.setData(data);
    }

    /**
     * Set success status with a custom success code and include payload data.
     *
     * @param struct The response struct to modify
     * @param data The payload data to include, may be {@code null}
     * @param successCode The localized message code key for the success message
     */
    public void setSuccessWithin(@Nonnull RestResponseStruct struct, @Nullable Object data, @Nonnull String successCode) {
        setSuccessWithin(struct, data, successCode, null, null, null);
    }

    /**
     * Set success status with a success code and arguments, and include payload data.
     *
     * @param struct The response struct to modify
     * @param data The payload data to include, may be {@code null}
     * @param successCode The localized message code key for the success message
     * @param successArgs Arguments for message formatting, may be {@code null}
     */
    public void setSuccessWithin(@Nonnull RestResponseStruct struct, @Nullable Object data, @Nonnull String successCode, @Nullable Object[] successArgs) {
        setSuccessWithin(struct, data, successCode, successArgs, null, null);
    }

    /**
     * Set success status with a fallback default message and include payload data.
     *
     * @param struct The response struct to modify
     * @param data The payload data to include, may be {@code null}
     * @param successCode The localized message code key for the success message
     * @param successArgs Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     */
    public void setSuccessWithin(@Nonnull RestResponseStruct struct, @Nullable Object data, @Nonnull String successCode, @Nullable Object[] successArgs, @Nullable String defaultMessage) {
        setSuccessWithin(struct, data, successCode, successArgs, defaultMessage, null);
    }

    /**
     * Set success status with full message customization and include payload data.
     *
     * @param struct The response struct to modify
     * @param data The payload data to include, may be {@code null}
     * @param successCode The localized message code key for the success message
     * @param successArgs Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     * @param locale The locale for message resolution, may be {@code null}
     */
    public void setSuccessWithin(@Nonnull RestResponseStruct struct, @Nullable Object data, @Nonnull String successCode, @Nullable Object[] successArgs, @Nullable String defaultMessage, @Nullable Locale locale) {
        setSuccess(struct, successCode, successArgs, defaultMessage, locale);
        struct.setData(data);
    }

    /**
     * Set failure status on a REST response struct using the default message.
     *
     * @param struct The response struct to modify
     */
    public void setFailure(@Nonnull RestResponseStruct struct) {
        setFailure(struct, true);
    }

    /**
     * Set failure status on a REST response struct, optionally adjusting the message.
     *
     * @param struct The response struct to modify
     * @param adjustMessage Whether to also set the message using the default failure code
     */
    public void setFailure(@Nonnull RestResponseStruct struct, boolean adjustMessage) {
        struct.setStatus(ResponseBodyConst.CODE_FAILURE);
        if (adjustMessage) {
            setMessage(struct, failureCode, null, null, null);
        }
    }

    /**
     * Set failure status on a REST response struct with a custom message code.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key, may be {@code null}
     */
    public void setFailure(@Nonnull RestResponseStruct struct, @Nullable String code) {
        setFailure(struct, code, null, null, null);
    }

    /**
     * Set failure status with a message code and arguments.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     */
    public void setFailure(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args) {
        setFailure(struct, code, args, null, null);
    }

    /**
     * Set failure status with a fallback default message.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     */
    public void setFailure(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        setFailure(struct, code, args, defaultMessage, null);
    }

    /**
     * Set failure status with full message customization.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     * @param locale The locale for message resolution, may be {@code null}
     */
    public void setFailure(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        struct.setStatus(ResponseBodyConst.CODE_FAILURE);
        setMessage(struct, code, args, defaultMessage, locale);
    }

    /**
     * Set failure status on a REST response struct from binding validation errors.
     *
     * @param struct The response struct to modify
     * @param binding The binding result containing validation errors
     */
    public void setFailure(@Nonnull RestResponseStruct struct, @Nonnull BindingResult binding) {
        setFailure(struct, binding, HtmlEscapeWraps.getBrAsString());
    }

    /**
     * Set failure status from binding errors using a custom delimiter.
     *
     * @param struct The response struct to modify
     * @param binding The binding result containing validation errors
     * @param delimiter Delimiter for joining multiple error messages
     */
    public void setFailure(@Nonnull RestResponseStruct struct, @Nonnull BindingResult binding, @Nullable String delimiter) {
        setFailure(struct, binding, delimiter, true, false, SymbolVariantConst.ORDER_SQUARES_SPACE, null);
    }

    /**
     * Set failure status from binding errors with configurable field naming.
     *
     * @param struct The response struct to modify
     * @param binding The binding result containing validation errors
     * @param delimiter Delimiter for joining multiple error messages
     * @param fieldName Whether to include field names in the output
     * @param nameLocalized Whether to use localized field names
     * @param orderFormat Template format for numbering errors, may be {@code null}
     */
    public void setFailure(@Nonnull RestResponseStruct struct, @Nonnull BindingResult binding, @Nullable String delimiter, boolean fieldName, boolean nameLocalized, @Nullable String orderFormat) {
        setFailure(struct, binding, delimiter, fieldName, nameLocalized, orderFormat, null);
    }

    /**
     * Set failure status from binding errors with full customization.
     *
     * @param struct The response struct to modify
     * @param binding The binding result containing validation errors
     * @param delimiter Delimiter for joining multiple error messages
     * @param fieldName Whether to include field names in the output
     * @param nameLocalized Whether to use localized field names
     * @param orderFormat Template format for numbering errors, may be {@code null}
     * @param locale The locale for message resolution, may be {@code null}
     */
    public void setFailure(@Nonnull RestResponseStruct struct, @Nonnull BindingResult binding, @Nullable String delimiter, boolean fieldName, boolean nameLocalized, @Nullable String orderFormat, @Nullable Locale locale) {
        Assert.isTrue(binding.hasErrors(), AssertMessageConst.IS_TRUE);
        struct.setStatus(ResponseBodyConst.CODE_FAILURE);
        Assert.notNull(messenger, AssertMessageConst.NOT_NULL);
        List<String> messages = messenger.getBindingErrors(binding, fieldName, nameLocalized, orderFormat, locale);
        struct.appendMessage(messages, delimiter);
    }

    /**
     * Set refusal status on a REST response struct using the default message.
     *
     * @param struct The response struct to modify
     */
    public void setRefusal(@Nonnull RestResponseStruct struct) {
        setRefusal(struct, true);
    }

    /**
     * Set refusal status on a REST response struct, optionally adjusting the message.
     *
     * @param struct The response struct to modify
     * @param adjustMessage Whether to also set the message using the default refusal code
     */
    public void setRefusal(@Nonnull RestResponseStruct struct, boolean adjustMessage) {
        struct.setStatus(ResponseBodyConst.CODE_REFUSAL);
        if (adjustMessage) {
            setMessage(struct, refusalCode, null, null, null);
        }
    }

    /**
     * Set refusal status on a REST response struct with a custom message code.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key, may be {@code null}
     */
    public void setRefusal(@Nonnull RestResponseStruct struct, @Nullable String code) {
        setRefusal(struct, code, null, null, null);
    }

    /**
     * Set refusal status with a message code and arguments.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     */
    public void setRefusal(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args) {
        setRefusal(struct, code, args, null, null);
    }

    /**
     * Set refusal status with a fallback default message.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     */
    public void setRefusal(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        setRefusal(struct, code, args, defaultMessage, null);
    }

    /**
     * Set refusal status with full message customization.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     * @param locale The locale for message resolution, may be {@code null}
     */
    public void setRefusal(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        struct.setStatus(ResponseBodyConst.CODE_REFUSAL);
        setMessage(struct, code, args, defaultMessage, locale);
    }

    /**
     * Set timeout status on a REST response struct using the default message.
     *
     * @param struct The response struct to modify
     */
    public void setTimeout(@Nonnull RestResponseStruct struct) {
        setTimeout(struct, true);
    }

    /**
     * Set timeout status on a REST response struct, optionally adjusting the message.
     *
     * @param struct The response struct to modify
     * @param adjustMessage Whether to also set the message using the default timeout code
     */
    public void setTimeout(@Nonnull RestResponseStruct struct, boolean adjustMessage) {
        struct.setStatus(ResponseBodyConst.CODE_TIMEOUT);
        if (adjustMessage) {
            setMessage(struct, timeoutCode, null, null, null);
        }
    }

    /**
     * Set timeout status on a REST response struct with a custom message code.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key, may be {@code null}
     */
    public void setTimeout(@Nonnull RestResponseStruct struct, @Nullable String code) {
        setTimeout(struct, code, null, null, null);
    }

    /**
     * Set timeout status with a message code and arguments.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     */
    public void setTimeout(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args) {
        setTimeout(struct, code, args, null, null);
    }

    /**
     * Set timeout status with a fallback default message.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     */
    public void setTimeout(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        setTimeout(struct, code, args, defaultMessage, null);
    }

    /**
     * Set timeout status with full message customization.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     * @param locale The locale for message resolution, may be {@code null}
     */
    public void setTimeout(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        struct.setStatus(ResponseBodyConst.CODE_TIMEOUT);
        setMessage(struct, code, args, defaultMessage, locale);
    }

    /**
     * Resolve and set the localized message using the given code.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key to look up, may be {@code null}
     */
    public void setMessage(@Nonnull RestResponseStruct struct, @Nullable String code) {
        setMessage(struct, code, null, null);
    }

    /**
     * Resolve and set the localized message using the given code and arguments.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key to look up, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     */
    public void setMessage(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args) {
        setMessage(struct, code, args, null);
    }

    /**
     * Resolve and set the localized message with a fallback default message.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key to look up, may be {@code null}
     * @param args Arguments for message formatting, may be {@code null}
     * @param defaultMessage Fallback default message if lookup fails, may be {@code null}
     */
    public void setMessage(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage) {
        setMessage(struct, code, args, null, null);
    }

    /**
     * Resolve and set the localized message into a REST response struct.
     *
     * @param struct The response struct to modify
     * @param code The localized message code key to look up
     * @param args Arguments for message formatting
     * @param defaultMessage Fallback default message when code is blank or lookup fails
     * @param locale The locale for message resolution
     */
    public void setMessage(@Nonnull RestResponseStruct struct, @Nullable String code, @Nullable Object[] args, @Nullable String defaultMessage, @Nullable Locale locale) {
        if (StringUtils.isBlank(code)) {
            struct.setMessage(defaultMessage);
        } else {
            struct.setMessage(MessageSourceWraps.getMessageLookup(messageSource, code, args, defaultMessage, locale));
        }
    }
}
