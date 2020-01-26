package compiler.lexico;

import java.util.ArrayList;
import java.util.Arrays;

import compiler.model.Token;

public class Tokenizer {
	
	private final String SPECIALS = "\"*()-=+/<>,.:";
	private final String CONTROLS = "\n\r";
	
	private StringBuilder tokenBuilder = new StringBuilder();
	private boolean inString = false;
	
	public Token token = new Token();
	public String specialChar = "";
	public String reclassifiedChar = "";
	
	public boolean isTokenFinished = false;
	public boolean isLineFinished = false;
	
	private boolean keepToken = false;
	
	public boolean handleCharacter(char c, int line) throws Exception {
		
		String s = String.valueOf(c);
		
		isTokenFinished = false;
		
		if (Character.isLetter(c)) {
            tokenBuilder.append(s);

		} else if (Character.isDigit(c)) {
			tokenBuilder.append(s);

		} else if (c == ' ' || c == '\t') {
			if(inString) {
				tokenBuilder.append(s);
            }
            else {
                token.Token = tokenBuilder.toString();
                token.line = line;

                if(!token.Token.equals("")) {
                    tokenBuilder = new StringBuilder();
                    
                    if (token.reclassify()) {
                    	reclassifiedChar = token.reclassifiedToken;
                    }
                    token.lastToken = token.Token;
                    isTokenFinished = true;
                }
                
            }

		} else if (SPECIALS.contains(String.format("%c",c))) {
			if (c == '\"') {
                if(inString) {
                    tokenBuilder.append(s);
                    token.Token = tokenBuilder.toString();
                    token.line = line;
                    tokenBuilder = new StringBuilder();
                    inString = false;
                    
                    isTokenFinished = true;
                }
                else {
                    token.Token = tokenBuilder.toString();
                    token.line = line;
                    tokenBuilder = new StringBuilder();
                    inString = true;
                    tokenBuilder.append(s);
                    
                    isTokenFinished = true;
                }
                if(!token.Token.equals("")) {
                	token.reclassify();
                }
            }
            else if(inString) {
                tokenBuilder.append(s);
            }
            else {

                token.Token = tokenBuilder.toString();
                token.line = line;
                if(!token.Token.equals("")) {
                    tokenBuilder = new StringBuilder();
                }
                isTokenFinished = true;
                
                if (token.reclassify(s)) {
                	reclassifiedChar = token.reclassifiedToken;
                }
                token.lastToken = s;
                specialChar = s;
                
            }

		} else if (CONTROLS.contains(String.format("%c",c))) {
			token.Token = tokenBuilder.toString();
			token.line = line;

            if(!token.Token.equals("")) {
                tokenBuilder = new StringBuilder();
                token.reclassify();
            }
            
            isLineFinished = true;
            
            isTokenFinished = true;
		} else {
			
			if(inString) {
				tokenBuilder.append(s);
			}
			else {
				throw new Exception("Invalid character!");
			}
		}
		
		return isTokenFinished;
	}
}
