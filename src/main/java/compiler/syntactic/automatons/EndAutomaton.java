package compiler.syntactic.automatons;

import compiler.syntactic.Automaton;
import compiler.syntactic.State;
import compiler.syntactic.Transition;
import compiler.syntactic.util.TokenType;
import compiler.syntactic.util.TransitionType;

public class EndAutomaton {
	public static Automaton automaton;

    public static void initialize() {

        Transition t;

        Automaton end = new Automaton("END");

        State end1 = new State();
        State end2 = new State();
        State end3 = new State();
        end3.isEnd = true;

        t = new Transition();
        t.transitionType = TransitionType.TOKEN;
        t.transitionToken = TokenType.DIGIT;
        t.nextState = end2;
        end1.addTransition(t);
        
        t = new Transition();
        t.transitionType = TransitionType.TOKEN;
        t.transitionToken = TokenType.END;
        t.nextState = end3;
        end2.addTransition(t);

        end.initialState = end1;
        end.states.add(end1);
        end.states.add(end2);
        end.states.add(end3);

        automaton = end;
    }

}
