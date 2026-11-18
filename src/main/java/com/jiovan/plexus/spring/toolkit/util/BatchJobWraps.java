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


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.ExecutionContext;
import com.jiovan.plexus.common.toolkit.constant.CharVariantConst;
import com.jiovan.plexus.common.toolkit.constant.StringVariantConst;
import com.jiovan.plexus.common.toolkit.identity.JdkUuidGenerator;


/**
 * Utilities for Spring batch
 *
 * @author David Hsing
 * @see org.springframework.batch.core.job.parameters.JobParameters
 */
@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public abstract class BatchJobWraps {
    /**
     * Return the job execution associated with the given context or step execution.
     *
     * @param context The application context, may be {@code null}
     *
     * @return The job execution, or {@code null}
     */
    public static JobExecution getJobExecution(@Nullable ChunkContext context) {
        return (context == null) ? null : context.getStepContext().getStepExecution().getJobExecution();
    }

    /**
     * Return the job execution associated with the given context or step execution.
     *
     * @param execution The job or step execution
     *
     * @return The job execution, or {@code null}
     */
    public static JobExecution getJobExecution(@Nullable StepExecution execution) {
        return (execution == null) ? null : execution.getJobExecution();
    }

    /**
     * Return the job execution context associated with the given context or step execution.
     *
     * @param context The application context, may be {@code null}
     *
     * @return The job execution context, or {@code null}
     */
    public static ExecutionContext getJobExecutionContext(@Nullable ChunkContext context) {
        return (context == null) ? null : context.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
    }

    /**
     * Return the job execution context associated with the given context or step execution.
     *
     * @param execution The job or step execution
     *
     * @return The job execution context, or {@code null}
     */
    public static ExecutionContext getJobExecutionContext(@Nullable StepExecution execution) {
        return (execution == null) ? null : execution.getJobExecution().getExecutionContext();
    }

    /**
     * Return the step execution associated with the given chunk context.
     *
     * @param context The application context, may be {@code null}
     *
     * @return The step execution, or {@code null}
     */
    public static StepExecution getStepExecution(@Nullable ChunkContext context) {
        return (context == null) ? null : context.getStepContext().getStepExecution();
    }

    /**
     * Return the step execution context associated with the given context or step execution.
     *
     * @param context The application context, may be {@code null}
     *
     * @return The step execution context, or {@code null}
     */
    public static ExecutionContext getStepExecutionContext(@Nullable ChunkContext context) {
        return (context == null) ? null : context.getStepContext().getStepExecution().getExecutionContext();
    }

    /**
     * Return the step execution context associated with the given context or step execution.
     *
     * @param execution The job or step execution
     *
     * @return The step execution context, or {@code null}
     */
    public static ExecutionContext getStepExecutionContext(@Nullable StepExecution execution) {
        return (execution == null) ? null : execution.getExecutionContext();
    }

    /**
     * Build a new set of job parameters from the given map.
     *
     * @param params The source parameter map, may be {@code null}
     *
     * @return The new job parameters
     */
    @Nonnull
    public static JobParameters newJobParameters(@Nullable Map<String, Object> params) {
        return newJobParameters(params, false);
    }

    /**
     * Returns a new job parameters, with random id capability
     *
     * @param params The source job parameters
     * @param addRandom Whether to add a random id to the job parameters, to avoid job parameters conflict
     *
     * @return a new job parameters, with random id capability
     */
    @Nonnull
    public static JobParameters newJobParameters(@Nullable Map<String, Object> params, boolean addRandom) {
        JobParametersBuilder builder = newJobParametersBuilder(params);
        if (addRandom) {
            String randomId = JdkUuidGenerator.getRandomId();
            builder.addString(StringUtils.join(StringVariantConst.RANDOM, CharVariantConst.UNDERSCORE, randomId), randomId);
        }
        return builder.toJobParameters();
    }

    /**
     * Build a job parameters builder populated from the given map.
     *
     * @param params The source parameter map, may be {@code null}
     *
     * @return The populated job parameters builder
     */
    @Nonnull
    public static JobParametersBuilder newJobParametersBuilder(@Nullable Map<String, Object> params) {
        JobParametersBuilder builder = new JobParametersBuilder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (StringUtils.isBlank(entry.getKey()) || entry.getValue() == null) {
                    continue;
                }
                if (entry.getValue() instanceof String alias) {
                    builder.addString(entry.getKey(), alias);
                } else if (entry.getValue() instanceof Integer alias) {
                    builder.addLong(entry.getKey(), alias.longValue());
                } else if (entry.getValue() instanceof Double alias) {
                    builder.addDouble(entry.getKey(), alias);
                } else if (entry.getValue() instanceof Long alias) {
                    builder.addLong(entry.getKey(), alias);
                } else if (entry.getValue() instanceof Date alias) {
                    builder.addDate(entry.getKey(), alias);
                } else if (entry.getValue() instanceof LocalDate alias) {
                    builder.addLocalDate(entry.getKey(), alias);
                } else if (entry.getValue() instanceof LocalTime alias) {
                    builder.addLocalTime(entry.getKey(), alias);
                } else if (entry.getValue() instanceof LocalDateTime alias) {
                    builder.addLocalDateTime(entry.getKey(), alias);
                } else {
                    builder.addJobParameter(entry.getKey(), entry.getValue(), Object.class);
                }
            }
        }
        return builder;
    }

    /**
     * Create a job execution listener that runs the given action before the job.
     *
     * @param action The action to invoke, may be {@code null}
     *
     * @return The configured job execution listener
     */
    @Nonnull
    public static JobExecutionListener beforeJobExecutionListener(@Nullable Consumer<JobExecution> action) {
        return new JobExecutionListener() {
            /**
             * Invoke the configured action before the job execution.
             *
             * @param execution The job or step execution
             */
            @Override
            public void beforeJob(@Nonnull JobExecution execution) {
                if (action != null) {
                    action.accept(execution);
                }
            }
        };
    }

    /**
     * Create a job execution listener that runs the given action after the job.
     *
     * @param action The action to invoke, may be {@code null}
     *
     * @return The configured job execution listener
     */
    @Nonnull
    public static JobExecutionListener afterJobExecutionListener(@Nullable Consumer<JobExecution> action) {
        return new JobExecutionListener() {
            /**
             * Invoke the configured action after the job execution.
             *
             * @param execution The job or step execution
             */
            @Override
            public void afterJob(@Nonnull JobExecution execution) {
                if (action != null) {
                    action.accept(execution);
                }
            }
        };
    }

    /**
     * Create a step execution listener that runs the given action before the step.
     *
     * @param action The action to invoke, may be {@code null}
     *
     * @return The configured step execution listener
     */
    @Nonnull
    public static StepExecutionListener beforeStepExecutionListener(@Nullable Consumer<StepExecution> action) {
        return new StepExecutionListener() {
            /**
             * Invoke the configured action before the step execution.
             *
             * @param execution The job or step execution
             */
            @Override
            public void beforeStep(@Nonnull StepExecution execution) {
                if (action != null) {
                    action.accept(execution);
                }
            }
        };
    }

    /**
     * Create a step execution listener that runs the given action after the step.
     *
     * @param action The action to invoke, may be {@code null}
     *
     * @return The configured step execution listener
     */
    @Nonnull
    public static StepExecutionListener afterStepExecutionListener(@Nullable Function<StepExecution, ExitStatus> action) {
        return new StepExecutionListener() {
            /**
             * Apply the configured action and return the resulting exit status.
             *
             * @param execution The job or step execution
             *
             * @return The resulting exit status, or {@code null}
             */
            @Override
            public ExitStatus afterStep(@Nonnull StepExecution execution) {
                return (action == null) ? null : action.apply(execution);
            }
        };
    }
}
