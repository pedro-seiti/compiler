package compiler.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import compiler.lexico.Tokenizer;
import compiler.model.Token;
import compiler.syntactic.Syntactic;

public class Startup {

	public static void main(String[] args) throws Exception {
		Charset encoding = Charset.defaultCharset();
		//File file = new File(args[0]);
		
		try {
			InputStream in = Startup.class.getResourceAsStream("/test/testeErroSintatico.txt");
			Reader reader = new InputStreamReader(in, encoding);
			Reader buffer = new BufferedReader(reader);
			handleInputStream(buffer);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private static void handleInputStream(Reader reader) throws Exception {
//		int r;
//		while ((r = reader.read()) != -1) {
//			char ch = (char) r;
//			System.out.println(ch);
//		}
		
		ArrayList<ArrayList<String>> lines = new ArrayList<ArrayList<String>>();
		ArrayList<String> tokens = new ArrayList<String>();
		
		Tokenizer tokenizer = new Tokenizer();
		
		int line = 1;
		int r = reader.read();
		while (r != -1) {
			
			char ch = (char) r;
			
			boolean tokenFinished = tokenizer.handleCharacter(ch, line);
			
			if (!tokenFinished) {
				r = reader.read();
				continue;
			} else {
				if (!tokenizer.reclassifiedChar.isEmpty()) {
					tokens.remove(tokens.size() - 1);
					tokens.add(tokenizer.reclassifiedChar);
					tokenizer.reclassifiedChar = "";
					tokenizer.specialChar = "";
				} else {
					if (!tokenizer.token.Token.toString().equals("")) {
						tokens.add(tokenizer.token.Token.toString());
					}
					
					if (!tokenizer.specialChar.isEmpty()) {
						tokens.add(tokenizer.specialChar);
						tokenizer.specialChar = "";
					}
				}
			}
			
			if (tokenizer.isLineFinished) {
				lines.add(tokens);
				tokens = new ArrayList<String>();
				tokenizer.isLineFinished = false;
				line += 1;
			}
			
			r = reader.read();
		}
		
		if (tokenizer.handleCharacter((char) 32)) {
			if (!tokenizer.token.Token.toString().equals("")) {
				tokens.add(tokenizer.token.Token.toString());
				lines.add(tokens);
			}
		}
		
		boolean isSyntacticSuccess = Syntactic.analyse(lines);
		
		System.out.print("done");
	}

}
