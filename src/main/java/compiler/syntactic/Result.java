package compiler.syntactic;

import java.util.ArrayList;
import java.util.List;

import compiler.model.Token;

public class Result {

	public ArrayList<Token> remainingLine;
	
    public boolean isSuccess;

    public Result(ArrayList<Token> remainingLine, boolean isSuccess) {
        this.remainingLine = remainingLine;
        this.isSuccess = isSuccess;
    }
}
