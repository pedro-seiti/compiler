package compiler.syntactic.table;

import compiler.model.Token;

public class Identifier {
	public String name;
    public int line;
    public int type;

    public Identifier(Token token, int type) {

        this.name = token.Token;
        this.line = token.line;
        this.type = type;
    }

    public static final int VARIABLE = 0;
    public static final int LABEL = 1;
    public static final int FUNCTION = 2;

}
