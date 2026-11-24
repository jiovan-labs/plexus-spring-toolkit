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

package com.jiovan.plexus.spring.toolkit.security.authentication;


import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import com.jiovan.plexus.common.toolkit.constant.CharVariantConst;
import com.jiovan.plexus.common.toolkit.constant.RegexVariantConst;
import com.jiovan.plexus.spring.toolkit.security.exception.IllegalAuthenticationException;
import com.jiovan.plexus.spring.toolkit.util.JsonParserWraps;
import com.jiovan.plexus.spring.toolkit.util.RequestParamWraps;
import com.jiovan.plexus.spring.toolkit.util.WebUtilsWraps;
import lombok.Getter;
import lombok.Setter;


/**
 * {@link org.springframework.security.core.Authentication} filter for a mobile with a captcha
 *
 * @author David Hsing
 *
 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
 */
@Getter
@Setter
@SuppressWarnings("unused")
public class MobileCaptchaAuthenticationFilter extends AbstractAuthenticationProcessingFilter implements BeanFactoryAware {
    /**
     * The default request parameter name for the captcha.
     */
    public static final String CAPTCHA_KEY = "captcha";    // $NON-NLS-1$

    /**
     * The default request parameter name for the mobile number.
     */
    public static final String MOBILE_KEY = "mobile";    // $NON-NLS-1$

    /**
     * The default request parameter name for the dial code.
     */
    public static final String DIAL_KEY = "dial";    // $NON-NLS-1$

    /**
     * The request matcher for the login endpoint.
     */
    private static final PathPatternRequestMatcher REQUEST_MATCHER = PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/login");    // $NON-NLS-1$

    /**
     * The request parameter name for the captcha.
     */
    @Getter
    private String captchaParameter = CAPTCHA_KEY;

    /**
     * The request parameter name for the mobile number.
     */
    @Getter
    private String mobileParameter = MOBILE_KEY;

    /**
     * The request parameter name for the dial code.
     */
    @Getter
    private String dialParameter = DIAL_KEY;

    /**
     * Whether only POST requests are accepted.
     */
    @Getter
    private boolean postOnly = true;

    /**
     * Whether REST-compatible request bodies are supported.
     */
    @Getter
    private boolean restCompatible = false;

    /**
     * The bean factory used for resolving beans.
     */
    protected BeanFactory beanFactory;

    /**
     * Create a MobileCaptchaAuthenticationFilter.
     */
    public MobileCaptchaAuthenticationFilter() {
        super(REQUEST_MATCHER);
    }

    /**
     * Create a MobileCaptchaAuthenticationFilter using the given authentication manager.
     *
     * @param manager The authentication manager
     */
    public MobileCaptchaAuthenticationFilter(@Nonnull AuthenticationManager manager) {
        super(REQUEST_MATCHER, manager);
    }

    /**
     * Returns the actual authentication through the given request
     * <p>
     * The implementation should do one of the following:
     * <ol>
     * <li>Return a populated authentication token for the authenticated user, indicating successful authentication</li>
     * <li>Return null, indicating that the authentication process is still in progress</li>
     * <li>Throw an <tt>AuthenticationException</tt> if the authentication process fails</li>
     * </ol>
     *
     * @param request From which to extract parameters and perform the authentication
     * @param response Which may be needed if the implementation has to do a redirect as part of a multi-stage authentication process (such as OpenID)
     *
     * @return the actual authentication through the given request
     *
     * @throws org.springframework.security.core.AuthenticationException if authentication fails
     *
     * @see org.springframework.security.authentication.ProviderManager
     */
    @Override
    public Authentication attemptAuthentication(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !HttpMethod.POST.matches(request.getMethod())) {
            throw new AuthenticationServiceException(super.messages.getMessage("MiscMessage.unsupportedHttpMethod", new Object[]{request.getMethod()}, "Unsupported method"));    // $NON-NLS-1$ // $NON-NLS-2$
        }
        HttpServletRequest wrapper = WebUtilsWraps.wrapWithContentCaching(request);
        String mobile = obtainMobile(wrapper), captcha = obtainCaptcha(wrapper), dial = obtainDial(wrapper);
        if (StringUtils.isBlank(mobile)) {
            throw new IllegalAuthenticationException(super.messages.getMessage("MobileCaptchaAuthenticationFilter.illegalMobile", "Illegal mobile"));    // $NON-NLS-1$ // $NON-NLS-2$
        }
        if (StringUtils.isBlank(captcha)) {
            throw new IllegalAuthenticationException(super.messages.getMessage("MobileCaptchaAuthenticationFilter.illegalCaptcha", "Illegal captcha"));    // $NON-NLS-1$ // $NON-NLS-2$
        }
        preAuthentication(wrapper, captcha, mobile, dial);
        MobileCaptchaAuthenticationToken token = new MobileCaptchaAuthenticationToken(mobile, captcha, null);
        token.setAdditive(dial);
        setDetails(wrapper, token);
        return super.getAuthenticationManager().authenticate(token);
    }

    /**
     * Extract the mobile number from the given request.
     *
     * @param request The current HTTP request
     *
     * @return The result of the operation
     */
    @SuppressWarnings("DuplicatedCode")
    protected String obtainMobile(@Nonnull HttpServletRequest request) {
        String result = RequestParamWraps.getStringParameterTrimming(request, mobileParameter);
        if (StringUtils.isEmpty(result) && restCompatible) {
            String content = WebUtilsWraps.getContentAsStringTrimmingQuietly(request, true);
            content = RegExUtils.removeAll(content, RegexVariantConst.LINE_SEPARATOR);
            content = RegExUtils.replaceAll(content, RegexVariantConst.COMMA_CURLY_BRACKET, CharUtils.toString(CharVariantConst.CURLY_BRACKET_RIGHT));
            result = JsonParserWraps.findNodeValueAsString(content, mobileParameter, beanFactory);
        }
        return result;
    }

    /**
     * Extract the captcha from the given request.
     *
     * @param request The current HTTP request
     *
     * @return The result of the operation
     */
    @SuppressWarnings("DuplicatedCode")
    protected String obtainCaptcha(@Nonnull HttpServletRequest request) {
        String result = RequestParamWraps.getStringParameterTrimming(request, captchaParameter);
        if (StringUtils.isEmpty(result) && restCompatible) {
            String content = WebUtilsWraps.getContentAsStringTrimmingQuietly(request, true);
            content = RegExUtils.removeAll(content, RegexVariantConst.LINE_SEPARATOR);
            content = RegExUtils.replaceAll(content, RegexVariantConst.COMMA_CURLY_BRACKET, CharUtils.toString(CharVariantConst.CURLY_BRACKET_RIGHT));
            result = JsonParserWraps.findNodeValueAsString(content, captchaParameter, beanFactory);
        }
        return result;
    }

    /**
     * Extract the dial code from the given request.
     *
     * @param request The current HTTP request
     *
     * @return The result of the operation
     */
    protected String obtainDial(@Nonnull HttpServletRequest request) {
        String result = RequestParamWraps.getStringParameterTrimming(request, dialParameter);
        if (StringUtils.isEmpty(result) && restCompatible) {
            String content = WebUtilsWraps.getContentAsStringTrimmingQuietly(request, true);
            result = JsonParserWraps.findNodeValueAsString(content, dialParameter, beanFactory);
        }
        return result;
    }

    /**
     * Perform actions before authentication
     * <p>
     * Override to perform additional actions, such as logging, etc
     */
    @SuppressWarnings("EmptyMethod")
    protected void preAuthentication(@Nonnull HttpServletRequest request, @Nonnull String captcha, @Nonnull String mobile, @Nullable String dial) throws AuthenticationException {
    }

    /**
     * Attach authentication details built from the request to the given token.
     *
     * @param request The current HTTP request
     * @param token The given token
     */
    protected void setDetails(@Nonnull HttpServletRequest request, @Nonnull MobileCaptchaAuthenticationToken token) {
        token.setDetails(super.authenticationDetailsSource.buildDetails(request));
    }
}
