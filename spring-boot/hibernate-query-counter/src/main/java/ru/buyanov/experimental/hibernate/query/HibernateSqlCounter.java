package ru.buyanov.experimental.hibernate.query;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author A.Buyanov 22.02.2016.
 */
public class HibernateSqlCounter extends ClassicConverter {
    private static final AtomicBoolean shouldCount = new AtomicBoolean(false);
    private static final AtomicInteger counter = new AtomicInteger(0);

    public static void startCount() {
        counter.set(0);
        shouldCount.set(true);
    }

    public static int stopCountAndGetResult() {
        shouldCount.set(false);
        return counter.getAndSet(0);
    }

    public static int getCurrentResult() {
        return counter.get();
    }

    @Override
    public String convert(ILoggingEvent event) {
        if (!shouldCount.get())
            return "";
        if ("org.hibernate.SQL".equals(event.getLoggerName()) && event.getMessage().startsWith("select ")) {
            return "[select #" + counter.incrementAndGet() + "]";
        }
        return "";
    }
}
