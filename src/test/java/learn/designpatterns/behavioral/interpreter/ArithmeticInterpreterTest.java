package learn.designpatterns.behavioral.interpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import learn.designpatterns.behavioral.interpreter.ArithmeticInterpreter.Add;
import learn.designpatterns.behavioral.interpreter.ArithmeticInterpreter.Expression;
import learn.designpatterns.behavioral.interpreter.ArithmeticInterpreter.Num;
import learn.designpatterns.behavioral.interpreter.ArithmeticInterpreter.Subtract;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArithmeticInterpreterTest {

    @Test
    @DisplayName("\"5 + 3 - 2\" evaluates to 6")
    void evaluatesExpression() {
        assertEquals(6, ArithmeticInterpreter.parse("5 + 3 - 2").interpret());
    }

    @Test
    @DisplayName("a single number is a valid sentence")
    void singleNumber() {
        assertEquals(42, ArithmeticInterpreter.parse("42").interpret());
    }

    @Test
    @DisplayName("operators group left to right")
    void leftAssociative() {
        assertEquals(5, ArithmeticInterpreter.parse("10 - 3 - 2").interpret());
        assertEquals("((10 - 3) - 2)", ArithmeticInterpreter.parse("10 - 3 - 2").toString());
    }

    @Test
    @DisplayName("grammar classes can be combined directly into any tree")
    void grammarClassesCompose() {
        Expression tree = new Add(new Num(2), new Subtract(new Num(9), new Num(4)));
        assertEquals(7, tree.interpret());
        assertEquals("(2 + (9 - 4))", tree.toString());
    }

    @Test
    @DisplayName("an unknown operator is rejected with the offending token")
    void unknownOperatorRejected() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> ArithmeticInterpreter.parse("5 * 3"));
        assertTrue(ex.getMessage().contains("*"), ex.getMessage());
    }

    @Test
    @DisplayName("results that don't fit in an int fail instead of wrapping around")
    void overflowDetected() {
        assertThrows(ArithmeticException.class, () -> ArithmeticInterpreter.parse("2147483647 + 1").interpret());
        assertThrows(ArithmeticException.class, () -> ArithmeticInterpreter.parse("-2147483648 - 1").interpret());
    }

    @Test
    @DisplayName("incomplete or non-numeric input is rejected")
    void malformedInputRejected() {
        assertThrows(IllegalArgumentException.class, () -> ArithmeticInterpreter.parse("5 +"));
        assertThrows(IllegalArgumentException.class, () -> ArithmeticInterpreter.parse(""));
        assertThrows(IllegalArgumentException.class, () -> ArithmeticInterpreter.parse("five + 3"));
    }
}
