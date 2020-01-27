package compiler.generator;

import compiler.generator.intermediate.IntermediateCodeLine;
import compiler.model.Token;
import compiler.syntactic.util.TokenComparator;
import compiler.syntactic.util.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class TraduzirAssembly {

    private static int branch = 0;
    private static Stack<String> returnLoop = new Stack<String>();
    private static List<String> goSubLabels = new ArrayList<String>();

//    private static int DataLen = 0;
//    private static int currentData = 0;

    public static List<AssemblyLine> TraduzirLinha(IntermediateCodeLine l) {

        List<AssemblyLine> ll = new ArrayList<AssemblyLine>();

        switch (l.Tipo) {
            case IntermediateCodeLine.ASSIGN:
                ll = TransformarExpressaoRPN(l.Expressoes.get(0),l.Variaveis.get(0).Token);
                ll.get(0).Label = l.Label;
                return ll;
//            case IntermediateCodeLine.DATA:
//                DataLen = l.Variaveis.size();
//                ll.add(new AssemblyLine("!","JP","\\002"));
//                for(int i = 0; i < l.Variaveis.size(); i++) {
//                    ll.add(new AssemblyLine(String.format("DATA_%d",i),"JP",l.Variaveis.get(i).Token));
//                }
//                return ll;
            case IntermediateCodeLine.FOR:
                ll.addAll(TransformarExpressaoRPN(l.Expressoes.get(0),l.Variaveis.get(0).Token));
                ll.get(0).Label = l.Label;

                String loopLabel = String.format("L_%s_%d",l.Variaveis.get(0).Token, branch);
                branch++;
                returnLoop.push(loopLabel);

                ll.add(new AssemblyLine(loopLabel,"LV","\\0"));
                return ll;
            case IntermediateCodeLine.GOSUB:
                String subLabel = String.format("S_%s_%d",l.Variaveis.get(0).Token, branch);
                branch++;
                goSubLabels.add(l.Variaveis.get(0).Token);

                ll.add(new AssemblyLine(l.Label,"LV",subLabel));
                ll.add(new AssemblyLine(l.Label,"MM","SUB_RET"));
                ll.add(new AssemblyLine(l.Label,"JP",l.Variaveis.get(0).Token));
                ll.add(new AssemblyLine(subLabel,"LV","\\000"));

                return ll;
            case IntermediateCodeLine.GOTO:
                ll.add(new AssemblyLine(l.Label,"JP",l.Variaveis.get(0).Token));
                return ll;
            case IntermediateCodeLine.IF:
                Token t = l.Variaveis.get(1);

                if (">".equals(t.Token)) {
                        ll.add(new AssemblyLine("!","LD",l.Variaveis.get(0).Token));
                        ll.add(new AssemblyLine("!","MM","IF_VAR2"));
                        ll.addAll(TransformarExpressaoRPN(l.Expressoes.get(0),"IF_VAR1"));


                        ll.add(new AssemblyLine("!","SUB","IF_VAR2"));
                        ll.add(new AssemblyLine("!","JN",l.Variaveis.get(2).Token));
                } else if ("<".equals(t.Token)) {
                        ll.addAll(TransformarExpressaoRPN(l.Expressoes.get(0),"IF_VAR2"));
                        ll.add(new AssemblyLine("!","LD",l.Variaveis.get(0).Token));
                        ll.add(new AssemblyLine("!","MM","IF_VAR1"));

                        ll.add(new AssemblyLine("!","SUB","IF_VAR2"));
                        ll.add(new AssemblyLine("!","SUB","IF_VAR2"));
                        ll.add(new AssemblyLine("!","JN",l.Variaveis.get(2).Token));
                } else if (">=".equals(t.Token)) {
                        ll.add(new AssemblyLine("!","LD",l.Variaveis.get(0).Token));
                        ll.add(new AssemblyLine("!","MM","IF_VAR2"));
                        ll.addAll(TransformarExpressaoRPN(l.Expressoes.get(0),"IF_VAR1"));

                        ll.add(new AssemblyLine("!","SUB","IF_VAR2"));
                        ll.add(new AssemblyLine("!","SUB","IF_VAR2"));
                        ll.add(new AssemblyLine("!","JN",l.Variaveis.get(2).Token));
                        ll.add(new AssemblyLine("!","JP",l.Variaveis.get(2).Token));
                } else if ("<=".equals(t.Token)) {
                        ll.addAll(TransformarExpressaoRPN(l.Expressoes.get(0),"IF_VAR2"));
                        ll.add(new AssemblyLine("!","LD",l.Variaveis.get(0).Token));
                        ll.add(new AssemblyLine("!","MM","IF_VAR1"));

                        ll.add(new AssemblyLine("!","SUB","IF_VAR2"));
                        ll.add(new AssemblyLine("!","SUB","IF_VAR2"));
                        ll.add(new AssemblyLine("!","JN",l.Variaveis.get(2).Token));
                        ll.add(new AssemblyLine("!","JP",l.Variaveis.get(2).Token));
                } else if ("=".equals(t.Token)) {
                        ll.addAll(TransformarExpressaoRPN(l.Expressoes.get(0),"IF_VAR2"));
                        ll.add(new AssemblyLine("!","LD",l.Variaveis.get(0).Token));
                        ll.add(new AssemblyLine("!","MM","IF_VAR1"));

                        ll.add(new AssemblyLine("!","SUB","IF_VAR2"));
                        ll.add(new AssemblyLine("!","SUB","IF_VAR2"));
                        ll.add(new AssemblyLine("!","JZ",l.Variaveis.get(2).Token));
                } else if ("<>".equals(t.Token)) {
                        ll.addAll(TransformarExpressaoRPN(l.Expressoes.get(0),"IF_VAR2"));
                        ll.add(new AssemblyLine("!","LD",l.Variaveis.get(0).Token));
                        ll.add(new AssemblyLine("!","MM","IF_VAR1"));

                        ll.add(new AssemblyLine("!","SUB","IF_VAR2"));
                        ll.add(new AssemblyLine("!","SUB","IF_VAR2"));

                        String lb = String.format("NOT_%s",l.Variaveis.get(2).Token);
                        ll.add(new AssemblyLine("!","JZ",lb));
                        ll.add(new AssemblyLine("!","JP",l.Variaveis.get(2).Token));
                        ll.add(new AssemblyLine(lb,"LV","\\000"));
                }
                ll.get(0).Label = l.Label;
                return  ll;
            case IntermediateCodeLine.NEXT:

                if(l.Variaveis.get(1).Token.equals("-")) {
                    ll.addAll(TransformarExpressaoRPN(l.Expressoes.get(1),"TO_VAR"));
                    ll.add(new AssemblyLine("!","LD",l.Variaveis.get(0).Token));
                    ll.add(new AssemblyLine("!","SUB","TO_VAR"));
                }
                else {
                    ll.addAll(TransformarExpressaoRPN(l.Expressoes.get(1),"TO_VAR"));
                    ll.add(new AssemblyLine("!","SUB",l.Variaveis.get(0).Token));
                }
                String breakLoop = String.format("B%s",returnLoop.peek());
                ll.add(new AssemblyLine("!","JN",breakLoop));
                ll.add(new AssemblyLine("!","JZ",breakLoop));

                ll.addAll(TransformarExpressaoRPN(l.Expressoes.get(0),"STP_VAR"));
                ll.add(new AssemblyLine("!","LD",l.Variaveis.get(0).Token));
                ll.add(new AssemblyLine("!","ADD","STP_VAR"));
                ll.add(new AssemblyLine("!","MM",l.Variaveis.get(0).Token));

                ll.add(new AssemblyLine("!","JP",returnLoop.pop()));
                ll.add(new AssemblyLine(breakLoop,"LV","\\000"));

                return ll;
            case IntermediateCodeLine.PRINT:
                for (Token tt:l.Variaveis) {

                    if(TokenType.STRING.equals(TokenComparator.getTokenType(tt.Token))) {
                        ll.add(new AssemblyLine("!","OS","\\001"));
                        for (int i = 0; i < tt.Token.length(); i++) {
                            char c = tt.Token.charAt(i);
                            String ascii = String.format("%03d",(int)c);
                            ll.add(new AssemblyLine("!","PD",String.format("\\%s",ascii)));
                        }
                        ll.add(new AssemblyLine("!","OS","\\000"));
                    }
                    else {
                        ll.add(new AssemblyLine("!","PD",tt.Token));
                    }
                }
                ll.get(0).Label = l.Label;
                return ll;
            case IntermediateCodeLine.RETURN:
                ll.add(new AssemblyLine("!","JP","SUB_RET"));
                return ll;
//            case IntermediateCodeLine.READ:
//                for (Token t1:l.Variaveis) {
//                    ll.add(new AssemblyLine("!","LD",String.format("DATA_%d",currentData)));
//                    ll.add(new AssemblyLine("!","MM",t1.Token));
//                    currentData++;
//                    if(currentData > DataLen) {
//                        currentData = 0;
//                    }
//                }
//                ll.get(0).Label = l.Label;
//                return ll;
        }
        return ll;
    }

    public static List<AssemblyLine> IsolarSubrotinas(List<AssemblyLine> linhas) {

        for (int i = 0; i < linhas.size(); i++) {

            AssemblyLine l = linhas.get(i);

            if(goSubLabels.contains(l.Label)) {
                linhas.add(i,new AssemblyLine("!","JP","STOP"));
                i++;
            }
        }

        return linhas;
    }

    public static List<AssemblyLine> ConfigurarMemoria(List<AssemblyLine> linhas) {

        List<String> labelsRef = new ArrayList<String>();

        for (AssemblyLine l:linhas) {

            if(!labelsRef.contains(l.Operador) && !l.Operador.startsWith("\\")) {
                labelsRef.add(l.Operador);
            }
        }

        for (AssemblyLine l:linhas) {
            labelsRef.remove(l.Label);
        }

        List<AssemblyLine> memoria = new ArrayList<AssemblyLine>();

        for (String s:labelsRef) {
            memoria.add(new AssemblyLine(s,"JP","\\000"));
        }
        return memoria;

    }
    
    public static List<AssemblyLine> TransformarExpressaoRPN(List<Token> exp_rpn, String destino) {

        int stack = 0;
        List<AssemblyLine> gerado = new ArrayList<AssemblyLine>();
        List<Token> tl = new ArrayList<Token>(exp_rpn);

        if(exp_rpn.size() == 1) {
            Token t = exp_rpn.get(0);

            if(TokenType.IDENTIFIER.equals(TokenComparator.getTokenType(t.Token))) {
                gerado.add(new AssemblyLine("!","LD",t.Token));
                gerado.add(new AssemblyLine("!","MM",destino));
            }
            else {
                gerado.add(new AssemblyLine("!","LV",String.format("\\%s",t.Token)));
                gerado.add(new AssemblyLine("!","MM",destino));
            }
            return gerado;
        }

        for(int i = 0; i < tl.size()-1; i++) {

            Token t = tl.get(i);
            Token t2 = tl.get(i+1);

            if(TokenType.IDENTIFIER.equals(TokenComparator.getTokenType(t.Token))) {

                if(TokenType.IDENTIFIER.equals(TokenComparator.getTokenType(t2.Token)) || TokenType.DIGIT.equals(TokenComparator.getTokenType(t2.Token))) {

                    gerado.add(new AssemblyLine("!","LD",t.Token));
                    gerado.add(new AssemblyLine("!","MM",String.format("REG%d",stack)));

                    stack++;
                }
                else if(TokenType.OPERATOR.equals(TokenComparator.getTokenType(t2.Token))){

                    stack--;

                    if (t2.Token == "+") {
                            gerado.add(new AssemblyLine("!","ADD",String.format("%s",t.Token)));
                            gerado.add(new AssemblyLine("!","MM",String.format("REG%d",stack)));
                    } else if (t2.Token == "-") {
                            gerado.add(new AssemblyLine("!","SUB",String.format("%s",t.Token)));
                            gerado.add(new AssemblyLine("!","MM",String.format("REG%d",stack)));
                    } else if (t2.Token == "*") {
                            gerado.add(new AssemblyLine("!","MUL",String.format("%s",t.Token)));
                            gerado.add(new AssemblyLine("!","MM",String.format("REG%d",stack)));
                    } else if (t2.Token == "/") {
                            gerado.add(new AssemblyLine("!","DIV",String.format("%s",t.Token)));
                            gerado.add(new AssemblyLine("!","MM",String.format("REG%d",stack)));
                    }
                    stack++;
                }

            }
            else if(TokenType.DIGIT.equals(TokenComparator.getTokenType(t.Token))) {

                if(TokenType.IDENTIFIER.equals(TokenComparator.getTokenType(t2.Token)) 
                		|| TokenType.DIGIT.equals(TokenComparator.getTokenType(t2.Token))) {

                    gerado.add(new AssemblyLine("!","LV",String.format("\\%s",t.Token)));
                    gerado.add(new AssemblyLine("!","MM",String.format("REG%d",stack)));

                    stack++;
                }
                else if(TokenType.OPERATOR.equals(TokenComparator.getTokenType(t2.Token))){

                    if (t2.Token == "+") {
                            gerado.add(new AssemblyLine("!","LV",String.format("\\%s",t.Token)));
                            gerado.add(new AssemblyLine("!","MM",String.format("REG%d",stack)));
                            stack--;
                            gerado.add(new AssemblyLine("!","LD",String.format("REG%d",stack)));
                            gerado.add(new AssemblyLine("!","ADD",String.format("REG%d",stack+1)));
                            gerado.add(new AssemblyLine("!","MM",String.format("REG%d",stack)));
                    } else if (t2.Token == "-") {
                            gerado.add(new AssemblyLine("!","LV",String.format("\\%s",t.Token)));
                            gerado.add(new AssemblyLine("!","MM",String.format("REG%d",stack)));
                            stack--;
                            gerado.add(new AssemblyLine("!","LD",String.format("REG%d",stack)));
                            gerado.add(new AssemblyLine("!","SUB",String.format("REG%d",stack+1)));
                            gerado.add(new AssemblyLine("!","MM",String.format("REG%d",stack)));
                    } else if (t2.Token == "*") {
                            gerado.add(new AssemblyLine("!","LV",String.format("\\%s",t.Token)));
                            gerado.add(new AssemblyLine("!","MM",String.format("REG%d",stack)));
                            stack--;
                            gerado.add(new AssemblyLine("!","LD",String.format("REG%d",stack)));
                            gerado.add(new AssemblyLine("!","MUL",String.format("REG%d",stack+1)));
                            gerado.add(new AssemblyLine("!","MM",String.format("REG%d",stack)));
                    } else if (t2.Token == "/") {
                            gerado.add(new AssemblyLine("!","LV",String.format("\\%s",t.Token)));
                            gerado.add(new AssemblyLine("!","MM",String.format("REG%d",stack)));
                            stack--;
                            gerado.add(new AssemblyLine("!","LD",String.format("REG%d",stack)));
                            gerado.add(new AssemblyLine("!","DIV",String.format("REG%d",stack+1)));
                            gerado.add(new AssemblyLine("!","MM",String.format("REG%d",stack)));
                    }
                    stack++;
                }

            }

            else if(TokenType.OPERATOR.equals(TokenComparator.getTokenType(t.Token))) {
                if(TokenType.OPERATOR.equals(TokenComparator.getTokenType(t2.Token))) {

                    stack--;

                    int x = stack;
                    gerado.add(new AssemblyLine("!","LD",String.format("REG%d",stack-1)));

                    if (t2.Token == "+") {
                            gerado.add(new AssemblyLine("!","ADD",String.format("REG%d",stack)));
                            gerado.add(new AssemblyLine("!","MM",String.format("REG%d",stack-1)));
                    } else if (t2.Token == "-") {
                            gerado.add(new AssemblyLine("!","SUB",String.format("REG%d",stack)));
                            gerado.add(new AssemblyLine("!","MM",String.format("REG%d",stack-1)));
                    } else if (t2.Token == "*") {
                            gerado.add(new AssemblyLine("!","MUL",String.format("REG%d",stack)));
                            gerado.add(new AssemblyLine("!","MM",String.format("REG%d",stack-1)));
                    } else if (t2.Token == "/")
                            gerado.add(new AssemblyLine("!","DIV",String.format("REG%d",stack)));
                            gerado.add(new AssemblyLine("!","MM",String.format("REG%d",stack-1)));
                            break;
                    }
                }
            }

        gerado.add(new AssemblyLine("!","MM",destino));
        return gerado;
    }

}