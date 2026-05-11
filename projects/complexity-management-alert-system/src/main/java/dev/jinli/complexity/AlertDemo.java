package dev.jinli.complexity;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlertDemo {

    public static void main(String[] args) {
        Clock clock = Clock.fixed(Instant.parse("2026-05-11T08:00:00Z"), ZoneOffset.UTC);

        RuleEngine rules = new RuleEngine(Arrays.<AlertRule>asList(
                new CpuPressureRule(90),
                new ErrorRateRule(0.05)
        ));

        Map<Severity, List<String>> channelMap = new HashMap<Severity, List<String>>();
        channelMap.put(Severity.CRITICAL, Arrays.asList("pager", "chat", "email"));
        channelMap.put(Severity.HIGH, Arrays.asList("chat", "email"));

        EscalationPolicy policy = new EscalationPolicy(Duration.ofMinutes(10), channelMap);

        NotificationRouter router = new NotificationRouter(Arrays.<NotificationChannel>asList(
                new ConsolePagerChannel(),
                new ConsoleChatChannel(),
                new ConsoleEmailChannel()
        ));

        InMemoryAlertStore store = new InMemoryAlertStore();
        InMemoryAuditLog auditLog = new InMemoryAuditLog();

        AlertWorkflow workflow = new AlertWorkflow(rules, policy, router, store, auditLog, clock);

        System.out.println("=== Ingesting signal: checkout (cpu=96%, errorRate=1%) ===");
        Alert alert1 = workflow.ingest(new SystemSignal("checkout", 96, 0.01));
        System.out.println();

        System.out.println("=== Acknowledging " + alert1.id() + " ===");
        workflow.acknowledge(alert1.id(), "on-call-engineer");
        System.out.println("Status: " + store.get(alert1.id()).get().status());
        System.out.println();

        System.out.println("=== Resolving " + alert1.id() + " ===");
        workflow.resolve(alert1.id(), "rollback completed");
        System.out.println("Status: " + store.get(alert1.id()).get().status());
        System.out.println();

        System.out.println("=== Ingesting signal: payment (cpu=50%, errorRate=8%) ===");
        Alert alert2 = workflow.ingest(new SystemSignal("payment", 50, 0.08));
        System.out.println();

        System.out.println("=== Checking escalation at +9 minutes (should NOT escalate) ===");
        workflow.escalateOverdueAlerts(Instant.parse("2026-05-11T08:09:00Z"));
        System.out.println("Status: " + store.get(alert2.id()).get().status());
        System.out.println();

        System.out.println("=== Checking escalation at +11 minutes (should escalate) ===");
        workflow.escalateOverdueAlerts(Instant.parse("2026-05-11T08:11:00Z"));
        System.out.println("Status: " + store.get(alert2.id()).get().status());
        System.out.println();

        System.out.println("=== Audit Log ===");
        for (String entry : auditLog.entries()) {
            System.out.println("  " + entry);
        }
    }
}
