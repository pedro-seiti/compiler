package compiler.generator.intermediate;

import java.util.ArrayList;

import compiler.model.Token;

public class IntermediateCodeLine {
	public String Label = "!";
    public int Tipo;
    public ArrayList<ArrayList<Token>> Expressoes = new ArrayList<ArrayList<Token>>();
    public ArrayList<Token> Variaveis = new ArrayList<Token>();

    public IntermediateCodeLine(){}

    public static final int ASSIGN = 0;
    public static final int PRINT = 1;
    public static final int FOR = 2;
    public static final int NEXT = 3;
    public static final int IF = 4;
    public static final int GOTO = 5;
    public static final int GOSUB = 6;
    public static final int RETURN = 7;
    public static final int DATA = 8;
    public static final int READ = 9;


}
