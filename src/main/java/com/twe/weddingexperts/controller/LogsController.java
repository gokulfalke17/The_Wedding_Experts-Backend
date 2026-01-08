package com.twe.weddingexperts.controller;

import com.twe.weddingexperts.config.InMemoryLogAppender;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogsController {

    private final InMemoryLogAppender logAppender;

    public LogsController(InMemoryLogAppender logAppender) {
        this.logAppender = logAppender;
    }

    @GetMapping
    public List<String> getLogs() {
        return logAppender.getLogs();
    }

    @DeleteMapping
    public String clearLogs() {
        logAppender.clearLogs();
        return "Logs cleared successfully";
    }
}
