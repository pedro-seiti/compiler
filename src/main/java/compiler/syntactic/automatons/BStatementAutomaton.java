package compiler.syntactic.automatons;

import java.util.List;

import compiler.syntactic.Automaton;
import compiler.syntactic.State;
import compiler.syntactic.Transition;
import compiler.syntactic.util.TokenType;
import compiler.syntactic.util.TransitionType;

public class BStatementAutomaton {
	public static Automaton automaton;

    public static void initialize(List<Automaton> automata) {

        Transition t;

        Automaton bstatement = new Automaton();

        State bstatement1 = new State();
        State bstatement2 = new State();
        State bstatement3 = new State();
        State bstatement4 = new State();
        bstatement4.isEnd = true;

        t = new Transition();
        t.transitionType = TransitionType.TOKEN;
        t.transitionToken = TokenType.IDENTIFIER;
        t.nextState = bstatement2;
        bstatement1.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = ":";
        t.nextState = bstatement3;
        bstatement2.addTransition(t);

        for (Automaton a : automata) {
            t = new Transition();
            t.transitionType = TransitionType.AUTOMATON;
            t.transitionAutomaton = a;
            t.nextState = bstatement4;
            bstatement1.addTransition(t);
        }

        t = new Transition();
        t.transitionType = TransitionType.TOKEN;
        t.transitionToken = TokenType.IDENTIFIER;
        t.nextState = bstatement2;
        bstatement1.addTransition(t);

        for (Automaton a : automata) {
            t = new Transition();
            t.transitionType = TransitionType.AUTOMATON;
            t.transitionAutomaton = a;
            t.nextState = bstatement4;
            bstatement3.addTransition(t);
        }

        bstatement.initialState = bstatement1;
        bstatement.states.add(bstatement1);
        bstatement.states.add(bstatement2);
        bstatement.states.add(bstatement3);
        bstatement.states.add(bstatement4);

        automaton = bstatement;
    }

}
