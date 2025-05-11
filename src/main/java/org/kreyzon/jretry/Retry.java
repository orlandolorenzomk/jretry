package org.kreyzon.jretry;

import java.util.concurrent.Callable;

public class Retry {

    public static void run(Runnable task, RetryPolicy policy) {
        run(() -> {
            task.run();
            return null;
        }, policy);
    }

    public static <T> T run(Callable<T> task, RetryPolicy policy) {
        int attempt = 0;
        while (true) {
            try {
                return task.call();
            } catch (Exception e) {
                attempt++;
                if (attempt >= policy.maxAttempts()) {
                    throw new RetryException("Max retries reached", e);
                }
                try {
                    Thread.sleep(policy.getDelay(attempt));
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RetryException("Retry interrupted", ie);
                }
            }
        }
    }

    public static RetryPolicy withBackoff(int maxAttempts, long delayMillis) {
        return new RetryPolicy(maxAttempts, attempt -> delayMillis);
    }

    public static RetryPolicy withExponentialBackoff(int maxAttempts, long baseDelayMillis) {
        return new RetryPolicy(maxAttempts, attempt -> baseDelayMillis * (1L << (attempt - 1)));
    }
}
