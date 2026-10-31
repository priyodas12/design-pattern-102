package learn.designpatterns.creational.singleton;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Singleton pattern.
 *
 * <p>Analogy: an application has exactly one configuration. Every part of the app reads the same
 * settings, so there must be one shared object, created once, that everyone can reach.
 *
 * <p>Uses the initialization-on-demand holder idiom: the JVM creates the instance lazily, the first
 * time {@link #getInstance()} is called, and class initialization guarantees it happens exactly once,
 * even with many threads, without explicit locking.
 */
public final class AppConfigSingleton {

    /** Counts constructor calls so tests can prove only one instance is ever created. */
    private static final AtomicInteger CREATED = new AtomicInteger();

    private final Map<String, String> settings;

    /** Private: no other class can call {@code new AppConfigSingleton()}. */
    private AppConfigSingleton() {
        CREATED.incrementAndGet();
        Map<String, String> defaults = new LinkedHashMap<>();
        defaults.put("app.name", "Design Pattern Catalog");
        defaults.put("app.locale", "en-US");
        defaults.put("app.maxUsers", "100");
        settings = Map.copyOf(defaults);
    }

    /** Holder class: not loaded, so the instance is not created, until getInstance() first runs. */
    private static final class Holder {
        private static final AppConfigSingleton INSTANCE = new AppConfigSingleton();
    }

    /** The single global access point. */
    public static AppConfigSingleton getInstance() {
        return Holder.INSTANCE;
    }

    /** How many times the constructor ran; always 1 once the instance exists. */
    static int instancesCreated() {
        return CREATED.get();
    }

    /** Returns a setting, or throws if the key is unknown. */
    public String get(String key) {
        String value = settings.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Unknown setting: " + key);
        }
        return value;
    }

    /** Returns a numeric setting, or throws if the key is unknown or its value is not a number. */
    public int getInt(String key) {
        String value = get(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Setting " + key + " is not a number: " + value, e);
        }
    }

    /** A read-only view of all settings. */
    public Map<String, String> all() {
        return settings;
    }

    public static void main(String[] args) {
        AppConfigSingleton fromLogin = AppConfigSingleton.getInstance();
        AppConfigSingleton fromReports = AppConfigSingleton.getInstance();

        System.out.println("Login screen reads app.name   = " + fromLogin.get("app.name"));
        System.out.println("Reports page reads app.locale = " + fromReports.get("app.locale"));
        System.out.println("Same object everywhere?        " + (fromLogin == fromReports));
        System.out.println("Instances created:             " + instancesCreated());
    }
}
