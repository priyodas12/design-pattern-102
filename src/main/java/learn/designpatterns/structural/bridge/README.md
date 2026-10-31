# Bridge

**Family:** Structural · **Status:** implemented

**Analogy:** remote controls and the devices they control. There are basic and advanced remotes, and there are TVs and radios. Instead of writing a class for every pairing (`BasicTvRemote`, `AdvancedRadioRemote`, ...), each remote holds a reference, the "bridge", to any device. The two sides can then grow independently.

Source: [`RemoteControlBridge.java`](RemoteControlBridge.java) · Test: [`RemoteControlBridgeTest.java`](../../../../../../test/java/learn/designpatterns/structural/bridge/RemoteControlBridgeTest.java)

## Intent
Decouple an abstraction from its implementation so the two can vary independently.

## When to use
- A class could grow in two independent directions, for example kinds of remote × kinds of device.
- Subclassing every combination would make the number of classes multiply.
- You want to pick or switch the implementation at runtime.

## Roles
| Role | In this example |
|------|-----------------|
| Abstraction | `RemoteControl`: `togglePower()`, `volumeUp()`, `volumeDown()` |
| Refined abstraction | `AdvancedRemote`: adds `mute()` |
| Implementor | `Device` (with the shared state in `BaseDevice`) |
| Concrete implementors | `Tv`, `Radio` |

## Diagram
```
     Abstraction side                     Implementation side
  RemoteControl ───── device ─────────>  Device (interface)
   togglePower()                          setPower(), setVolume(), status()
   volumeUp()/Down()                          ▲
        ▲                                 ┌───┴───┐
  AdvancedRemote                          Tv     Radio
   mute()
  (2 remotes × 2 devices = 4 combinations, with no class per combination)
```

## Pros
- Avoids an explosion of classes, one per combination.
- Each side can be extended alone: a new remote never touches devices, and the reverse.
- Implementations can be swapped at runtime.

## Cons
- Two hierarchies are more to understand up front.
- Overkill when there's only one implementation and no plan for more.

## Related patterns
- **Adapter:** makes existing, incompatible classes fit after the fact; Bridge separates the two sides by design.
- **Abstract Factory:** can create the right abstraction and implementation pair.
- **Strategy:** structurally similar (holding a reference to a swappable part), but used for algorithms rather than platforms.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.structural.bridge.RemoteControlBridge
mvn test -Dtest=RemoteControlBridgeTest
```
