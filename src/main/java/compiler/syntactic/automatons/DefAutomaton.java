package compiler.syntactic.automatons;

import compiler.syntactic.Automaton;
import compiler.syntactic.State;
import compiler.syntactic.Transition;
import compiler.syntactic.util.TransitionType;

public class DefAutomaton {

	public static Automaton automaton;

    public static void initialize(Automaton exp) {

        Transition t;

        Automaton def = new Automaton("DEF");

        State def1 = new State();
        State def2 = new State();
        State def3 = new State();
        State def4 = new State();
        State def5 = new State();
        State def6 = new State();
        State def7 = new State();
        State def8 = new State();
        State def9 = new State();
        def9.isEnd = true;

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "DEF";
        t.nextState = def2;
        def1.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "FN";
        t.nextState = def3;
        def2.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.REGEX;
        t.transitionValue = "[A-VY-Z]";
        t.nextState = def4;
        def3.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "(";
        t.nextState = def5;
        def4.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "X";
        t.nextState = def6;
        def5.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = ")";
        t.nextState = def7;
        def6.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "=";
        t.nextState = def8;
        def7.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.AUTOMATON;
        t.transitionAutomaton = exp;
        t.nextState = def9;
        def8.addTransition(t);

        def.initialState = def1;
        def.states.add(def1);
        def.states.add(def2);
        def.states.add(def3);
        def.states.add(def4);
        def.states.add(def5);
        def.states.add(def6);
        def.states.add(def7);
        def.states.add(def8);
        def.states.add(def9);

        automaton = def;
    }

}
