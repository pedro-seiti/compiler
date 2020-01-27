package compiler.generator;

import java.util.ArrayList;
import java.util.List;

public abstract class LinguagemMaquina {

    public static List<AssemblyLine> Montar(List<AssemblyLine> codigo) {

        for (AssemblyLine l:codigo) {

            l.Instrucao = MnemonicoParaHex(l.Instrucao);

            if(l.Operador.startsWith("\\")) {
                String s = l.Operador.substring(1);

                s = String.format("%03X",Integer.parseInt(s));
                l.Operador = s;
            }
            else {
                for (AssemblyLine ll:codigo) {

                    if(ll.Label.equals(l.Operador)) {
                        l.Operador = String.format("%03X",ll.Linha);
                        break;
                    }
                }
            }
        }

        return codigo;
    }


    private static String MnemonicoParaHex(String mne) {

        if (mne == "JP") {
                return "0";
        } else if (mne == "JZ") {
                return "1";
        } else if (mne == "JN") {
                return "2";
        } else if (mne == "LV") {
                return "3";
        } else if (mne == "ADD") {
                return "4";
        } else if (mne == "SUB") {
                return "5";
        } else if (mne == "MUL") {
                return "6";
        } else if (mne == "DIV") {
                return "7";
        } else if (mne == "LD") {
                return "8";
        } else if (mne == "MM") {
                return "9";
        } else if (mne == "SC") {
                return "A";
        } else if (mne == "RS") {
                return "B";
        } else if (mne == "HM") {
                return "C";
        } else if (mne == "GD") {
                return "D";
        } else if (mne == "PD") {
                return "E";
        } else if (mne == "OS") {
                return "F";
        } else {
                return "0";
        }
    }
}
