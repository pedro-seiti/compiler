package compiler.syntactic.automatons;

import compiler.syntactic.Automaton;
import compiler.syntactic.State;
import compiler.syntactic.Transition;
import compiler.syntactic.util.TokenType;
import compiler.syntactic.util.TransitionType;

public class GoSubAutomaton {
	public static Automaton automaton;

    public static void initialize() {

        Transition t;

        Automaton gosub = new Automaton();

        State gosub1 = new State();
        State gosub2 = new State();
        State gosub3 = new State();
        gosub3.isEnd = true;

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "GOSUB";
        t.nextState = gosub2;
        gosub1.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.TOKEN;
        t.transitionToken = TokenType.IDENTIFIER;
        t.nextState = gosub3;
        gosub2.addTransition(t);

        gosub.initialState = gosub1;
        gosub.states.add(gosub1);
        gosub.states.add(gosub2);
        gosub.states.add(gosub3);

        automaton = gosub;
    }

}
