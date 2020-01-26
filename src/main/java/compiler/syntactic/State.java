package compiler.syntactic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class State {
	public boolean isEnd;
    private List<Transition> transitions;


    public State() {
        this.transitions = new ArrayList<Transition>();
        this.isEnd = false;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public void addTransition(Transition t) {
    	this.transitions.add(t);
    	orderTransitions();
    }

    private void orderTransitions() {
    	Collections.sort(this.transitions, new Comparator<Transition>() {
            public int compare(Transition p1, Transition p2) {
                return p1.transitionType.getValue() - p2.transitionType.getValue();
            }

        });
    }
}
