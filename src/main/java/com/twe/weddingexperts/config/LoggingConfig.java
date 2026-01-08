package com.twe.weddingexperts.config;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {

    @Bean
    public InMemoryLogAppender inMemoryLogAppender() {
        InMemoryLogAppender appender = new InMemoryLogAppender();

        LoggerContext loggerContext =
                (LoggerContext) LoggerFactory.getILoggerFactory();

        appender.setContext(loggerContext);
        appender.start();

        Logger rootLogger =
                loggerContext.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);

        rootLogger.addAppender(appender);

        return appender;
    }
}
