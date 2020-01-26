package compiler.syntactic.automatons;

import compiler.syntactic.Automaton;
import compiler.syntactic.State;
import compiler.syntactic.Transition;
import compiler.syntactic.util.TokenType;
import compiler.syntactic.util.TransitionType;

public class RemarkAutomaton {
	public static Automaton automaton;

    public static void initialize() {

        Transition t;

        Automaton remark = new Automaton();

        State rem1 = new State();
        State rem2 = new State();
        State rem3 = new State();
        rem3.isEnd = true;

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "REM";
        t.nextState = rem2;
        rem1.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.TOKEN;
        t.transitionToken = TokenType.STRING;
        t.nextState = rem3;
        rem2.addTransition(t);

        remark.initialState = rem1;
        remark.states.add(rem1);
        remark.states.add(rem2);
        remark.states.add(rem3);

        automaton = remark;
    }

}
