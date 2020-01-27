package compiler.generator.util;

import java.util.ArrayList;

import compiler.model.Token;

public class RedundancyHelper {
	
	public static ArrayList<ArrayList<Token>> addZeros(ArrayList<ArrayList<Token>> code) {

        ArrayList<ArrayList<Token>> finalList = new ArrayList<ArrayList<Token>>();
        int i = 0;

        for (ArrayList<Token> l:code) {

            finalList.add(new ArrayList<Token>());

            for(int j = 0; j < l.size(); j++) {

                Token t1 = l.get(j);

                if(!",=(".contains(t1.Token) && !t1.Token.equals("STEP")) {
                    finalList.get(i).add(t1);
                }
                else {
                    Token t2 = l.get(j+1);

                    if(t2.Token.equals("-")) {
                        Token tZero = new Token();
                        tZero.Token = "0";
                        tZero.line = t1.line;

                        finalList.get(i).add(t1);
                        finalList.get(i).add(tZero);
                    }
                    else {
                        finalList.get(i).add(t1);
                    }
                }
            }
            i++;
        }

        return finalList;
    }

    public static ArrayList<ArrayList<Token>> addStep(ArrayList<ArrayList<Token>> code) {

        ArrayList<ArrayList<Token>> finalList = new ArrayList<ArrayList<Token>>();
        int i = 0;

        for (ArrayList<Token> l:code) {

            finalList.add(new ArrayList<Token>());
            finalList.get(i).addAll(l);
            
            boolean _for = false;
            boolean _step = false;

            for (Token t:l) {

                if(t.Token.equals("FOR")) {
                    _for = true;
                }
                else if(t.Token.equals("STEP")) {
                    _step = true;
                }
            }

            if(_for && !_step) {
                Token t1 = new Token();
                t1.Token = "STEP";
                t1.line = i;

                Token t2 = new Token();
                t2.Token = "1";
                t2.line = i;

                finalList.get(i).add(t1);
                finalList.get(i).add(t2);
            }
            
            i++;
        }

        return finalList;
    }

}
