package ru.mail.im.botapi.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IOBackoffTest {

    private final FakeSleeping fakeSleeping = new FakeSleeping();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void executeSingleFail() {
        final IOBackoff backoff = IOBackoff.newTestBuilder(fakeSleeping)
                .startTime(10)
                .maxTime(1000)
                .factor(1.5)
                .build();

        backoff.execute(new FailOperation());

        assertEquals(Collections.singletonList(10L), fakeSleeping.sleepTimeList);
    }

    @Test
    public void executeTwoSequentialFails() {
        final IOBackoff backoff = IOBackoff.newTestBuilder(fakeSleeping)
                .startTime(10)
                .maxTime(1000)
                .factor(50)
                .build();

        backoff.execute(new FailOperation());
        backoff.execute(new FailOperation());

        assertEquals(Arrays.asList(10L, 500L), fakeSleeping.sleepTimeList);
    }

    @Test
    public void executeTwoNonSequentialFails() {
        final IOBackoff backoff = IOBackoff.newTestBuilder(fakeSleeping)
                .startTime(10)
                .maxTime(1000)
                .factor(50)
                .build();

        backoff.execute(new FailOperation());
        backoff.execute(new SuccessOperation());
        backoff.execute(new FailOperation());

        assertEquals(Arrays.asList(10L, 10L), fakeSleeping.sleepTimeList);
    }

    @Test
    public void executeWithMaxLimit() {
        final IOBackoff backoff = IOBackoff.newTestBuilder(fakeSleeping)
                .startTime(10)
                .maxTime(50)
                .factor(2)
                .build();

        for (int i = 0; i < 5; i++) {
            backoff.execute(new FailOperation());
        }

        assertEquals(Arrays.asList(10L, 20L, 40L, 50L, 50L), fakeSleeping.sleepTimeList);
    }

    @Test
    public void illegalStartTime() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("startTime must be greater than zero");
        IOBackoff.newBuilder()
                .startTime(-1)
                .maxTime(1000)
                .factor(2)
                .build();
    }

    @Test
    public void illegalMaxTime() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("maxTime must be greater than zero");
        IOBackoff.newBuilder()
                .startTime(10)
                .maxTime(-1)
                .factor(2)
                .build();
    }

    @Test
    public void illegalFactor() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("factor must be greater than zero");
        IOBackoff.newBuilder()
                .startTime(10)
                .maxTime(20)
                .factor(0)
                .build();
    }

    @Test
    public void illegalStartAndMaxTime() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("startTime must be less or equal to maxTime");
        IOBackoff.newBuilder()
                .startTime(20)
                .maxTime(10)
                .factor(2)
                .build();
    }

    @Test
    public void failSleep() {
        final IOBackoff.Sleeping failSleeping = ms -> {
            throw new InterruptedException("Fake");
        };
        IOBackoff.newTestBuilder(failSleeping)
                .startTime(10)
                .maxTime(20)
                .factor(2)
                .build()
                .execute(new FailOperation());

        assertTrue(Thread.interrupted());
    }

    private static class FailOperation implements IOBackoff.IOOperation {

        @Override
        public void execute() throws IOException {
            throw new IOException("Fake");
        }
    }

    private static class SuccessOperation implements IOBackoff.IOOperation {

        @Override
        public void execute() {
        }
    }

    private static class FakeSleeping implements IOBackoff.Sleeping {

        final List<Long> sleepTimeList = new ArrayList<>();

        @Override
        public void sleep(final long ms) {
            sleepTimeList.add(ms);
        }
    }
}