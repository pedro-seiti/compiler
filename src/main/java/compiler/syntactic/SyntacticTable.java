package compiler.syntactic;

import java.util.ArrayList;

import compiler.model.Token;
import compiler.syntactic.table.Identifier;
import compiler.syntactic.util.TokenComparator;
import compiler.syntactic.util.TokenType;

public class SyntacticTable {
	public static ArrayList<Identifier> IDENTIFIERS = new ArrayList<Identifier>();
    public static ArrayList<Identifier> IDENT_DECLARE = new ArrayList<Identifier>();

    private static ArrayList<Identifier> FOR_NEXT = new ArrayList<Identifier>();
    
    public static boolean fillTable(ArrayList<ArrayList<Token>> code) {

        ArrayList<ArrayList<Token>> tempCode = new ArrayList<ArrayList<Token>>();

        boolean isSuccess;
        int i = 0;

        for (ArrayList<Token> l : code) {
        	tempCode.add(new ArrayList<Token>());
        	tempCode.get(i).addAll(l);
            i++;
        }

        isSuccess = searchInLabels(tempCode);

        if(!isSuccess) {
            return false;
        }

        isSuccess = searchInFunctions(tempCode);

        if(!isSuccess) {
            return false;
        }

        isSuccess = searchInVariables(tempCode);

        if(!isSuccess) {
            return false;
        }

        return true;
    }
    
    public static boolean verifyReferences() {

        for (Identifier i:IDENTIFIERS) {

            boolean isSuccess = false;

            int labelCount = 0;
            int funcCount = 0;

            boolean funcRecursao = false;

loop:       for (Identifier i2:IDENT_DECLARE) {

                if(i.name.equals(i2.name)) {

                    if(i.type != i2.type) {
                        System.out.println(String.format("Error: Identifier \"%s\" (line %d) is not expected token - line %d",
                                i.name,i.line,i2.line));
                        return false;
                    }
                    else {
                        switch (i.type) {
                            case Identifier.VARIABLE:
                                if(i2.line < i.line) {
                                    isSuccess = true;
                                    break loop;
                                }
                                break;
                            case Identifier.LABEL:
                                labelCount++;
                                isSuccess = true;
                                break;
                            case Identifier.FUNCTION:
                                funcCount++;
                                isSuccess = true;

                                if(i.line == i2.line) {
                                    funcRecursao = true;
                                }

                                break;

                        }
                    }
                }
            }

            if(!isSuccess) {
                System.out.println(String.format("Error: Identifier \"%s\" (line %d) not declared",i.name,i.line));
                return false;
            }
            else if(labelCount > 1 || funcCount > 1) {
                System.out.println(String.format("Error: Identifier \"%s\" (line %d) has multiple declarations",i.name,i.line));
                return false;
            }
            else if(funcRecursao) {
                System.out.println(String.format("Error: Function \"%s(X)\" (line %d) is referenced is in declaration",i.name,i.line));
                return false;
            }
        }
        return true;
    }

    
    public static boolean searchInLabels(ArrayList<ArrayList<Token>> code) {

        boolean isSuccess = true;

        for (ArrayList<Token> l : code) {

        	Token t = l.get(0);

            if(TokenComparator.getTokenType(t.Token) == TokenType.IDENTIFIER) {
            	isSuccess = addIdentifier(t, true, Identifier.LABEL);
                l.remove(0);
                l.remove(0);

                String s = l.get(0).Token;

                if(s.equals("DATA") || s.equals("DEF") || s.equals("RETURN") || s.equals("END") || s.equals("REM") || s.equals("NEXT")) {
                	isSuccess = false;
                    System.out.println(String.format("Error: \"%s\" no labels allowed (line %d)", s, t.line));
                }

            }

            if(!isSuccess) {
                return false;
            }

            t = l.get(0);

            if( t.Token.equals("GOSUB")) {
                t = l.get(1);
                isSuccess = addIdentifier(t, false, Identifier.LABEL);
                l.remove(1);
            }
            else if(t.Token.equals("GOTO") || t.Token.equals("IF")) {
                t = l.get(l.size() - 1);

                if(TokenComparator.getTokenType(t.Token) != TokenType.STOP) {
                	isSuccess = addIdentifier(t,false,Identifier.LABEL);
                    l.remove(l.size() - 1);
                }
            }

            if(!isSuccess) {
                return false;
            }
        }
        return true;
    }

    public static boolean searchInFunctions(ArrayList<ArrayList<Token>> code) {

        boolean isSuccess = true;

        for (ArrayList<Token> l : code) {

            Token t = l.get(0);

            if(t.Token.equals("DEF")) {

                t = l.get(2);

                isSuccess = addIdentifier(t,true,Identifier.FUNCTION);
                l.remove(0);
                l.remove(0);
                l.remove(0);
                l.remove(0);
                l.remove(0);
                l.remove(0);

                for (int j = 0; j < l.size(); j ++) {

                    Token tt = l.get(j);

                    if(TokenComparator.getTokenType(t.Token) == TokenType.IDENTIFIER && tt.Token.equals("X")) {
                        l.remove(j);
                        j--;
                    }
                }
            }

            if(!isSuccess) {
                return false;
            }

            for(int j = 0; j < l.size();j++) {

                Token tt = l.get(j);

                if(tt.Token.equals("FN")) {
                    tt = l.get(j+1);
                    isSuccess = addIdentifier(tt,false,Identifier.FUNCTION);
                    l.remove(j+1);
                }
            }

            if(!isSuccess) {
                return false;
            }

        }
        return true;
    }

    public static boolean searchInVariables(ArrayList<ArrayList<Token>> code) {

        boolean isSuccess = true;

        for (ArrayList<Token> l:code) {

            Token t = l.get(0);

            if(t.Token.equals("LET") || t.Token.equals("FOR")) {
                Token tt = l.get(1);
                isSuccess = addIdentifier(tt, true, Identifier.VARIABLE);
                l.remove(0);
                l.remove(0);

                if(t.Token.equals("FOR")) {
                    FOR_NEXT.add(new Identifier(tt,Identifier.VARIABLE));
                }

            }
            if(!isSuccess) {
                return false;
            }

            t = l.get(0);

            if(t.Token.equals("NEXT")) {
                t = l.get(1);

                for (Identifier i:FOR_NEXT) {
                    if(i.name.equals(t.Token)) {
                        if(i.line < t.line) {
                            FOR_NEXT.remove(i);
                            break;
                        }
                    }
                }
            }

            else {
                for (Token tt:l) {
                    if(TokenComparator.getTokenType(tt.Token) == TokenType.IDENTIFIER) {
                    	isSuccess = addIdentifier(tt,false,Identifier.VARIABLE);

                    }
                }
            }

            if(!isSuccess) {
                return false;
            }
        }
        if(FOR_NEXT.size() > 0) {
            Identifier i = FOR_NEXT.get(0);
            System.out.println(String.format("Error: loop in (%d) without (\"NEXT\")",i.line));
            return false;
        }
        return true;
    }
    
    private static boolean addIdentifier(Token t, boolean declaration, int type){

        if(declaration) {
            switch (type) {
                case Identifier.VARIABLE:

                    for (Identifier i : IDENT_DECLARE) {
                        if(i.type == Identifier.VARIABLE && i.name.equals(t.Token)) {
                            if(i.line < t.line) {
                                IDENTIFIERS.add(new Identifier(t, Identifier.VARIABLE));
                                return true;
                            }
                        }
                    }

                    IDENT_DECLARE.add(new Identifier(t, Identifier.VARIABLE));
                    return true;
                default:
                    for (Identifier i : IDENT_DECLARE) {
                        if(i.type == type && i.name.equals(t.Token)) {
                            System.out.println(String.format("Error: \"%s\" (line %d), already declared (%d)", t.Token, t.line, i.line));
                            return false;
                        }
                    }
                    IDENT_DECLARE.add(new Identifier(t, type));
                    return true;
            }
        }
        else {
            IDENTIFIERS.add(new Identifier(t, type));
            return true;
        }
    }


}
