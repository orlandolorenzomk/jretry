package org.kreyzon.jretry;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class RetryTest {

    @Test
    void shouldSucceedAfterRetries() {
        AtomicInteger counter = new AtomicInteger(0);

        String result = Retry.run(() -> {
            if (counter.incrementAndGet() < 3) {
                throw new RuntimeException("fail");
            }
            return "success";
        }, Retry.withBackoff(5, 100));

        assertEquals("success", result);
        assertEquals(3, counter.get());
    }

    @Test
    void shouldThrowAfterMaxAttempts() {
        AtomicInteger counter = new AtomicInteger(0);

        RetryException ex = assertThrows(RetryException.class, () -> {
            Retry.run(() -> {
                counter.incrementAndGet();
                throw new RuntimeException("fail");
            }, Retry.withBackoff(3, 100));
        });

        assertTrue(ex.getMessage().contains("Max retries"));
        assertEquals(3, counter.get());
    }

    @Test
    void shouldComputeExponentialBackoffCorrectly() {
        RetryPolicy policy = Retry.withExponentialBackoff(5, 100);

        assertEquals(100, policy.getDelay(1));
        assertEquals(200, policy.getDelay(2));
        assertEquals(400, policy.getDelay(3));
        assertEquals(800, policy.getDelay(4));
        assertEquals(1600, policy.getDelay(5));
    }
}
