package compiler.syntactic.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TokenComparator {
	
	private static ArrayList<String> OPERATORS = new ArrayList<String>(
		Arrays.asList("+", "-", "*", "/")
	);
			
	private static List<String> COMPARATORS = new ArrayList<String>(
		Arrays.asList("<>", "=", ">", "<", "<=", ">=")
	);
	
	private static List<String> SIGNS = new ArrayList<String>(
			Arrays.asList("(", ")", ",", ":")
		);

	public static boolean areSameTokenTypes(String token, TokenType transitionTokenType) {

		if ("STOP".equals(token) && TokenType.STOP.equals(transitionTokenType)) {
			return true;
		}
		
		else if ("END".equals(token) && TokenType.END.equals(transitionTokenType)) {
			return true;
		}
		
        else if(OPERATORS.contains(token) && TokenType.OPERATOR.equals(transitionTokenType)) {
        	return true; 
        }

        else if(COMPARATORS.contains(token) && TokenType.COMPARATOR.equals(transitionTokenType)) {
        	return true;
        }

        else if(token.matches("[A-Z]*\\d?") && TokenType.IDENTIFIER.equals(transitionTokenType)) {
        	return true;
        }

        else if(token.matches("\\d+") && TokenType.DIGIT.equals(transitionTokenType)) {
        	return true;
        }

        else if(token.startsWith("\"") && token.endsWith("\"") && TokenType.STRING.equals(transitionTokenType)) {
        	return true;
        }
        
		return false;
	}
	
	public static TokenType getTokenType(String token) {
		if ("STOP".equals(token)) {
			return TokenType.STOP;
		}
		
		else if ("END".equals(token)) {
			return TokenType.END;
		}
		
        else if(OPERATORS.contains(token)) {
        	return TokenType.OPERATOR; 
        }

        else if(COMPARATORS.contains(token)) {
        	return TokenType.COMPARATOR;
        }

        else if(token.matches("[A-Z]\\d?")) {
        	return TokenType.IDENTIFIER;
        }

        else if(token.matches("\\d+")) {
        	return TokenType.DIGIT;
        }

        else if(token.startsWith("\"") && token.endsWith("\"")) {
        	return TokenType.STRING;
        }
		
        else if(SIGNS.contains(token)) {
        	return TokenType.SIGN;
        }
		
        else return TokenType.INVALID;
	}
}
