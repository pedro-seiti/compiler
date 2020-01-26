package compiler.syntactic.automatons;

import compiler.syntactic.Automaton;
import compiler.syntactic.State;
import compiler.syntactic.Transition;
import compiler.syntactic.util.TokenType;
import compiler.syntactic.util.TransitionType;

public class NextAutomaton {
	public static Automaton automaton;

    public static void initialize() {

        Transition t;

        Automaton next = new Automaton("NEXT");

        State next1 = new State();
        State next2 = new State();
        State next3 = new State();
        next3.isEnd = true;

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "NEXT";
        t.nextState = next2;
        next1.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.TOKEN;
        t.transitionToken = TokenType.IDENTIFIER;
        t.nextState = next3;
        next2.addTransition(t);

        next.initialState = next1;
        next.states.add(next1);
        next.states.add(next2);
        next.states.add(next3);

        automaton = next;
    }


}
