package dev.jinli.complexity;

public class ConsoleChatChannel implements NotificationChannel {

    @Override
    public String name() {
        return "chat";
    }

    @Override
    public void send(Alert alert) {
        System.out.println("[CHAT] #alerts -- " + alert.severity()
                + " alert on " + alert.service()
                + ": " + alert.reason()
                + " (" + alert.id() + ")");
    }
}
