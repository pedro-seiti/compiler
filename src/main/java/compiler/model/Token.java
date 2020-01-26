package compiler.model;

public class Token {

    public String Token;
    public String lastToken;
    public String reclassifiedToken;
    public int line;
    
    public boolean reclassify(String actualToken) {
		if ("<".equals(lastToken) && ">".equals(actualToken)) {
			reclassifiedToken = "<>";
			return true;
		}
		return false;
    }
    
    public boolean reclassify() {
    	if ("GO".equals(lastToken) && "TO".equals(Token)) {
			reclassifiedToken = "GOTO";
			return true;
		}
		return false;
    }

}
