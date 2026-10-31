package learn.designpatterns.behavioral.observer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import learn.designpatterns.behavioral.observer.NewsChannelObserver.NewsChannel;
import learn.designpatterns.behavioral.observer.NewsChannelObserver.PhoneApp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NewsChannelObserverTest {

    private NewsChannel channel;
    private PhoneApp ana;
    private PhoneApp ben;

    @BeforeEach
    void setUp() {
        channel = new NewsChannel("Daily Tech");
        ana = new PhoneApp("Ana");
        ben = new PhoneApp("Ben");
    }

    @Test
    @DisplayName("publishing notifies every subscriber")
    void allSubscribersNotified() {
        channel.subscribe(ana);
        channel.subscribe(ben);
        channel.publish("New phone released");

        assertEquals(List.of("Daily Tech: New phone released"), ana.notifications());
        assertEquals(List.of("Daily Tech: New phone released"), ben.notifications());
    }

    @Test
    @DisplayName("an unsubscribed reader is no longer notified")
    void unsubscribedNotNotified() {
        channel.subscribe(ana);
        channel.subscribe(ben);
        channel.unsubscribe(ben);
        channel.publish("Chip shortage ends");

        assertEquals(1, ana.notifications().size());
        assertEquals(List.of(), ben.notifications());
    }

    @Test
    @DisplayName("subscribers are notified in the order they subscribed; lambdas work as observers")
    void notificationOrder() {
        List<String> order = new ArrayList<>();
        channel.subscribe((name, headline) -> order.add("first"));
        channel.subscribe((name, headline) -> order.add("second"));
        channel.publish("Anything");
        assertEquals(List.of("first", "second"), order);
    }

    @Test
    @DisplayName("subscribing twice still means one notification per headline")
    void duplicateSubscriptionIgnored() {
        channel.subscribe(ana);
        channel.subscribe(ana);
        channel.publish("Once only");
        assertEquals(1, ana.notifications().size());
        assertEquals(1, channel.subscriberCount());
    }

    @Test
    @DisplayName("blank headlines and missing subscribers are rejected")
    void invalidInputRejected() {
        assertThrows(IllegalArgumentException.class, () -> channel.publish(" "));
        assertThrows(IllegalArgumentException.class, () -> channel.subscribe(null));
    }
}
