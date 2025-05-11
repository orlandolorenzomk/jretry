package org.kreyzon.jretry;

import java.util.function.IntToLongFunction;

public record RetryPolicy(int maxAttempts, IntToLongFunction delayFunction) {
    public long getDelay(int attempt) {
        return delayFunction.applyAsLong(attempt);
    }
}

