package compiler.generator;

public class AssemblyLine {

    public String Label;
    public String Instrucao;
    public String Operador;
    public int Linha;

    public AssemblyLine(String l, String i, String o) {
        Label = l;
        Instrucao = i;
        Operador = o;
    }

}
