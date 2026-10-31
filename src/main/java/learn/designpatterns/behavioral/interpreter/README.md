# Interpreter

**Family:** Behavioral · **Status:** implemented

**Analogy:** a pocket calculator that understands a tiny language: whole numbers joined by `+` and `-`, with spaces between tokens, such as `"5 + 3 - 2"`. Each grammar rule becomes a class (a number, an addition, a subtraction). A sentence becomes a tree of those objects, and evaluating the tree gives the answer.

Source: [`ArithmeticInterpreter.java`](ArithmeticInterpreter.java) · Test: [`ArithmeticInterpreterTest.java`](../../../../../../test/java/learn/designpatterns/behavioral/interpreter/ArithmeticInterpreterTest.java)

## Intent
Define a grammar for a small language and an interpreter that evaluates sentences in it.

## When to use
- You have a simple, stable language: filters, rules, small formulas, query snippets.
- The grammar is small enough that one class per rule stays manageable.
- Speed isn't critical, because walking an object tree is slower than compiled code.

## Roles
| Role | In this example |
|------|-----------------|
| Abstract expression | `Expression`: `interpret()` |
| Terminal expression | `Num`: a number literal |
| Non-terminal expressions | `Add`, `Subtract`: combine a left and a right `Expression` |
| Context / parser | `ArithmeticInterpreter.parse()`: turns text into a tree (left to right) |
| Client | `main` and the tests: parse, then call `interpret()` |

Grammar: `expression := number ( ("+" | "-") number )*`

## Diagram
```
  "5 + 3 - 2"  ──parse──>        Subtract
                                 /       \
                               Add       Num(2)
                              /   \
                          Num(5)  Num(3)

  interpret(): (5 + 3) - 2 = 6        toString(): "((5 + 3) - 2)"
```

## Pros
- Each grammar rule is a small, testable class.
- The language is easy to extend: add `Multiply` as a new class.
- Expression trees can also be built directly in code, without parsing.

## Cons
- Large grammars produce too many classes. Use a parser generator instead.
- No operator precedence or parentheses in this tiny grammar. Adding them needs a real parser.
- Tokens must be separated by spaces (`"5+3"` is rejected as not a number).

## Related patterns
- **Composite:** the expression tree *is* a composite, with numbers as leaves.
- **Visitor:** adds new operations over the tree (pretty-print, simplify) without changing the expression classes.
- **Flyweight:** terminal symbols such as common numbers can be shared.

## Run it
```bash
mvn -q compile exec:java -Dexec.mainClass=learn.designpatterns.behavioral.interpreter.ArithmeticInterpreter
mvn test -Dtest=ArithmeticInterpreterTest
```
