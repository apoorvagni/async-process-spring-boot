package com.test.async.configurations;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Custom decorator to pass application context to thread as well.
 */
public class ContextDecorator implements TaskDecorator {

    /**
     * Overriding to extract and use main thread's application context.
     *
     * @param runnable {@linkplain Runnable}.
     * @return {@linkplain Runnable}.
     */
    @Override
    public Runnable decorate(final @NotNull Runnable runnable) {
        Assert.notNull(runnable, "Can not be null or empty");
        Map<String, String> previousContext = MDC.getCopyOfContextMap();
        return () -> {
            try {
                MDC.setContextMap(previousContext);
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }

}
