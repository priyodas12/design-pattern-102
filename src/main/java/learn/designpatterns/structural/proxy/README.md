# Proxy

**Family:** Structural · **Status:** implemented

**Analogy:** a photo gallery shows thumbnails for hundreds of photos, but loads a full photo from disk only when you open it, and keeps it once loaded. The gallery holds lightweight stand-ins (proxies) that look exactly like real images. Each one loads the real image the first time it's used.

Source: [`ImageLoaderProxy.java`](ImageLoaderProxy.java) · Test: [`ImageLoaderProxyTest.java`](../../../../../../test/java/learn/designpatterns/structural/proxy/ImageLoaderProxyTest.java)

## Intent
Provide a stand-in for another object to control access to it.

## When to use
- **Virtual proxy:** delay creating an expensive object until it's really needed, as in this example.
- **Caching proxy:** reuse an expensive result instead of recomputing it.
- **Protection proxy:** check permissions before forwarding a call.
- **Remote proxy:** make a remote object look local.

## Roles
| Role | In this example |
|------|-----------------|
| Subject | `Image`: `display()` |
| Real subject | `RealImage`: reads its pixels from `Disk` in its constructor, which is expensive |
| Proxy | `ImageProxy`: same interface; creates the `RealImage` on the first `display()`, then reuses it |
| Client | the gallery in `main`: holds `Image`s and doesn't care which kind |
| (Test helper) | `Disk`: records each read, so loading behavior can be checked |

## Diagram
```
  client ──display()──> ImageProxy ──first call only──> new RealImage(file) ──read──> Disk
                          │  real == null? create it
                          └─ later calls ──> cached RealImage.display()

  3 proxies created → 0 disk reads; beach.jpg shown twice → 1 disk read
```

## Pros
- Expensive work happens only when needed, and only once.
- The client code doesn't change, because the proxy has the same interface.
- Access control, caching or logging can be added without touching the real class.

## Cons
- The first access is slower, because it pays the loading cost.
- Adds a class and a level of indirection.
- This simple version isn't thread-safe. Concurrent first calls could load twice.

## Related patterns
- **Decorator:** the same wrapping structure, but it adds behavior instead of controlling access.
- **Adapter:** gives the wrapped object a *different* interface; a proxy keeps the *same* one.
- **Flyweight:** also saves resources, by sharing instead of deferring.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.structural.proxy.ImageLoaderProxy
mvn test -Dtest=ImageLoaderProxyTest
```
