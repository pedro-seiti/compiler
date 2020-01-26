package compiler.model;

public class Token {

    public String Token;
    public String lastToken;
    public String reclassifiedToken;
    public int line;
    
    public Token() {
    	
    }
    
    public Token(Token t) {
    	this.Token = t.Token;
    	this.line = t.line;
    }
    
    public boolean reclassify(String actualToken) {
		if ("<".equals(lastToken) && ">".equals(actualToken)) {
			reclassifiedToken = "<>";
			Token = reclassifiedToken;
			return true;
		}
		return false;
    }
    
    public boolean reclassify() {
    	if ("GO".equals(lastToken) && "TO".equals(Token)) {
			reclassifiedToken = "GOTO";
			Token = reclassifiedToken;
			return true;
		}
		return false;
    }

}
