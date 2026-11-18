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

package com.jiovan.plexus.spring.toolkit.cache;


import java.lang.reflect.Method;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.cache.interceptor.KeyGenerator;
import com.jiovan.plexus.common.toolkit.constant.CharVariantConst;
import com.jiovan.plexus.common.toolkit.constant.SymbolVariantConst;
import com.jiovan.plexus.common.toolkit.util.StringUtilsWraps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Abstract cache key generator by hash codes
 * <p>
 * The maximum allowed key/value size is 512 MB
 *
 * @author David Hsing
 *
 * @see org.springframework.cache.interceptor.KeyGenerator
 * @see org.springframework.cache.interceptor.SimpleKeyGenerator
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuppressWarnings("unused")
public abstract class AbstractKeyGenerator implements KeyGenerator {
    private String prefix;
    private String suffix;
    private boolean clazzName = false;
    private boolean shortClazzName = true;
    private boolean methodName = true;
    private boolean methodHash = false;

    /**
     * Generate a cache key for the given method invocation.
     *
     * @param target The target object
     * @param method The method being invoked
     * @param params The method parameters
     *
     * @return The generated cache key
     */
    @Nonnull
    @Override
    public Object generate(@Nonnull Object target, @Nonnull Method method, @Nullable Object... params) {
        beforeGenerate(target, method, params);
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isNotBlank(prefix)) {
            builder.append(StringUtilsWraps.appendIfMissing(prefix, CharVariantConst.COLON));
        }
        if (clazzName) {
            Class<?> targetClass = AopUtils.getTargetClass(target);
            String className = shortClazzName ? ClassUtils.getShortClassName(targetClass) : targetClass.getCanonicalName();
            builder.append(className).append(CharVariantConst.COLON);
        }
        if (methodName) {
            builder.append(method.getName());
        }
        if (methodHash) {
            builder.append(String.format(SymbolVariantConst.HEX_ORDER_SQUARES, Math.abs(method.hashCode())));
        }
        StringUtilsWraps.ifNotBlank(resolveParams(target, method, params), builder::append);
        if (StringUtils.isNotBlank(suffix)) {
            builder.append(StringUtilsWraps.prependIfMissing(suffix, CharVariantConst.COLON));
        }
        return afterGenerate(builder.toString());
    }

    /**
     * Hook invoked before the cache key is generated.
     *
     * @param target The target object
     * @param method The method being invoked
     * @param params The method parameters
     */
    @SuppressWarnings("unused")
    protected void beforeGenerate(@Nonnull Object target, @Nonnull Method method, @Nullable Object... params) {
    }

    /**
     * Resolve the method parameters into the parameter part of the cache key.
     *
     * @param target The target object
     * @param method The method being invoked
     * @param params The method parameters
     *
     * @return The resolved parameter part of the cache key
     */
    protected abstract String resolveParams(@Nonnull Object target, @Nonnull Method method, @Nullable Object... params);

    /**
     * Hook invoked after the cache key has been assembled.
     *
     * @param generated The assembled cache key
     *
     * @return The final cache key
     */
    protected String afterGenerate(@Nullable String generated) {
        return generated;
    }
}
