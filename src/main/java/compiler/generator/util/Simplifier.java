package compiler.generator.util;

import java.util.ArrayList;

import compiler.model.Token;
import compiler.syntactic.SyntacticTable;
import compiler.syntactic.table.Identifier;
import compiler.syntactic.util.TokenComparator;
import compiler.syntactic.util.TokenType;

public class Simplifier {
	public static ArrayList<ArrayList<Token>> simplifyCode(ArrayList<ArrayList<Token>> code) {

        ArrayList<ArrayList<Token>> ll = new ArrayList<ArrayList<Token>>();

        for (ArrayList<Token> l:code) {
            ll.add(new ArrayList<Token>());
            ll.get(ll.size() - 1).addAll(l);
        }

        int changes = 0;
        do {

            for (Identifier i: SyntacticTable.IDENT_DECLARE) {

                if(i.type == Identifier.VARIABLE || i.type == Identifier.FUNCTION) {

                    int refs = 0;

                    for (Identifier i2:SyntacticTable.IDENTIFIERS) {
                        if(i.type == i2.type && i.name.equals(i2.name)) {
                            refs++;
                        }
                    }
                    changes = 0;
                    if(refs == 0) {
                        changes = 1;
                        ll.set(i.line, new ArrayList<Token>());
                        SyntacticTable.IDENT_DECLARE.remove(i);
                        break;
                    }
                }

            }
        } while (changes != 0);


        for (ArrayList<Token> l:ll) {
            for (Token t:l) {
                if(t.Token.equals("REM")) {
                    ll.set(t.line,new ArrayList<Token>());
                    break;
                }
            }
        }

        return ll;
    }
	
	
	public static ArrayList<ArrayList<Token>> substituteFunctions(ArrayList<ArrayList<Token>> codigo) {

        ArrayList<ArrayList<Token>> ll = new ArrayList<ArrayList<Token>>();

        for (ArrayList<Token> l:codigo) {
            ll.add(new ArrayList<Token>());
            ll.get(ll.size() - 1).addAll(l);
        }

        for (Identifier i: SyntacticTable.IDENT_DECLARE) {

            if(i.type == Identifier.FUNCTION) {

                ArrayList<Token> l = new ArrayList<Token>(ll.get(i.line));
                l.remove(0);
                l.remove(0);
                l.remove(0);
                l.remove(0);
                l.remove(0);
                l.remove(0);
                l.remove(0);

                for (int z = 0; z < ll.size(); z++) {
                    ArrayList<Token> o = ll.get(z);
                    int pos = -1;

                    for(int j = 0; j < o.size(); j++) {
                        Token t = o.get(j);

                        if(t.Token.equals("FN")) {
                            if(!o.get(j-1).Token.equals("DEF")) {
                                if(o.get(j+1).Token.equals(i.name)) {
                                    pos = j;
                                    break;
                                }
                            }
                        }
                    }
                    if(pos != -1) {
                        z--;

                        o.remove(pos);
                        o.remove(pos);
                        o.remove(pos);

                        ArrayList<Token> arg = new ArrayList<Token>();
                        int c = 1;
                        do {
                            Token tt = o.get(pos);
                            if(tt.Token.equals("(")) {
                                c++;
                            }
                            else if(tt.Token.equals(")")) {
                                c--;
                            }
                            o.remove(pos);
                            if(c != 0) {
                                arg.add(tt);
                            }

                        }while (c != 0);

                        ArrayList<Token> exp_mod = new ArrayList<Token>(l);

                        for(int k = 0; k < exp_mod.size(); k++) {
                            Token t = exp_mod.get(k);

                            if(t.Token.equals("X") && TokenType.IDENTIFIER.equals(TokenComparator.getTokenType(t.Token))) {
                                exp_mod.remove(k);
                                exp_mod.addAll(k,arg);
                                k = k + arg.size() - 1;
                            }
                        }

                        Token par1 = new Token();
                        par1.Token = "(";

                        Token par2 = new Token();
                        par2.Token = ")";

                        exp_mod.add(0,par1);
                        exp_mod.add(exp_mod.size(),par2);

                        o.addAll(pos,exp_mod);
                    }
                }
                ll.set(i.line,new ArrayList<Token>());
            }
        }
        return ll;
    }

}
