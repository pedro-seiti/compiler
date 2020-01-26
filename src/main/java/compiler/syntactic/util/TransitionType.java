package compiler.syntactic.util;

public enum TransitionType {

    LITERAL(0),
    TOKEN(1),
    REGEX(2),
    AUTOMATON(3),
    EMPTY(4);
    
    private final int value;

    TransitionType(final int value) {
        this.value = value;
    }

    public int getValue() { return value; }
}
