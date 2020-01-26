package compiler.syntactic.automatons;

import compiler.syntactic.Automaton;
import compiler.syntactic.State;
import compiler.syntactic.Transition;
import compiler.syntactic.util.TransitionType;

public class ReturnAutomaton {
	public static Automaton automaton;

    public static void initialize() {

        Transition t;

        Automaton _return = new Automaton("RETURN");

        State return1 = new State();
        State return2 = new State();
        return2.isEnd = true;

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "RETURN";
        t.nextState = return2;
        return1.addTransition(t);

        _return.initialState = return1;
        _return.states.add(return1);
        _return.states.add(return2);

        automaton = _return;
    }

}
