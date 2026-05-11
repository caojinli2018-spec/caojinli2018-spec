package dev.jinli.complexity;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EscalationPolicy {
    private final Duration after;
    private final Map<Severity, List<String>> channelsBySeverity;

    public EscalationPolicy(Duration after, Map<Severity, List<String>> channelsBySeverity) {
        this.after = after;
        this.channelsBySeverity = channelsBySeverity;
    }

    public List<String> channelsFor(Severity severity) {
        List<String> channels = channelsBySeverity.get(severity);
        return channels == null ? Collections.<String>emptyList() : channels;
    }

    public boolean isOverdue(Alert alert, Instant now) {
        return Duration.between(alert.openedAt(), now).compareTo(after) > 0;
    }
}
