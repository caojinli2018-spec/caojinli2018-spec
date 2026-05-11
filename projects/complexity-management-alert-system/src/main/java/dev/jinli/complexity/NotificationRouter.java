package dev.jinli.complexity;

import java.util.ArrayList;
import java.util.List;

public class NotificationRouter {
    private final List<NotificationChannel> channels;
    private final List<String> sentChannels = new ArrayList<String>();

    public NotificationRouter(List<NotificationChannel> channels) {
        this.channels = channels;
    }

    public void route(Alert alert, List<String> channelNames) {
        for (String channelName : channelNames) {
            for (NotificationChannel channel : channels) {
                if (channel.name().equals(channelName)) {
                    channel.send(alert);
                    sentChannels.add(channel.name());
                }
            }
        }
    }

    public List<String> sentChannels() {
        return sentChannels;
    }
}
