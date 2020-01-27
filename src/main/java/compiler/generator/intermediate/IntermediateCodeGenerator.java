package compiler.generator.intermediate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import compiler.model.Token;
import compiler.syntactic.util.TokenComparator;
import compiler.syntactic.util.TokenType;

public class IntermediateCodeGenerator {
	public static ArrayList<IntermediateCodeLine> NovasLinhas = new ArrayList<IntermediateCodeLine>();

    private static Stack<IntermediateCodeLine> Loops = new Stack<IntermediateCodeLine>();

    public static void generateLine(ArrayList<Token> linha) {

    	IntermediateCodeLine l = new IntermediateCodeLine();

        ArrayList<Token> t;

        if(linha.size() == 0) {
            return;
        }

        if(TokenComparator.getTokenType(linha.get(0).Token) == TokenType.DIGIT) {
            l.Label = linha.get(0).Token;
            linha.remove(0);
        }

        if ("LET".equals(linha.get(0).Token)) {
                l.Tipo = IntermediateCodeLine.ASSIGN;
                l.Variaveis.add(linha.get(1));
                linha.remove(0);
                linha.remove(0);
                linha.remove(0);

                l.Expressoes.add(expToRPN(linha));
                NovasLinhas.add(l);
        } else if ("PRINT".equals(linha.get(0).Token)) {
                l.Tipo = IntermediateCodeLine.PRINT;
                linha.remove(0);
                boolean fim = false;
                while(!fim) {
                    l.Variaveis.add(linha.get(0));
                    linha.remove(0);
                    if(linha.size() == 0) {
                        fim = true;
                    }
                    else {
                        linha.remove(0);
                    }
                }
                NovasLinhas.add(l);
        } else if ("FOR".equals(linha.get(0).Token)) {
                l.Tipo = IntermediateCodeLine.FOR;
                linha.remove(0);
                l.Variaveis.add(linha.get(0));
                linha.remove(0);
                linha.remove(0);

                t = new ArrayList<Token>();

                while(!linha.get(0).Token.equals("TO")) {
                    t.add(linha.get(0));
                    linha.remove(0);
                }
                l.Expressoes.add(expToRPN(t));
                linha.remove(0);

                t = new ArrayList<Token>();

                while(!linha.get(0).Token.equals("STEP")) {
                    t.add(linha.get(0));
                    linha.remove(0);
                }
                l.Expressoes.add(expToRPN(t));
                linha.remove(0);

                t = new ArrayList<Token>();

                while(linha.size() > 0) {
                    t.add(linha.get(0));
                    linha.remove(0);
                }
                l.Expressoes.add(expToRPN(t));

                l.Variaveis.add(t.get(0));

                Loops.push(l);
                NovasLinhas.add(l);
        } else if ("NEXT".equals(linha.get(0).Token)) {
                l.Tipo = IntermediateCodeLine.NEXT;
                l.Variaveis.add(linha.get(1));

                IntermediateCodeLine x = Loops.pop();
                l.Expressoes.add(x.Expressoes.get(2));
                l.Expressoes.add(x.Expressoes.get(1));
                l.Variaveis.add(x.Variaveis.get(1));
                NovasLinhas.add(l);
        } else if ("IF".equals(linha.get(0).Token)) {
                l.Tipo = IntermediateCodeLine.IF;
                linha.remove(0);
                l.Variaveis.add(linha.get(0));
                linha.remove(0);
                l.Variaveis.add(linha.get(0));

                t = new ArrayList<Token>();

                while(!linha.get(0).Token.equals("THEN")) {
                    t.add(linha.get(0));
                    linha.remove(0);
                }
                l.Expressoes.add(expToRPN(t));
                linha.remove(0);
                l.Variaveis.add(linha.get(0));
                NovasLinhas.add(l);
        } else if ("GOTO".equals(linha.get(0).Token)) {
                l.Tipo = IntermediateCodeLine.GOTO;
                l.Variaveis.add(linha.get(1));
                NovasLinhas.add(l);
        } else if ("GOSUB".equals(linha.get(0).Token)) {
                l.Tipo = IntermediateCodeLine.GOSUB;
                l.Variaveis.add(linha.get(1));
                NovasLinhas.add(l);
        } else if ("RETURN".equals(linha.get(0).Token)) {
                l.Tipo = IntermediateCodeLine.RETURN;
                NovasLinhas.add(l);
        }
    }
    
    public static ArrayList<Token> expToRPN(ArrayList<Token> tokens) {

        ArrayList<Token> lt = new ArrayList<Token>();
        Stack<Token> st = new Stack<Token>();

        Map<String,Integer> peso = new HashMap<String, Integer>();
        peso.put("*",3);
        peso.put("/",3);
        peso.put("+",2);
        peso.put("-",2);
        peso.put("(",1);

        for (Token t:tokens) {
            switch (TokenComparator.getTokenType(t.Token)) {
                case IDENTIFIER:
                    lt.add(t);
                    break;
                case DIGIT:
                    lt.add(t);
                    break;
                case SIGN:
                    if(t.Token.equals("(")) {
                        st.push(t);
                    }
                    else if (t.Token.equals(")")) {
                        while (!st.peek().Token.equals("(")) {
                            lt.add(st.pop());
                        }
                        st.pop();
                    }
                    break;
                case OPERATOR:
                    while (!st.empty() && peso.get(st.peek().Token) >= peso.get(t.Token)) {
                        lt.add(st.pop());
                    }
                    st.push(t);
                    break;
            }
        }

        while (!st.empty()) {
            lt.add(st.pop());
        }
        return lt;
    }
}
