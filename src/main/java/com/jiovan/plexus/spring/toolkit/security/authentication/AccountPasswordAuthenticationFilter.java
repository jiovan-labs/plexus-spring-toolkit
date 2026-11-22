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


import java.io.IOException;
import java.util.function.UnaryOperator;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import com.jiovan.plexus.common.toolkit.constant.CharVariantConst;
import com.jiovan.plexus.common.toolkit.constant.RegexVariantConst;
import com.jiovan.plexus.common.toolkit.util.FieldUtilsWraps;
import com.jiovan.plexus.spring.toolkit.security.exception.IllegalAuthenticationException;
import com.jiovan.plexus.spring.toolkit.util.JsonParserWraps;
import com.jiovan.plexus.spring.toolkit.util.RequestParamWraps;
import com.jiovan.plexus.spring.toolkit.util.WebUtilsWraps;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * {@link org.springframework.security.core.Authentication} filter for a username with a password
 *
 * @author David Hsing
 *
 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
 * @see org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
 */
@NoArgsConstructor
@Setter
@SuppressWarnings("unused")
public class AccountPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter implements BeanFactoryAware {
    /**
     * Whether REST-compatible request bodies are supported.
     */
    @Getter
    private boolean restCompatible = false;

    /**
     * Whether remember-me services are enabled.
     */
    @Getter
    private boolean rememberMe = false;

    /**
     * Optional transition transforming the success authentication event.
     */
    @Getter
    private UnaryOperator<AbstractAuthenticationEvent> successEventTransition;

    /**
     * The bean factory used for resolving beans.
     */
    protected BeanFactory beanFactory;

    /**
     * Create a AccountPasswordAuthenticationFilter using the given authentication manager.
     *
     * @param manager The authentication manager
     */
    public AccountPasswordAuthenticationFilter(AuthenticationManager manager) {
        super(manager);
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
    @Nonnull
    @Override
    public Authentication attemptAuthentication(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) throws AuthenticationException {
        if (isPostOnly() && !HttpMethod.POST.matches(request.getMethod())) {
            throw new AuthenticationServiceException(super.messages.getMessage("MiscMessage.unsupportedHttpMethod", new Object[]{request.getMethod()}, "Unsupported method"));    // $NON-NLS-1$ // $NON-NLS-2$
        }
        HttpServletRequest wrapper = WebUtilsWraps.wrapWithContentCaching(request);
        String username = obtainUsername(wrapper), password = obtainPassword(wrapper);
        if (StringUtils.isBlank(username)) {
            throw new IllegalAuthenticationException(super.messages.getMessage("AccountPasswordAuthenticationFilter.illegalUsername", "Illegal username"));    // $NON-NLS-1$ // $NON-NLS-2$
        }
        if (StringUtils.isBlank(password)) {
            throw new IllegalAuthenticationException(super.messages.getMessage("AccountPasswordAuthenticationFilter.illegalPassword", "Illegal password"));    // $NON-NLS-1$ // $NON-NLS-2$
        }
        preAuthentication(wrapper, username, password);
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        super.setDetails(wrapper, token);
        return super.getAuthenticationManager().authenticate(token);
    }

    /**
     * Returns whether only POST requests are accepted.
     *
     * @return Whether only POST requests are accepted
     */
    public boolean isPostOnly() {
        return BooleanUtils.isTrue(FieldUtilsWraps.readDeclaredFieldAs(this, "postOnly", true, Boolean.class));    // $NON-NLS-1$
    }

    /**
     * Extract the username from the given request.
     *
     * @param request The current HTTP request
     *
     * @return The result of the operation
     */
    @Nullable
    protected String obtainUsername(@Nonnull HttpServletRequest request) {
        String result = RequestParamWraps.getStringParameterTrimming(request, super.getUsernameParameter());
        if (StringUtils.isEmpty(result) && restCompatible) {
            String content = WebUtilsWraps.getContentAsStringTrimmingQuietly(request, true);
            content = RegExUtils.removeAll(content, RegexVariantConst.LINE_SEPARATOR);
            content = RegExUtils.replaceAll(content, RegexVariantConst.COMMA_CURLY_BRACKET, CharUtils.toString(CharVariantConst.CURLY_BRACKET_RIGHT));
            result = JsonParserWraps.findNodeValueAsString(content, super.getUsernameParameter(), beanFactory);
        }
        return result;
    }

    /**
     * Extract the password from the given request.
     *
     * @param request The current HTTP request
     *
     * @return The result of the operation
     */
    @Nullable
    protected String obtainPassword(@Nonnull HttpServletRequest request) {
        String result = RequestParamWraps.getStringParameterTrimming(request, super.getPasswordParameter());
        if (StringUtils.isEmpty(result) && restCompatible) {
            String content = WebUtilsWraps.getContentAsStringTrimmingQuietly(request, true);
            content = RegExUtils.removeAll(content, RegexVariantConst.LINE_SEPARATOR);
            content = RegExUtils.replaceAll(content, RegexVariantConst.COMMA_CURLY_BRACKET, CharUtils.toString(CharVariantConst.CURLY_BRACKET_RIGHT));
            result = JsonParserWraps.findNodeValueAsString(content, super.getPasswordParameter(), beanFactory);
        }
        return result;
    }

    /**
     * Perform actions before authentication
     * <p>
     * Override to perform additional actions, such as logging, etc
     */
    @SuppressWarnings("EmptyMethod")
    protected void preAuthentication(HttpServletRequest request, String username, String password) throws AuthenticationException {
    }

    /**
     * Behavior for successful authentication
     * <p>
     * With {@code authenticationSuccessTransition}, this can publish a different authentication event, not only {@link org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent}
     */
    @Override
    @SuppressWarnings("DuplicatedCode")
    protected void successfulAuthentication(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain, @Nonnull Authentication authentication) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        SecurityContextRepository repository = FieldUtilsWraps.readDeclaredFieldAs(this, "securityContextRepository", true, SecurityContextRepository.class);    // $NON-NLS-1$
        if (repository != null) {
            repository.saveContext(context, request, response);
        }
        if (rememberMe) {
            super.getRememberMeServices().loginSuccess(request, response, authentication);
        }
        AbstractAuthenticationEvent source = rememberMe ? new InteractiveAuthenticationSuccessEvent(authentication, this.getClass()) : new AuthenticationSuccessEvent(authentication);
        AbstractAuthenticationEvent event = successEventTransition.apply(source);
        if (ObjectUtils.allNotNull(event, super.eventPublisher)) {
            super.eventPublisher.publishEvent(event);
        }
        super.getSuccessHandler().onAuthenticationSuccess(request, response, authentication);
    }
}
