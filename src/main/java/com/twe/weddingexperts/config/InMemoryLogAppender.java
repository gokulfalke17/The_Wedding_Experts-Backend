package com.twe.weddingexperts.config;

import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.LinkedList;
import java.util.List;

public class InMemoryLogAppender extends AppenderBase<ILoggingEvent> {

    private final LinkedList<String> logLines = new LinkedList<>();
    private final int maxSize = 2000; // max log entries to keep in memory

    @Override
    protected synchronized void append(ILoggingEvent eventObject) {
        String formattedMessage =
                "[" + eventObject.getLevel() + "] " + eventObject.getFormattedMessage();

        if (logLines.size() >= maxSize) {
            logLines.removeFirst();
        }
        logLines.add(formattedMessage);
    }

    public synchronized List<String> getLogs() {
        return List.copyOf(logLines); // unmodifiable copy
    }

    public synchronized void clearLogs() {
        logLines.clear();
    }
}
