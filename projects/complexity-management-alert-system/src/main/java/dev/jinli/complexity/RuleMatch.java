package dev.jinli.complexity;

public class RuleMatch {
    private final Severity severity;
    private final String reason;

    public RuleMatch(Severity severity, String reason) {
        this.severity = severity;
        this.reason = reason;
    }

    public Severity severity() {
        return severity;
    }

    public String reason() {
        return reason;
    }
}
