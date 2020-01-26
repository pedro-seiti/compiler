package compiler.syntactic.automatons;

import compiler.syntactic.Automaton;
import compiler.syntactic.State;
import compiler.syntactic.Transition;
import compiler.syntactic.util.TokenType;
import compiler.syntactic.util.TransitionType;

public class ExpAutomaton {
	
	public static Automaton automaton;

    public static void initialize() {

        Transition t = new Transition();

        Automaton EXP = new Automaton("EXP");
        Automaton EB = new Automaton("EB");

        State exp1 = new State();
        State exp2 = new State();
        State exp3 = new State();
        State exp4 = new State();
        exp4.isEnd = true;

        t.transitionType = TransitionType.REGEX;
        t.transitionValue = "[+\\-]";
        t.nextState = exp1;
        exp1.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.AUTOMATON;
        t.transitionAutomaton = EB;
        t.nextState = exp2;
        exp1.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.TOKEN;
        t.transitionToken = TokenType.OPERATOR;
        t.nextState = exp3;
        exp2.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.EMPTY;
        t.nextState = exp4;
        exp2.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.AUTOMATON;
        t.transitionAutomaton = EB;
        t.nextState = exp2;
        exp3.addTransition(t);

        EXP.initialState = exp1;
        EXP.states.add(exp1);
        EXP.states.add(exp2);
        EXP.states.add(exp3);
        EXP.states.add(exp4);

        // configuração do autômato EB
        State eb1 = new State();
        State eb2 = new State();
        State eb3 = new State();
        State eb4 = new State();
        State eb5 = new State();
        State eb6 = new State();
        State eb7 = new State();
        State eb8 = new State();
        eb8.isEnd = true;

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "(";
        t.nextState = eb2;
        eb1.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.TOKEN;
        t.transitionToken = TokenType.DIGIT;
        t.nextState = eb8;
        eb1.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.TOKEN;
        t.transitionToken = TokenType.IDENTIFIER;
        t.nextState = eb8;
        eb1.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "FN";
        t.nextState = eb3;
        eb1.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.AUTOMATON;
        t.transitionAutomaton = EXP;
        t.nextState = eb4;
        eb2.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = ")";
        t.nextState = eb8;
        eb4.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.REGEX;
        t.transitionValue = "[A-Z]";
        t.nextState = eb5;
        eb3.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = "(";
        t.nextState = eb6;
        eb5.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.AUTOMATON;
        t.transitionAutomaton = EXP;
        t.nextState = eb7;
        eb6.addTransition(t);

        t = new Transition();
        t.transitionType = TransitionType.LITERAL;
        t.transitionValue = ")";
        t.nextState = eb8;
        eb7.addTransition(t);

        EB.initialState = eb1;
        EB.states.add(eb1);
        EB.states.add(eb2);
        EB.states.add(eb3);
        EB.states.add(eb4);
        EB.states.add(eb5);
        EB.states.add(eb6);
        EB.states.add(eb7);
        EB.states.add(eb8);

        automaton = EXP;
    }
}
