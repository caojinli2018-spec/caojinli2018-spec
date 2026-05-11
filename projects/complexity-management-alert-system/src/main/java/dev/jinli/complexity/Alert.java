package dev.jinli.complexity;

import java.time.Instant;

public class Alert {
    private final String id;
    private final String service;
    private final Severity severity;
    private final AlertStatus status;
    private final Instant openedAt;
    private final String reason;

    public Alert(String id, String service, Severity severity, AlertStatus status, Instant openedAt, String reason) {
        this.id = id;
        this.service = service;
        this.severity = severity;
        this.status = status;
        this.openedAt = openedAt;
        this.reason = reason;
    }

    public String id() {
        return id;
    }

    public String service() {
        return service;
    }

    public Severity severity() {
        return severity;
    }

    public AlertStatus status() {
        return status;
    }

    public Instant openedAt() {
        return openedAt;
    }

    public String reason() {
        return reason;
    }

    public Alert transitionTo(AlertStatus nextStatus) {
        return new Alert(id, service, severity, nextStatus, openedAt, reason);
    }
}
