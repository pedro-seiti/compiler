package compiler.syntactic.automatons;

import compiler.syntactic.Automaton;
import compiler.syntactic.State;
import compiler.syntactic.Transition;
import compiler.syntactic.util.TokenType;
import compiler.syntactic.util.TransitionType;

public class ProgramAutomaton {
	public static Automaton automaton;

    public static void initialize(Automaton bstatement) {

        Transition t;

        Automaton program = new Automaton("PROGRAM");

        State program1 = new State();
        State program2 = new State();
        State program3 = new State();
        State program4 = new State();
        program4.isEnd = true;

        t = new Transition();
        t.transitionType = TransitionType.AUTOMATON;
        t.transitionAutomaton = bstatement;
        t.nextState = program2;
        program1.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.AUTOMATON;
        t.transitionAutomaton = bstatement;
        t.nextState = program2;
        program2.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.TOKEN;
        t.transitionToken = TokenType.DIGIT;
        t.nextState = program3;
        program2.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.TOKEN;
        t.transitionToken = TokenType.END;
        t.nextState = program4;
        program3.addTransition(t);

        program.initialState = program1;
        program.states.add(program1);
        program.states.add(program2);
        program.states.add(program3);
        program.states.add(program4);

        automaton = program;
    }

}
