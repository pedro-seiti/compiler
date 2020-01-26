package compiler.syntactic;

import compiler.syntactic.util.TokenType;
import compiler.syntactic.util.TransitionType;

public class Transition {

	public TransitionType transitionType;
    public String transitionValue;
    public TokenType transitionToken;
    public Automaton transitionAutomaton;
    public State nextState;
    
}
