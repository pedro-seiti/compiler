package compiler.syntactic.automatons;

import compiler.syntactic.Automaton;
import compiler.syntactic.State;
import compiler.syntactic.Transition;
import compiler.syntactic.util.TokenType;
import compiler.syntactic.util.TransitionType;

public class ProgramAutomaton {
	public static Automaton automaton;

    public static void initialize(Automaton bstatement, Automaton end) {

        Transition t;

        Automaton program = new Automaton("PROGRAM");

        State program1 = new State();
        State program2 = new State();
        State program3 = new State();
        program3.isEnd = true;

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
        t.transitionType = TransitionType.AUTOMATON;
        t.transitionAutomaton = end;
        t.nextState = program3;
        program2.addTransition(t);

        program.initialState = program1;
        program.states.add(program1);
        program.states.add(program2);
        program.states.add(program3);

        automaton = program;
    }

}
