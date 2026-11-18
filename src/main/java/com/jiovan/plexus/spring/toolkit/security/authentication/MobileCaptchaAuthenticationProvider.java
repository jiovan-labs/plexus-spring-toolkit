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
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.util.Assert;
import com.jiovan.plexus.common.toolkit.util.ObjectUtilsWraps;
import com.jiovan.plexus.common.toolkit.util.StringUtilsWraps;
import com.jiovan.plexus.spring.toolkit.security.detail.DefaultPostAuthenticationChecker;
import com.jiovan.plexus.spring.toolkit.security.detail.DefaultPreAuthenticationChecker;
import com.jiovan.plexus.spring.toolkit.security.detail.MobileUserCache;
import com.jiovan.plexus.spring.toolkit.security.detail.MobileUserDetailsService;
import com.jiovan.plexus.spring.toolkit.security.detail.impl.NullMobileUserCache;
import com.jiovan.plexus.spring.toolkit.security.exception.BadCaptchaException;
import com.jiovan.plexus.spring.toolkit.security.exception.IllegalAuthenticationException;
import com.jiovan.plexus.spring.toolkit.security.exception.MobileNotFoundException;
import com.jiovan.plexus.spring.toolkit.util.MessageSourceWraps;
import com.jiovan.plexus.spring.toolkit.util.SecurityUtilsWraps;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * {@link org.springframework.security.authentication.AuthenticationProvider} for a mobile with a captcha
 *
 * @author David Hsing
 *
 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
 * @see org.springframework.security.authentication.dao.DaoAuthenticationProvider
 * @see org.springframework.security.authentication.DefaultAuthenticationEventPublisher
 */
@NoArgsConstructor
@Setter
@SuppressWarnings("unused")
public abstract class MobileCaptchaAuthenticationProvider implements AuthenticationProvider, ApplicationEventPublisherAware, MessageSourceAware {
    /**
     * Whether the principal should be represented as its string form.
     */
    @Getter
    private boolean forcePrincipalAsString = false;

    /**
     * The cache used to look up user details.
     */
    @Getter
    private MobileUserCache userCache = new NullMobileUserCache();

    /**
     * The service used to load user details.
     */
    @Getter
    private MobileUserDetailsService userDetailsService;

    /**
     * The mapper for granted authorities.
     */
    @Getter
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    /**
     * The checker invoked before authentication.
     */
    @Getter
    private UserDetailsChecker preAuthenticationChecker = new DefaultPreAuthenticationChecker();

    /**
     * The checker invoked after authentication.
     */
    @Getter
    private UserDetailsChecker postAuthenticationChecker = new DefaultPostAuthenticationChecker();

    /**
     * The publisher for application events.
     */
    protected ApplicationEventPublisher applicationEventPublisher;
    /**
     * The message source for resolving localized messages.
     */
    protected MessageSource messageSource;

    /**
     * Perform authentication for the given request.
     *
     * @param authentication The authentication object
     *
     * @return The populated authentication token
     *
     * @throws AuthenticationException If the operation fails
     */
    @Override
    public Authentication authenticate(@Nonnull Authentication authentication) throws AuthenticationException {
        Assert.notNull(userDetailsService, "The user details service must be set");
        Assert.notNull(applicationEventPublisher, "The application event publisher must be set");
        Assert.notNull(messageSource, "The message source must be set");
        Assert.isInstanceOf(MobileCaptchaAuthenticationToken.class, authentication, "Only supported " + MobileCaptchaAuthenticationToken.class.getSimpleName());
        String mobile = determineMobile(authentication), dial = determineDial(authentication);
        if (StringUtils.isBlank(mobile)) {
            throw new IllegalAuthenticationException(MessageSourceWraps.getMessage(messageSource, "MobileCaptchaAuthenticationFilter.illegalMobile", "Illegal mobile"));    // $NON-NLS-1$ // $NON-NLS-2$
        }
        boolean cacheUsed = true;
        UserDetails details = (userCache == null) ? null : userCache.getUserFromCache(mobile, dial);
        if (details == null) {
            cacheUsed = false;
            details = retrieveUser(mobile, authentication, dial);
            Assert.notNull(details, "retrieveUser returned null - a violation of the interface contract");
        }
        try {
            if (preAuthenticationChecker != null) {
                preAuthenticationChecker.check(details);
            }
            additionalAuthenticationChecks(details, authentication);
        } catch (AuthenticationException ex) {
            if (!cacheUsed) {
                throw ex;
            }
            // There was a problem, so try again after checking. We're using latest data (i.e. not from the cache)
            cacheUsed = false;
            details = retrieveUser(mobile, authentication, dial);
            if (preAuthenticationChecker != null) {
                preAuthenticationChecker.check(details);
            }
            additionalAuthenticationChecks(details, authentication);
        }
        if (postAuthenticationChecker != null) {
            postAuthenticationChecker.check(details);
        }
        if (!cacheUsed && userCache != null) {
            userCache.putUserInCache(details);
        }
        Object principal = forcePrincipalAsString ? details.getUsername() : details;
        return createSuccessAuthentication(principal, authentication, details, dial);
    }

    /**
     * Returns whether this provider supports the given authentication type.
     *
     * @param authentication The authentication object
     *
     * @return Whether the given authentication type is supported
     */
    @Override
    public boolean supports(@Nonnull Class<?> authentication) {
        return MobileCaptchaAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * Determine the mobile number from the given authentication.
     *
     * @param authentication The authentication object
     *
     * @return The mobile number, or {@code null}
     */
    protected String determineMobile(@Nullable Authentication authentication) {
        return (authentication == null) ? null : authentication.getName();
    }

    /**
     * Determine the dial code from the given authentication.
     *
     * @param authentication The authentication object
     *
     * @return The dial code, or {@code null}
     */
    protected String determineDial(@Nullable Authentication authentication) {
        if (authentication instanceof MobileCaptchaAuthenticationToken alias) {
            return ObjectUtilsWraps.castAs(alias.getAdditive(), String.class);
        }
        return null;
    }

    /**
     * Load the user details for the given mobile and dial code.
     *
     * @param mobile The mobile number
     * @param authentication The authentication object
     * @param dial The dial code, may be {@code null}
     *
     * @return The loaded user details
     *
     * @throws AuthenticationException If the operation fails
     */
    protected UserDetails retrieveUser(@Nonnull String mobile, @Nonnull Authentication authentication, @Nullable String dial) throws AuthenticationException {
        try {
            return userDetailsService.loadUserByMobile(mobile, dial);
        } catch (MobileNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    /**
     * Create an authenticated token from the resolved user details.
     *
     * @param principal The given principal
     * @param authentication The authentication object
     * @param details The user details
     * @param dial The dial code, may be {@code null}
     *
     * @return The authenticated token
     */
    protected Authentication createSuccessAuthentication(@Nonnull Object principal, @Nonnull Authentication authentication, @Nonnull UserDetails details, @Nullable Object dial) {
        Assert.notNull(authoritiesMapper, "The authorities mapper must be set");
        MobileCaptchaAuthenticationToken result = new MobileCaptchaAuthenticationToken(principal, authentication.getCredentials(), authoritiesMapper.mapAuthorities(details.getAuthorities()));
        result.setAuthenticated(true);
        result.setAdditive(dial);
        result.setDetails(authentication.getDetails());
        return result;
    }

    /**
     * Validate the submitted captcha against the stored value.
     *
     * @param details The user details
     * @param authentication The authentication object
     *
     * @throws AuthenticationException If the operation fails
     */
    protected void additionalAuthenticationChecks(@Nonnull UserDetails details, @Nonnull Authentication authentication) throws AuthenticationException {
        String mobile = determineMobile(authentication), dial = determineDial(authentication);
        String income = SecurityUtilsWraps.getAuthenticationCredentialsAsString(authentication);
        String stored = getStoredCaptcha(mobile, dial);
        if (StringUtilsWraps.anyNotBlank(income, stored) && !StringUtilsWraps.equals(income, stored)) {
            throw new BadCaptchaException(MessageSourceWraps.getMessage(messageSource, "MobileCaptchaAuthenticationProvider.badMobileOrCaptcha", "Bad mobile or captcha"));    // $NON-NLS-1$ // $NON-NLS-2$
        }
    }

    /**
     * The value held by this provider.
     */
    protected abstract String getStoredCaptcha(@Nonnull String mobile, @Nullable String dial);
}
