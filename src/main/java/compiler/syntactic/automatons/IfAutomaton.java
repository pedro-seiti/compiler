package compiler.syntactic.automatons;

import compiler.syntactic.Automaton;
import compiler.syntactic.State;
import compiler.syntactic.Transition;
import compiler.syntactic.util.TokenType;
import compiler.syntactic.util.TransitionType;

public class IfAutomaton {
	public static Automaton automaton;

    public static void initialize(Automaton exp) {

        Transition t;

        Automaton _if = new Automaton("IF");

        State if1 = new State();
        State if2 = new State();
        State if3 = new State();
        State if4 = new State();
        State if5 = new State();
        State if6 = new State();
        State if7 = new State();
        if7.isEnd = true;

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "IF";
        t.nextState = if2;
        if1.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.AUTOMATON;
        t.transitionAutomaton = exp;
        t.nextState = if3;
        if2.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.TOKEN;
        t.transitionToken = TokenType.COMPARATOR;
        t.nextState = if4;
        if3.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.AUTOMATON;
        t.transitionAutomaton = exp;
        t.nextState = if5;
        if4.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "THEN";
        t.nextState = if6;
        if5.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.TOKEN;
        t.transitionToken = TokenType.DIGIT;
        t.nextState = if7;
        if6.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.TOKEN;
        t.transitionToken = TokenType.STOP;
        t.nextState = if7;
        if6.addTransition(t);

        _if.initialState = if1;
        _if.states.add(if1);
        _if.states.add(if2);
        _if.states.add(if3);
        _if.states.add(if4);
        _if.states.add(if5);
        _if.states.add(if6);
        _if.states.add(if7);

        automaton = _if;
    }

}
