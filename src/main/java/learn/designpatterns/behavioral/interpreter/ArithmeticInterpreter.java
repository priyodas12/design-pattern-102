package learn.designpatterns.behavioral.interpreter;

/**
 * Interpreter pattern.
 *
 * <p>Analogy: a pocket calculator that understands a tiny language, whole numbers joined by
 * {@code +} and {@code -} with spaces between tokens, for example {@code "5 + 3 - 2"}. Each grammar
 * rule becomes a class (a number, an addition, a subtraction), a sentence becomes a tree of those
 * objects, and evaluating the tree gives the answer.
 *
 * <p>Grammar: {@code expression := number ( ("+" | "-") number )*}, evaluated left to right.
 */
public final class ArithmeticInterpreter {

    private ArithmeticInterpreter() {
    }

    /** Abstract expression: every grammar rule can interpret itself. */
    interface Expression {
        int interpret();
    }

    /** Terminal expression: a number. */
    record Num(int value) implements Expression {
        public int interpret() {
            return value;
        }

        @Override
        public String toString() {
            return Integer.toString(value);
        }
    }

    /** Non-terminal expressions: combine two sub-expressions. Overflow throws ArithmeticException. */
    record Add(Expression left, Expression right) implements Expression {
        public int interpret() {
            return Math.addExact(left.interpret(), right.interpret());
        }

        @Override
        public String toString() {
            return "(" + left + " + " + right + ")";
        }
    }

    record Subtract(Expression left, Expression right) implements Expression {
        public int interpret() {
            return Math.subtractExact(left.interpret(), right.interpret());
        }

        @Override
        public String toString() {
            return "(" + left + " - " + right + ")";
        }
    }

    /** Builds the expression tree for a sentence such as "5 + 3 - 2" (tokens separated by spaces). */
    static Expression parse(String sentence) {
        if (sentence == null || sentence.isBlank()) {
            throw new IllegalArgumentException("Expression must not be empty");
        }
        String[] tokens = sentence.trim().split("\\s+");
        if (tokens.length % 2 == 0) {
            throw new IllegalArgumentException("Incomplete expression: '" + sentence + "'");
        }
        Expression tree = number(tokens[0]);
        for (int i = 1; i < tokens.length; i += 2) {
            Expression right = number(tokens[i + 1]);
            tree = switch (tokens[i]) {
                case "+" -> new Add(tree, right);
                case "-" -> new Subtract(tree, right);
                default -> throw new IllegalArgumentException("Unknown operator '" + tokens[i] + "' (supported: + -)");
            };
        }
        return tree;
    }

    private static Num number(String token) {
        try {
            return new Num(Integer.parseInt(token));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Not a number: '" + token + "'", e);
        }
    }

    public static void main(String[] args) {
        for (String sentence : new String[] {"5 + 3 - 2", "10 - 3 - 2", "100"}) {
            Expression tree = parse(sentence);
            System.out.println(sentence + "  ->  tree " + tree + "  =  " + tree.interpret());
        }
    }
}
