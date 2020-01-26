package compiler.syntactic;

import java.util.ArrayList;
import java.util.List;

import Classes.Memoria;
import Sintaxe.TabelaSimbolos;
import compiler.syntactic.automatons.AssignAutomaton;
import compiler.syntactic.automatons.BStatementAutomaton;
import compiler.syntactic.automatons.DefAutomaton;
import compiler.syntactic.automatons.ExpAutomaton;
import compiler.syntactic.automatons.ForAutomaton;
import compiler.syntactic.automatons.GoSubAutomaton;
import compiler.syntactic.automatons.GoToAutomaton;
import compiler.syntactic.automatons.IfAutomaton;
import compiler.syntactic.automatons.NextAutomaton;
import compiler.syntactic.automatons.PrintAutomaton;
import compiler.syntactic.automatons.ProgramAutomaton;
import compiler.syntactic.automatons.RemarkAutomaton;
import compiler.syntactic.automatons.ReturnAutomaton;

public class Syntactic {
	
	private static Automaton lineAutomaton;
    
    public static boolean analyse(ArrayList<ArrayList<String>> lines) {
    	initialize();
    	
    	ArrayList<String> allLines = new ArrayList<String>();
   		for (ArrayList<String> line : lines) {
   			allLines.addAll(line); 
   		}
    	Result result = lineAutomaton.runAutomaton(allLines);
    	
    	if (!result.isSuccess || !result.remainingLine.isEmpty()) {
            String error = result.remainingLine.get(0);
            System.out.println(String.format("Syntactic error: %s", error));
            return false;
        }
    	
    	if (!SyntacticTable.fillTable(lines)) {
            return false;
        }

        if(!SyntacticTable.verifyReferences()) {
            return false;
        }
    	
		return true;
    }
    
    private static void initialize() {

        ExpAutomaton.initialize();
        AssignAutomaton.initialize(ExpAutomaton.automaton);
        DefAutomaton.initialize(ExpAutomaton.automaton);
        ForAutomaton.initialize(ExpAutomaton.automaton);
        GoSubAutomaton.initialize();
        GoToAutomaton.initialize();
        IfAutomaton.initialize(ExpAutomaton.automaton);
        NextAutomaton.initialize();
        PrintAutomaton.initialize(ExpAutomaton.automaton);
        RemarkAutomaton.initialize();
        ReturnAutomaton.initialize();

        List<Automaton> automata = new ArrayList<Automaton>();
        automata.add(AssignAutomaton.automaton);
        automata.add(DefAutomaton.automaton);
        automata.add(ForAutomaton.automaton);
        automata.add(GoSubAutomaton.automaton);
        automata.add(GoToAutomaton.automaton);
        automata.add(IfAutomaton.automaton);
        automata.add(NextAutomaton.automaton);
        automata.add(PrintAutomaton.automaton);
        automata.add(RemarkAutomaton.automaton);
        automata.add(ReturnAutomaton.automaton);

        BStatementAutomaton.initialize(automata);

        ProgramAutomaton.initialize(BStatementAutomaton.automaton);

        lineAutomaton = ProgramAutomaton.automaton;
    }

}
