package dev.jinli.complexity;

import java.util.List;
import java.util.Optional;

public class RuleEngine {
    private final List<AlertRule> rules;

    public RuleEngine(List<AlertRule> rules) {
        this.rules = rules;
    }

    public RuleMatch evaluate(SystemSignal signal) {
        for (AlertRule rule : rules) {
            Optional<RuleMatch> match = rule.evaluate(signal);
            if (match.isPresent()) {
                return match.get();
            }
        }
        return new RuleMatch(Severity.LOW, "no_rule_matched");
    }
}
