package dev.jinli.complexity;

import java.util.Optional;

public class ErrorRateRule implements AlertRule {
    private final double threshold;

    public ErrorRateRule(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public Optional<RuleMatch> evaluate(SystemSignal signal) {
        if (signal.errorRate() >= threshold) {
            return Optional.of(new RuleMatch(Severity.HIGH, "error_rate_above_threshold"));
        }
        return Optional.empty();
    }
}
