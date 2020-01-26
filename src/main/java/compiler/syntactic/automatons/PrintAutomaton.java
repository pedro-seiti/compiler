package compiler.syntactic.automatons;

import compiler.syntactic.Automaton;
import compiler.syntactic.State;
import compiler.syntactic.Transition;
import compiler.syntactic.util.TokenType;
import compiler.syntactic.util.TransitionType;

public class PrintAutomaton {
	public static Automaton automaton;

    public static void initialize(Automaton exp) {

        Transition t;

        Automaton print = new Automaton("PRINT");

        State print1 = new State();
        State print2 = new State();
        State print3 = new State();
        State print4 = new State();
        print4.isEnd = true;

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "PRINT";
        t.nextState = print2;
        print1.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.AUTOMATON;
        t.transitionAutomaton = exp;
        t.nextState = print3;
        print2.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.TOKEN;
        t.transitionToken = TokenType.STRING;
        t.nextState = print3;
        print2.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = ",";
        t.nextState = print2;
        print3.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.EMPTY;
        t.nextState = print4;
        print3.addTransition(t);

        print.initialState = print1;
        print.states.add(print1);
        print.states.add(print2);
        print.states.add(print3);
        print.states.add(print4);

        automaton = print;
    }

}
