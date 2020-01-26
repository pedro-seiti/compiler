package compiler.syntactic.automatons;

import compiler.syntactic.Automaton;
import compiler.syntactic.State;
import compiler.syntactic.Transition;
import compiler.syntactic.util.TokenType;
import compiler.syntactic.util.TransitionType;

public class ForAutomaton {
	public static Automaton automaton;

    public static void initialize(Automaton exp) {

        Transition t;

        Automaton _for = new Automaton();

        State for1 = new State();
        State for2 = new State();
        State for3 = new State();
        State for4 = new State();
        State for5 = new State();
        State for6 = new State();
        State for7 = new State();
        State for8 = new State();
        State for9 = new State();
        for9.isEnd = true;

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "FOR";
        t.nextState = for2;
        for1.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.TOKEN;
        t.transitionToken = TokenType.IDENTIFIER;
        t.nextState = for3;
        for2.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "=";
        t.nextState = for4;
        for3.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.AUTOMATON;
        t.transitionAutomaton = exp;
        t.nextState = for5;
        for4.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "TO";
        t.nextState = for6;
        for5.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.AUTOMATON;
        t.transitionAutomaton = exp;
        t.nextState = for7;
        for6.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.EMPTY;
        t.nextState = for9;
        for7.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "STEP";
        t.nextState = for8;
        for7.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.AUTOMATON;
        t.transitionAutomaton = exp;
        t.nextState = for9;
        for8.addTransition(t);

        _for.initialState = for1;
        _for.states.add(for1);
        _for.states.add(for2);
        _for.states.add(for3);
        _for.states.add(for4);
        _for.states.add(for5);
        _for.states.add(for6);
        _for.states.add(for7);
        _for.states.add(for8);
        _for.states.add(for9);

        automaton = _for;
    }

}
