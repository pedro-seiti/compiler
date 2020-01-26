package compiler.syntactic.automatons;

import compiler.syntactic.Automaton;
import compiler.syntactic.State;
import compiler.syntactic.Transition;
import compiler.syntactic.util.TokenType;
import compiler.syntactic.util.TransitionType;

public class AssignAutomaton {
	public static Automaton automaton;

    public static void initialize(Automaton exp) {

        Transition t;

        Automaton assign = new Automaton();

        State assign1 = new State();
        State assign2 = new State();
        State assign3 = new State();
        State assign4 = new State();
        State assign5 = new State();
        assign5.isEnd = true;

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "LET";
        t.nextState = assign2;
        assign1.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.TOKEN;
        t.transitionToken = TokenType.IDENTIFIER;
        t.nextState = assign3;
        assign2.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "=";
        t.nextState = assign4;
        assign3.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.AUTOMATON;
        t.transitionAutomaton = exp;
        t.nextState = assign5;
        assign4.addTransition(t);

        assign.initialState = assign1;
        assign.states.add(assign1);
        assign.states.add(assign2);
        assign.states.add(assign3);
        assign.states.add(assign4);
        assign.states.add(assign5);

        automaton = assign;
    }
}
