package compiler.syntactic;

import java.util.ArrayList;
import java.util.List;

import compiler.model.Token;
import compiler.syntactic.util.TokenComparator;
import compiler.syntactic.util.TransitionType;

public class Automaton {

	public State initialState;
	public List<State> states;
	public String name;

	public Automaton(String name) {
		this.name = name;
		this.states = new ArrayList<State>();
	}

	public Result runAutomaton(ArrayList<Token> tokens) {

		boolean error = false;
		State current = initialState;

		ArrayList<Token> remainingLine = new ArrayList<Token>(tokens);

		while (!current.isEnd && !error) {

			if (!this.states.contains(current)) {
				return new Result(remainingLine, false);
			}

			for (Transition t : current.getTransitions()) {
				error = true;

				if (remainingLine.isEmpty()) {
					if (TransitionType.EMPTY.equals(t.transitionType)) {
						error = false;
						current = t.nextState;
						break;
					}
				}

				else if (TransitionType.LITERAL.equals(t.transitionType)) {
					if (remainingLine.get(0).Token.equals(t.transitionValue)) {

						current = t.nextState;
						remainingLine.remove(0);
						error = false;
						break;
					}
				} else if (TransitionType.TOKEN.equals(t.transitionType)) {
					if (TokenComparator.areSameTokenTypes(remainingLine.get(0).Token, t.transitionToken)) {
						current = t.nextState;
						remainingLine.remove(0);
						error = false;
						break;
					}
				} else if (TransitionType.REGEX.equals(t.transitionType)) {
					if (remainingLine.get(0).Token.matches(t.transitionValue)) {
						current = t.nextState;
						remainingLine.remove(0);
						error = false;
						break;
					}
				} else if (TransitionType.AUTOMATON.equals(t.transitionType)) {
					Result r = t.transitionAutomaton.runAutomaton(remainingLine);

					if (r.isSuccess) {
						remainingLine = r.remainingLine;
						current = t.nextState;
						error = false;
						break;
					} else {
						if (name == "PROGRAM" && t.transitionAutomaton.name == "BSTATEMENT") {
							continue;
						}
					}
				} else if (TransitionType.EMPTY.equals(t.transitionType)) {
					current = t.nextState;
					error = false;
					break;
				} else {
					break;
				}
			}
		}

		if (error) {
			return new Result(remainingLine, false);
		} else {
			return new Result(remainingLine, true);
		}
	}

}
