package learn.designpatterns.creational.singleton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AppConfigSingletonTest {

    @Test
    @DisplayName("getInstance always returns the same object")
    void sameInstanceEveryTime() {
        assertSame(AppConfigSingleton.getInstance(), AppConfigSingleton.getInstance());
    }

    @Test
    @DisplayName("concurrent callers all get one instance, created exactly once")
    void threadSafeLazyInitialization() throws Exception {
        int threads = 16;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CountDownLatch start = new CountDownLatch(1);
        try {
            List<Future<AppConfigSingleton>> results = new ArrayList<>();
            for (int i = 0; i < threads; i++) {
                results.add(pool.submit(() -> {
                    start.await();
                    return AppConfigSingleton.getInstance();
                }));
            }
            start.countDown();
            AppConfigSingleton first = results.get(0).get(5, TimeUnit.SECONDS);
            for (Future<AppConfigSingleton> result : results) {
                assertSame(first, result.get(5, TimeUnit.SECONDS));
            }
        } finally {
            pool.shutdownNow();
        }
        assertEquals(1, AppConfigSingleton.instancesCreated());
    }

    @Test
    @DisplayName("no public constructor: nobody else can create a second instance")
    void constructorIsPrivate() {
        for (Constructor<?> constructor : AppConfigSingleton.class.getDeclaredConstructors()) {
            assertTrue(Modifier.isPrivate(constructor.getModifiers()), constructor.toString());
        }
    }

    @Test
    @DisplayName("reads shared settings")
    void readsSettings() {
        AppConfigSingleton config = AppConfigSingleton.getInstance();
        assertEquals("Design Pattern Catalog", config.get("app.name"));
        assertEquals(100, config.getInt("app.maxUsers"));
    }

    @Test
    @DisplayName("settings cannot be changed through the returned view")
    void settingsAreReadOnly() {
        assertThrows(UnsupportedOperationException.class,
                () -> AppConfigSingleton.getInstance().all().put("app.name", "Changed"));
    }

    @Test
    @DisplayName("unknown key fails with a clear message")
    void unknownKeyRejected() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> AppConfigSingleton.getInstance().get("app.theme"));
        assertTrue(ex.getMessage().contains("app.theme"), ex.getMessage());
    }
}
