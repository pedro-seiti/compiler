package compiler.syntactic;

import java.util.ArrayList;
import java.util.List;

import compiler.model.Token;

public class Result {

	public ArrayList<String> remainingLine;
	
    public boolean isSuccess;

    public Result(ArrayList<String> remainingLine, boolean isSuccess) {
        this.remainingLine = remainingLine;
        this.isSuccess = isSuccess;
    }
}
