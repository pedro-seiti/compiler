package compiler.generator;

import java.util.ArrayList;
import java.util.List;

import compiler.generator.intermediate.IntermediateCodeGenerator;
import compiler.generator.intermediate.IntermediateCodeLine;
import compiler.generator.util.Simplifier;
import compiler.model.Token;

public class CodeGenerator {
	
	public static List<AssemblyLine> codigoAssembly = new ArrayList<AssemblyLine>();

	public static void translateToAssembly(ArrayList<ArrayList<Token>> code) {
		ArrayList<ArrayList<Token>> ll = new ArrayList<ArrayList<Token>>();

        for (ArrayList<Token> lt: code) {
            ll.add(new ArrayList<Token>());
            ll.get(ll.size()-1).addAll(lt);
        }

        ll = Simplifier.simplifyCode(ll);
        ll = Simplifier.substituteFunctions(ll);

        for (ArrayList<Token> lt:ll) {
        	IntermediateCodeGenerator.generateLine(lt);
        }

        codigoAssembly.add(new AssemblyLine("!","JP","\\004"));
        codigoAssembly.add(new AssemblyLine("STOP","HM","\\004"));

        for (IntermediateCodeLine l : IntermediateCodeGenerator.NovasLinhas) {
            codigoAssembly.addAll(TraduzirAssembly.TraduzirLinha(l));
        }
        codigoAssembly.add(new AssemblyLine("!","JP","STOP"));
        codigoAssembly = TraduzirAssembly.IsolarSubrotinas(codigoAssembly);
        codigoAssembly.addAll(TraduzirAssembly.ConfigurarMemoria(codigoAssembly));


        for(int i = 0; i < codigoAssembly.size();i ++) {
            AssemblyLine l = codigoAssembly.get(i);
            l.Linha = 2*i;
        }
	}
    public static void ParaMaquina(){

        LinguagemMaquina.Montar(codigoAssembly);
    }
}
