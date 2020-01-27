package compiler.syntactic.automatons;

import compiler.syntactic.Automaton;
import compiler.syntactic.State;
import compiler.syntactic.Transition;
import compiler.syntactic.util.TokenType;
import compiler.syntactic.util.TransitionType;

public class GoToAutomaton {
	 public static Automaton automaton;

	    public static void initialize() {

	        Transition t;

	        Automaton _goto = new Automaton("GOTO");

	        State goto1 = new State();
	        State goto2 = new State();
	        State goto3 = new State();
	        goto3.isEnd = true;

	        t = new Transition();
	        t.transitionType = TransitionType.LITERAL;
	        t.transitionValue = "GOTO";
	        t.nextState = goto2;
	        goto1.addTransition(t);

	        t = new Transition();
	        t.transitionType = TransitionType.TOKEN;
	        t.transitionToken = TokenType.DIGIT;
	        t.nextState = goto3;
	        goto2.addTransition(t);

	        t = new Transition();
	        t.transitionType = TransitionType.TOKEN;
	        t.transitionToken = TokenType.STOP;
	        t.nextState = goto3;
	        goto2.addTransition(t);

	        _goto.initialState = goto1;
	        _goto.states.add(goto1);
	        _goto.states.add(goto2);
	        _goto.states.add(goto3);

	        automaton = _goto;
	    }

}
