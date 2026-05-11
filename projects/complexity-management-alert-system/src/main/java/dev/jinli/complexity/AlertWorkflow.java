package dev.jinli.complexity;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

public class AlertWorkflow {
    private final RuleEngine ruleEngine;
    private final EscalationPolicy escalationPolicy;
    private final NotificationRouter notificationRouter;
    private final AlertStore alertStore;
    private final AuditLog auditLog;
    private final Clock clock;
    private int sequence = 0;

    public AlertWorkflow(
            RuleEngine ruleEngine,
            EscalationPolicy escalationPolicy,
            NotificationRouter notificationRouter,
            AlertStore alertStore,
            AuditLog auditLog,
            Clock clock
    ) {
        this.ruleEngine = ruleEngine;
        this.escalationPolicy = escalationPolicy;
        this.notificationRouter = notificationRouter;
        this.alertStore = alertStore;
        this.auditLog = auditLog;
        this.clock = clock;
    }

    public Alert ingest(SystemSignal signal) {
        RuleMatch match = ruleEngine.evaluate(signal);
        Alert alert = new Alert(nextId(), signal.service(), match.severity(), AlertStatus.OPEN, clock.instant(), match.reason());
        alertStore.save(alert);
        auditLog.record("created " + alert.id() + " because " + match.reason());
        notificationRouter.route(alert, escalationPolicy.channelsFor(alert.severity()));
        return alert;
    }

    public void acknowledge(String alertId, String actor) {
        Alert alert = alertStore.get(alertId).get();
        alertStore.save(alert.transitionTo(AlertStatus.ACKNOWLEDGED));
        auditLog.record("acknowledged " + alertId + " by " + actor);
    }

    public void resolve(String alertId, String reason) {
        Alert alert = alertStore.get(alertId).get();
        alertStore.save(alert.transitionTo(AlertStatus.RESOLVED));
        auditLog.record("resolved " + alertId + " because " + reason);
    }

    public void escalateOverdueAlerts(Instant now) {
        for (Alert alert : alertStore.openAlerts()) {
            if (escalationPolicy.isOverdue(alert, now)) {
                alertStore.save(alert.transitionTo(AlertStatus.ESCALATED));
                notificationRouter.route(alert, escalationPolicy.channelsFor(alert.severity()));
                auditLog.record("escalated " + alert.id() + " after " + Duration.between(alert.openedAt(), now));
            }
        }
    }

    private String nextId() {
        sequence += 1;
        return "alert-" + sequence;
    }
}
