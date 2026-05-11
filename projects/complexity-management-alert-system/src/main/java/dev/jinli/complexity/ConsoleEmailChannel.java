package dev.jinli.complexity;

public class ConsoleEmailChannel implements NotificationChannel {

    @Override
    public String name() {
        return "email";
    }

    @Override
    public void send(Alert alert) {
        System.out.println("[EMAIL] To: oncall@example.com");
        System.out.println("        Subject: " + alert.severity() + " Alert -- " + alert.service());
        System.out.println("        Body: " + alert.reason()
                + " | id=" + alert.id()
                + " | status=" + alert.status());
    }
}
