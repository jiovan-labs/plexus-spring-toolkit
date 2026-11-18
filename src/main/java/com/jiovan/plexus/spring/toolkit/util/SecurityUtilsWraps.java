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


import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.JaasAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.util.CollectionUtils;
import com.jiovan.plexus.common.toolkit.exception.UnsupportedClassException;
import com.jiovan.plexus.common.toolkit.util.ArrayUtilsWraps;
import com.jiovan.plexus.common.toolkit.util.ObjectUtilsWraps;
import com.jiovan.plexus.spring.toolkit.constant.SpringAttributeConst;
import com.jiovan.plexus.spring.toolkit.security.exception.IllegalAuthenticationException;


/**
 * Utilities for Spring Security
 *
 * @author David Hsing
 *
 * @see org.springframework.security.core.Authentication
 * @see org.springframework.security.core.authority.AuthorityUtils
 * @see org.springframework.security.core.context.SecurityContextHolder
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class SecurityUtilsWraps {
    /**
     * Return the name of the current authentication.
     *
     * @param authentication The authentication, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getAuthenticationName(@Nullable Authentication authentication) {
        return getAuthenticationName(authentication, false);
    }

    /**
     * @see org.springframework.security.authentication.AbstractAuthenticationToken#getName
     */
    @Nullable
    public static String getAuthenticationName(@Nullable Authentication authentication, boolean authenticated) {
        return (authentication == null || (authenticated && !authentication.isAuthenticated())) ? null : authentication.getName();
    }

    /**
     * Return the authentication principal converted to the expected type.
     *
     * @param authentication The authentication, may be {@code null}
     * @param expectType The expected type, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static <T> T getAuthenticationPrincipalAs(@Nullable Authentication authentication, @Nullable Class<T> expectType) {
        return ObjectUtils.anyNull(authentication, expectType) ? null : ObjectUtilsWraps.castAs(authentication.getPrincipal(), expectType);
    }

    /**
     * Return the authentication principal as a string.
     *
     * @param authentication The authentication, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getAuthenticationPrincipalAsString(@Nullable Authentication authentication) {
        return getAuthenticationPrincipalAs(authentication, String.class);
    }

    /**
     * Return the authentication credentials converted to the expected type.
     *
     * @param authentication The authentication, may be {@code null}
     * @param expectType The expected type, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static <T> T getAuthenticationCredentialsAs(@Nullable Authentication authentication, @Nullable Class<T> expectType) {
        return ObjectUtils.anyNull(authentication, expectType) ? null : ObjectUtilsWraps.castAs(authentication.getCredentials(), expectType);
    }

    /**
     * Return the authentication credentials as a string.
     *
     * @param authentication The authentication, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getAuthenticationCredentialsAsString(@Nullable Authentication authentication) {
        return getAuthenticationCredentialsAs(authentication, String.class);
    }

    /**
     * Return the authentication details converted to the expected type.
     *
     * @param authentication The authentication, may be {@code null}
     * @param expectType The expected type, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static <T> T getAuthenticationDetailsAs(@Nullable Authentication authentication, @Nullable Class<T> expectType) {
        return ObjectUtils.anyNull(authentication, expectType) ? null : ObjectUtilsWraps.castAs(authentication.getDetails(), expectType);
    }

    /**
     * Return the authentication details as a string.
     *
     * @param authentication The authentication, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getAuthenticationDetailsAsString(@Nullable Authentication authentication) {
        return getAuthenticationDetailsAs(authentication, String.class);
    }

    /**
     * Return the authentication from the current security context.
     *
     * @return The result of the operation
     */
    @Nullable
    public static Authentication getContextAuthentication() {
        return getContextAuthentication(false);
    }

    /**
     * Return the authentication from the current security context.
     *
     * @param authenticated The given authenticated
     *
     * @return The result of the operation
     */
    @Nullable
    public static Authentication getContextAuthentication(boolean authenticated) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return (authentication == null || (authenticated && !authentication.isAuthenticated())) ? null : authentication;
    }

    /**
     * Return the context authentication converted to the expected type.
     *
     * @param expectType The expected type, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static <T extends Authentication> T getContextAuthenticationAs(@Nullable Class<T> expectType) {
        return getContextAuthenticationAs(false, expectType);
    }

    /**
     * Return the context authentication converted to the expected type.
     *
     * @param authenticated The given authenticated
     * @param expectType The expected type, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static <T extends Authentication> T getContextAuthenticationAs(boolean authenticated, @Nullable Class<T> expectType) {
        return (expectType == null) ? null : ObjectUtilsWraps.castAs(getContextAuthentication(authenticated), expectType);
    }

    /**
     * Return the name of the context authentication.
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getContextAuthenticationName() {
        return getContextAuthenticationName(false);
    }

    /**
     * @see org.springframework.security.authentication.jaas.JaasNameCallbackHandler#handle
     *
     * @see "https://github.com/szerhusenBC/jwt-spring-security-demo"
     */
    @Nullable
    public static String getContextAuthenticationName(boolean authenticated) {
        return getAuthenticationName(getContextAuthentication(authenticated), authenticated);
    }

    /**
     * Return the required context authentication name.
     *
     * @return The result of the operation
     *
     * @throws AuthenticationException If the operation fails
     */
    @Nonnull
    public static String getContextAuthenticationNameRequired() throws AuthenticationException {
        return getContextAuthenticationNameRequired(true);
    }

    /**
     * Return the required context authentication name.
     *
     * @param authenticated The given authenticated
     *
     * @return The result of the operation
     *
     * @throws AuthenticationException If the operation fails
     */
    @Nonnull
    public static String getContextAuthenticationNameRequired(boolean authenticated) throws AuthenticationException {
        String result = getContextAuthenticationName(authenticated);
        if (StringUtils.isBlank(result)) {
            throw new IllegalAuthenticationException("Context authentication not found");
        }
        return result;
    }

    /**
     * Return the required authentication name, or {@code null} on failure.
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getContextAuthenticationNameRequiredQuietly() {
        return getContextAuthenticationNameRequiredQuietly(true);
    }

    /**
     * Return the required authentication name, or {@code null} on failure.
     *
     * @param authenticated The given authenticated
     *
     * @return The result of the operation
     */
    @Nullable
    public static String getContextAuthenticationNameRequiredQuietly(boolean authenticated) {
        try {
            return getContextAuthenticationNameRequired(authenticated);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Return the context principal converted to the expected type.
     *
     * @param expectType The expected type, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static <T> T getContextAuthenticationPrincipalAs(@Nullable Class<T> expectType) {
        return getContextAuthenticationPrincipalAs(expectType, false);
    }

    /**
     * Return the context principal converted to the expected type.
     *
     * @param expectType The expected type, may be {@code null}
     * @param authenticated The given authenticated
     *
     * @return The result of the operation
     */
    @Nullable
    public static <T> T getContextAuthenticationPrincipalAs(@Nullable Class<T> expectType, boolean authenticated) {
        return getAuthenticationPrincipalAs(getContextAuthentication(authenticated), expectType);
    }

    /**
     * Return the required context principal as the expected type.
     *
     * @param expectType The expected type, may be {@code null}
     *
     * @return The result of the operation
     *
     * @throws AuthenticationException If the operation fails
     */
    @Nonnull
    public static <T> T getContextAuthenticationPrincipalRequiredAs(@Nullable Class<T> expectType) throws AuthenticationException {
        return getContextAuthenticationPrincipalRequiredAs(expectType, true);
    }

    /**
     * Return the required context principal as the expected type.
     *
     * @param expectType The expected type, may be {@code null}
     * @param authenticated The given authenticated
     *
     * @return The result of the operation
     *
     * @throws AuthenticationException If the operation fails
     */
    @Nonnull
    public static <T> T getContextAuthenticationPrincipalRequiredAs(@Nullable Class<T> expectType, boolean authenticated) throws AuthenticationException {
        T result = getAuthenticationPrincipalAs(getContextAuthentication(authenticated), expectType);
        if (result == null) {
            throw new IllegalAuthenticationException("Context authentication principal not found");
        }
        return result;
    }

    /**
     * Return the required context principal, or {@code null} on failure.
     *
     * @param expectType The expected type, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static <T> T getContextAuthenticationPrincipalRequiredAsQuietly(@Nullable Class<T> expectType) {
        return getContextAuthenticationPrincipalRequiredAsQuietly(expectType, true);
    }

    /**
     * Return the required context principal, or {@code null} on failure.
     *
     * @param expectType The expected type, may be {@code null}
     * @param authenticated The given authenticated
     *
     * @return The result of the operation
     */
    @Nullable
    public static <T> T getContextAuthenticationPrincipalRequiredAsQuietly(@Nullable Class<T> expectType, boolean authenticated) {
        try {
            return getContextAuthenticationPrincipalRequiredAs(expectType, authenticated);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Return the context details converted to the expected type.
     *
     * @param expectType The expected type, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static <T> T getContextAuthenticationDetailsAs(@Nullable Class<T> expectType) {
        return getContextAuthenticationDetailsAs(expectType, false);
    }

    /**
     * Return the context details converted to the expected type.
     *
     * @param expectType The expected type, may be {@code null}
     * @param authenticated The given authenticated
     *
     * @return The result of the operation
     */
    @Nullable
    public static <T> T getContextAuthenticationDetailsAs(@Nullable Class<T> expectType, boolean authenticated) {
        return getAuthenticationDetailsAs(getContextAuthentication(authenticated), expectType);
    }

    /**
     * Return the granted authorities of the current authentication.
     *
     * @param authorities The authorities, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static List<GrantedAuthority> getGrantedAuthorities(@Nullable String[] authorities) {
        return getGrantedAuthorities(ArrayUtilsWraps.asList(authorities));
    }

    /**
     * Return the granted authorities of the current authentication.
     *
     * @param authorities The authorities, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static List<GrantedAuthority> getGrantedAuthorities(@Nullable Collection<String> authorities) {
        return CollectionUtils.isEmpty(authorities) ? null : authorities.stream().filter(StringUtils::isNotBlank).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    /**
     * Return the names of the granted authorities in the context.
     *
     * @return The result of the operation
     */
    @Nullable
    public static List<String> getContextGrantedAuthoritiesNames() {
        return getGrantedAuthoritiesNames(getContextAuthentication());
    }

    /**
     * Return the names of the granted authorities.
     *
     * @param authentication The authentication, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static List<String> getGrantedAuthoritiesNames(@Nullable Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return CollectionUtils.isEmpty(authorities) ? null : authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    /**
     * Return the authentication stored in the session.
     *
     * @param request The HTTP request, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Authentication getSessionAuthentication(@Nullable HttpServletRequest request) {
        return (request == null) ? null : getSessionAuthentication(request.getSession());
    }

    /**
     * Return the authentication stored in the session.
     *
     * @param session The HTTP session, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Authentication getSessionAuthentication(@Nullable HttpSession session) {
        SecurityContext context = getSessionSecurityContext(session);
        return (context == null) ? null : context.getAuthentication();
    }

    /**
     * Return the session authentication converted to the expected type.
     *
     * @param request The HTTP request, may be {@code null}
     * @param expectType The expected type, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static <T extends Authentication> T getSessionAuthenticationAs(@Nullable HttpServletRequest request, @Nullable Class<T> expectType) {
        return ObjectUtils.anyNull(request, expectType) ? null : getSessionAuthenticationAs(request.getSession(), expectType);
    }

    /**
     * Return the session authentication converted to the expected type.
     *
     * @param session The HTTP session, may be {@code null}
     * @param expectType The expected type, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static <T extends Authentication> T getSessionAuthenticationAs(@Nullable HttpSession session, @Nullable Class<T> expectType) {
        return ObjectUtilsWraps.castAs(getSessionAuthentication(session), expectType);
    }

    /**
     * Return all authentications stored in the session.
     *
     * @param request The HTTP request, may be {@code null}
     *
     * @return The result of the operation
     */
    public static List<Authentication> getSessionAuthentications(@Nullable HttpServletRequest request) {
        return (request == null) ? null : getSessionAuthentications(request.getSession());
    }

    /**
     * Return all authentications stored in the session.
     *
     * @param session The HTTP session, may be {@code null}
     *
     * @return The result of the operation
     */
    public static List<Authentication> getSessionAuthentications(@Nullable HttpSession session) {
        if (session == null) {
            return null;
        }
        List<SecurityContext> contexts = getSessionSecurityContexts(session);
        return CollectionUtils.isEmpty(contexts) ? null : contexts.stream().map(SecurityContext::getAuthentication).collect(Collectors.toList());
    }

    /**
     * Return the security context stored in the session.
     *
     * @param request The HTTP request, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static SecurityContext getSessionSecurityContext(@Nullable HttpServletRequest request) {
        return (request == null) ? null : getSessionSecurityContext(request.getSession());
    }

    /**
     * Return the security context stored in the session.
     *
     * @param session The HTTP session, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static SecurityContext getSessionSecurityContext(@Nullable HttpSession session) {
        if (session == null) {
            return null;
        }
        Object attribute = session.getAttribute(SpringAttributeConst.SECURITY_CONTEXT);
        return ObjectUtilsWraps.castAs(attribute, SecurityContext.class);
    }

    /**
     * Return all security contexts stored in the session.
     *
     * @param request The HTTP request, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static List<SecurityContext> getSessionSecurityContexts(@Nullable HttpServletRequest request) {
        return (request == null) ? null : getSessionSecurityContexts(request.getSession());
    }

    /**
     * @see org.springframework.security.web.session.HttpSessionDestroyedEvent#getSecurityContexts
     */
    @Nullable
    public static List<SecurityContext> getSessionSecurityContexts(@Nullable HttpSession session) {
        if (session == null) {
            return null;
        }
        Enumeration<String> attributes = session.getAttributeNames();
        ArrayList<SecurityContext> result = new ArrayList<>();
        while (attributes != null && attributes.hasMoreElements()) {
            String name = attributes.nextElement();
            Object value = session.getAttribute(name);
            if (value instanceof SecurityContext alias) {
                result.add(alias);
            }
        }
        return CollectionUtils.isEmpty(result) ? null : result;
    }

    /**
     * Return whether the current authentication is authenticated.
     *
     * @param authentication The authentication, may be {@code null}
     *
     * @return Whether the condition holds
     */
    public static boolean isAuthenticationAuthenticated(@Nullable Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }

    /**
     * Return whether the context authentication is authenticated.
     *
     * @return Whether the condition holds
     */
    public static boolean isContextAuthenticationAuthenticated() {
        return isAuthenticationAuthenticated(getContextAuthentication());
    }

    /**
     * Renew the authentication held in the current security context.
     *
     * @param authentication The authentication, may be {@code null}
     * @param principal The principal, may be {@code null}
     *
     * @return The result of the operation
     *
     * @throws IllegalArgumentException If the operation fails
     * @throws IllegalAccessException If the operation fails
     */
    public static Authentication renewAuthentication(@Nullable Authentication authentication, @Nullable Object principal) throws IllegalArgumentException, IllegalAccessException {
        return renewAuthentication(authentication, principal, false, null, false, null, false, null);
    }

    /**
     * Renew the authentication held in the current security context.
     *
     * @param authentication The authentication, may be {@code null}
     * @param principal The principal, may be {@code null}
     * @param details The details, may be {@code null}
     *
     * @return The result of the operation
     *
     * @throws IllegalArgumentException If the operation fails
     * @throws IllegalAccessException If the operation fails
     */
    public static Authentication renewAuthentication(@Nullable Authentication authentication, @Nullable Object principal, @Nullable Object details) throws IllegalArgumentException, IllegalAccessException {
        return renewAuthentication(authentication, principal, false, null, false, null, true, details);
    }

    /**
     * Renew the authentication held in the current security context.
     *
     * @param authentication The authentication, may be {@code null}
     * @param principal The principal, may be {@code null}
     * @param renewCredentials The given renewCredentials
     * @param credentials The credentials, may be {@code null}
     * @param renewAuthorities The given renewAuthorities
     * @param authorities The authorities, may be {@code null}
     * @param renewDetails The given renewDetails
     * @param details The details, may be {@code null}
     *
     * @return The result of the operation
     *
     * @throws IllegalAccessException If the operation fails
     * @throws UnsupportedClassException If the operation fails
     */
    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static Authentication renewAuthentication(@Nullable Authentication authentication, @Nullable Object principal, boolean renewCredentials, @Nullable Object credentials, boolean renewAuthorities, @Nullable Collection<? extends GrantedAuthority> authorities, boolean renewDetails, @Nullable Object details) throws IllegalAccessException, UnsupportedClassException {
        if (ObjectUtils.anyNull(authentication, principal)) {
            return authentication;
        }
        Authentication result;
        final String keyHash = "keyHash";    // $NON-NLS-1$
        if (authentication instanceof AnonymousAuthenticationToken alias) {
            result = new AnonymousAuthenticationToken(StringUtils.SPACE, principal, renewAuthorities ? authorities : authentication.getAuthorities());
            FieldUtils.writeField(result, keyHash, alias.getKeyHash(), true);
        } else if (authentication instanceof JaasAuthenticationToken alias) {
            result = new JaasAuthenticationToken(principal, renewCredentials ? credentials : authentication.getCredentials(), alias.getLoginContext());
        } else if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            result = new PreAuthenticatedAuthenticationToken(principal, renewCredentials ? credentials : authentication.getCredentials(), renewAuthorities ? authorities : authentication.getAuthorities());
        } else if (authentication instanceof RememberMeAuthenticationToken alias) {
            result = new RememberMeAuthenticationToken(StringUtils.SPACE, principal, renewAuthorities ? authorities : authentication.getAuthorities());
            FieldUtils.writeField(result, keyHash, alias.getKeyHash(), true);
        } else if (authentication instanceof TestingAuthenticationToken) {
            List<GrantedAuthority> oldAuthorities = new ArrayList<>(authentication.getAuthorities());
            List<GrantedAuthority> newAuthorities = new ArrayList<>(authorities);
            result = new TestingAuthenticationToken(principal, renewCredentials ? credentials : authentication.getCredentials(), renewAuthorities ? newAuthorities : oldAuthorities);
        } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
            result = new UsernamePasswordAuthenticationToken(principal, renewCredentials ? credentials : authentication.getCredentials(), renewAuthorities ? authorities : authentication.getAuthorities());
        } else {
            throw new UnsupportedClassException("Unsupported authentication type: " + authentication.getClass().getName());
        }
        if (renewDetails) {
            setAuthenticationDetails(result, details);
        }
        return result;
    }

    /**
     * Renew the authentication, ignoring failures.
     *
     * @param authentication The authentication, may be {@code null}
     * @param principal The principal, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Authentication renewAuthenticationQuietly(@Nullable Authentication authentication, @Nullable Object principal) {
        try {
            return renewAuthentication(authentication, principal);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Renew the authentication, ignoring failures.
     *
     * @param authentication The authentication, may be {@code null}
     * @param principal The principal, may be {@code null}
     * @param details The details, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Authentication renewAuthenticationQuietly(@Nullable Authentication authentication, @Nullable Object principal, @Nullable Object details) {
        try {
            return renewAuthentication(authentication, principal, details);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Renew the authentication, ignoring failures.
     *
     * @param authentication The authentication, may be {@code null}
     * @param principal The principal, may be {@code null}
     * @param renewCredentials The given renewCredentials
     * @param credentials The credentials, may be {@code null}
     * @param renewAuthorities The given renewAuthorities
     * @param authorities The authorities, may be {@code null}
     * @param renewDetails The given renewDetails
     * @param details The details, may be {@code null}
     *
     * @return The result of the operation
     */
    @Nullable
    public static Authentication renewAuthenticationQuietly(@Nullable Authentication authentication, @Nullable Object principal, boolean renewCredentials, @Nullable Object credentials, boolean renewAuthorities, @Nullable Collection<? extends GrantedAuthority> authorities, boolean renewDetails, @Nullable Object details) {
        try {
            return renewAuthentication(authentication, principal, renewCredentials, credentials, renewAuthorities, authorities, renewDetails, details);
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * @see org.springframework.security.web.authentication.WebAuthenticationDetails
     */
    public static void setAuthenticationDetails(@Nullable Authentication authentication, @Nullable Object details) {
        if (ObjectUtils.anyNull(authentication, details)) {
            return;
        }
        if (!(authentication instanceof AbstractAuthenticationToken)) {
            throw new IllegalArgumentException("Unsupported authentication type: " + authentication.getClass().getName());
        }
        ((AbstractAuthenticationToken) authentication).setDetails(details);
    }

    /**
     * Set authentication details, ignoring failures.
     *
     * @param authentication The authentication, may be {@code null}
     * @param details The details, may be {@code null}
     */
    public static void setAuthenticationDetailsQuietly(@Nullable Authentication authentication, @Nullable Object details) {
        try {
            setAuthenticationDetails(authentication, details);
        } catch (Exception ignored) {
        }
    }

    /**
     * Store the given authentication in the current security context.
     *
     * @param authentication The authentication, may be {@code null}
     */
    public static void setContextAuthentication(@Nullable Authentication authentication) {
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
    }

    /**
     * Set the principal on the current context authentication.
     *
     * @param principal The principal, may be {@code null}
     *
     * @throws IllegalAccessException If the operation fails
     * @throws IllegalArgumentException If the operation fails
     */
    public static void setContextAuthenticationPrincipal(@Nullable Object principal) throws IllegalAccessException, IllegalArgumentException {
        if (principal != null) {
            Authentication authentication = renewAuthentication(getContextAuthentication(), principal);
            setContextAuthentication(authentication);
        }
    }

    /**
     * Set the principal on the current context authentication.
     *
     * @param principal The principal, may be {@code null}
     * @param renewCredentials The given renewCredentials
     * @param credentials The credentials, may be {@code null}
     * @param renewAuthorities The given renewAuthorities
     * @param authorities The authorities, may be {@code null}
     *
     * @throws IllegalAccessException If the operation fails
     * @throws IllegalArgumentException If the operation fails
     */
    public static void setContextAuthenticationPrincipal(@Nullable Object principal, boolean renewCredentials, @Nullable Object credentials, boolean renewAuthorities, @Nullable Collection<? extends GrantedAuthority> authorities) throws IllegalAccessException, IllegalArgumentException {
        if (principal != null) {
            Authentication authentication = renewAuthentication(getContextAuthentication(), principal, renewCredentials, credentials, renewAuthorities, authorities, false, null);
            setContextAuthentication(authentication);
        }
    }

    /**
     * Set the context principal, ignoring failures.
     *
     * @param principal The principal, may be {@code null}
     */
    public static void setContextAuthenticationPrincipalQuietly(@Nullable Object principal) {
        try {
            setContextAuthenticationPrincipal(principal);
        } catch (Exception ignored) {
        }
    }

    /**
     * Set the context principal, ignoring failures.
     *
     * @param principal The principal, may be {@code null}
     * @param renewCredentials The given renewCredentials
     * @param credentials The credentials, may be {@code null}
     * @param renewAuthorities The given renewAuthorities
     * @param authorities The authorities, may be {@code null}
     */
    public static void setContextAuthenticationPrincipalQuietly(@Nullable Object principal, boolean renewCredentials, @Nullable Object credentials, boolean renewAuthorities, @Nullable Collection<? extends GrantedAuthority> authorities) {
        try {
            setContextAuthenticationPrincipal(principal, renewCredentials, credentials, renewAuthorities, authorities);
        } catch (Exception ignored) {
        }
    }

    /**
     * Set details on the current context authentication.
     *
     * @param details The details, may be {@code null}
     *
     * @throws IllegalArgumentException If the operation fails
     */
    public static void setContextAuthenticationDetails(@Nullable WebAuthenticationDetails details) throws IllegalArgumentException {
        if (details != null) {
            Authentication authentication = getContextAuthentication();
            setAuthenticationDetails(authentication, details);
            setContextAuthentication(authentication);
        }
    }

    /**
     * Set context authentication details, ignoring failures.
     *
     * @param details The details, may be {@code null}
     */
    public static void setContextAuthenticationDetailsQuietly(@Nullable WebAuthenticationDetails details) {
        try {
            setContextAuthenticationDetails(details);
        } catch (Exception ignored) {
        }
    }
}
