# Singleton

**Family:** Creational · **Status:** implemented

**Analogy:** an application has exactly one configuration. The login screen and the reports page must read the same settings, so there is one shared configuration object, created once, that every part of the app can reach.

Source: [`AppConfigSingleton.java`](AppConfigSingleton.java) · Test: [`AppConfigSingletonTest.java`](../../../../../../test/java/learn/designpatterns/creational/singleton/AppConfigSingletonTest.java)

## Intent
Ensure a class has only one instance and provide a global point of access to it.

## When to use
- Exactly one instance must exist, such as shared configuration, a registry or a hardware gateway.
- That instance must be reachable from many places without passing it around.
- Creating it is expensive, so it should be created lazily, on first use, and only once, even under concurrency.

## Roles
| Role | In this example |
|------|-----------------|
| Singleton | `AppConfigSingleton`: private constructor, holds the settings |
| Lazy holder | `AppConfigSingleton.Holder`: its static field is created on first access |
| Global access point | `AppConfigSingleton.getInstance()` |
| Clients | the login screen and reports page in `main` |

## Diagram
```
  LoginScreen ──┐
                ├──> AppConfigSingleton.getInstance() ──> Holder.INSTANCE (created once)
  ReportsPage ──┘                                            │
                                                             └── settings: app.name, app.locale, ...
```

## Pros
- Guarantees a single instance. The holder idiom is lazy and thread-safe without locks.
- One obvious place to get shared state.
- The instance is created only if it is actually used.

## Cons
- It is global state in disguise: hidden dependencies make code harder to reason about.
- It is hard to replace in tests. Prefer passing the instance in (dependency injection) when you can.
- Mutable singletons need careful synchronization. This example keeps settings read-only.

## Related patterns
- **Abstract Factory, Builder, Prototype:** a factory or registry is often a singleton.
- **Facade:** a facade is frequently a single shared object.
- **Flyweight:** a flyweight factory usually has one shared cache, much like a singleton.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.creational.singleton.AppConfigSingleton
mvn test -Dtest=AppConfigSingletonTest
```
