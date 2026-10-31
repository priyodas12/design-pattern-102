package learn.designpatterns.behavioral.observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Observer pattern.
 *
 * <p>Analogy: a news channel and its subscribers. Readers subscribe once; whenever the channel
 * publishes a headline, every current subscriber is notified automatically. The channel doesn't know
 * what each subscriber does with the news, and readers can unsubscribe at any time.
 */
public final class NewsChannelObserver {

    private NewsChannelObserver() {
    }

    /** Observer: anything that wants to hear about new headlines. One method, so lambdas work too. */
    @FunctionalInterface
    interface Subscriber {
        void update(String channelName, String headline);
    }

    /** Subject: keeps the subscriber list and notifies everyone on publish. */
    static final class NewsChannel {
        private final String name;
        private final List<Subscriber> subscribers = new ArrayList<>();

        NewsChannel(String name) {
            this.name = name;
        }

        /** Subscribing twice has no extra effect. */
        void subscribe(Subscriber subscriber) {
            if (subscriber == null) {
                throw new IllegalArgumentException("Subscriber must not be null");
            }
            if (!subscribers.contains(subscriber)) {
                subscribers.add(subscriber);
            }
        }

        void unsubscribe(Subscriber subscriber) {
            subscribers.remove(subscriber);
        }

        /** Notifies subscribers in the order they subscribed. Iterates a copy, so a subscriber may unsubscribe while being notified. */
        void publish(String headline) {
            if (headline == null || headline.isBlank()) {
                throw new IllegalArgumentException("Headline must not be blank");
            }
            for (Subscriber subscriber : List.copyOf(subscribers)) {
                subscriber.update(name, headline);
            }
        }

        int subscriberCount() {
            return subscribers.size();
        }
    }

    /** Concrete observer: a phone app that shows notifications. */
    static final class PhoneApp implements Subscriber {
        private final String owner;
        private final List<String> notifications = new ArrayList<>();

        PhoneApp(String owner) {
            this.owner = owner;
        }

        @Override
        public void update(String channelName, String headline) {
            notifications.add(channelName + ": " + headline);
        }

        List<String> notifications() {
            return Collections.unmodifiableList(notifications);
        }

        @Override
        public String toString() {
            return owner + "'s phone";
        }
    }

    public static void main(String[] args) {
        NewsChannel channel = new NewsChannel("Daily Tech");
        PhoneApp ana = new PhoneApp("Ana");
        PhoneApp ben = new PhoneApp("Ben");
        channel.subscribe(ana);
        channel.subscribe(ben);
        channel.subscribe((name, headline) -> System.out.println("[TV ticker] " + name + ": " + headline));

        channel.publish("New phone released");
        channel.unsubscribe(ben);
        channel.publish("Chip shortage ends");

        System.out.println(ana + " got " + ana.notifications());
        System.out.println(ben + " got " + ben.notifications());
    }
}
