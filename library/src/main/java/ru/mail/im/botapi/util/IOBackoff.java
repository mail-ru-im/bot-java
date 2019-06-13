package ru.mail.im.botapi.util;

import java.io.IOException;

public class IOBackoff {

    private final long startTime;
    private final long maxTime;
    private final double factor;
    private final Sleeping sleeping;

    private long currentTime;

    private IOBackoff(final Builder builder) {
        startTime = builder.startTime;
        maxTime = builder.maxTime;
        factor = builder.factor;
        sleeping = builder.sleeping;

        reset();
    }

    public void execute(final IOOperation operation) {
        try {
            operation.execute();
            reset();
        } catch (IOException ignored) {
            waitNext();
        }
    }

    private void reset() {
        currentTime = startTime;
    }

    private void waitNext() {
        try {
            sleeping.sleep(currentTime);
            currentTime = Math.min(maxTime, (long) (currentTime * factor));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static Builder newBuilder() {
        return new Builder(Thread::sleep);
    }

    static Builder newTestBuilder(final Sleeping sleeping) {
        return new Builder(sleeping);
    }

    public static final class Builder {

        private final Sleeping sleeping;

        private long startTime;
        private long maxTime;
        private double factor;

        private Builder(final Sleeping sleeping) {
            this.sleeping = sleeping;
        }

        public Builder startTime(final long startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder maxTime(final long maxTime) {
            this.maxTime = maxTime;
            return this;
        }

        public Builder factor(final double factor) {
            this.factor = factor;
            return this;
        }

        public IOBackoff build() {
            if (startTime <= 0) {
                throw new IllegalArgumentException("startTime must be greater than zero");
            }
            if (maxTime <= 0) {
                throw new IllegalArgumentException("maxTime must be greater than zero");
            }
            if (factor <= 0) {
                throw new IllegalArgumentException("factor must be greater than zero");
            }
            if (startTime > maxTime) {
                throw new IllegalArgumentException("startTime must be less or equal to maxTime");
            }
            return new IOBackoff(this);
        }
    }

    public interface IOOperation {
        void execute() throws IOException;
    }

    interface Sleeping {
        void sleep(final long ms) throws InterruptedException;
    }
}
