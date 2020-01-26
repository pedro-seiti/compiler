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
			InputStream in = Startup.class.getResourceAsStream("/test/testeSintatico.txt");
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
		
		ArrayList<ArrayList<Token>> lines = new ArrayList<ArrayList<Token>>();
		ArrayList<Token> tokens = new ArrayList<Token>();
		
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
				if (tokenizer.reclassifiedChar != null && !tokenizer.reclassifiedChar.Token.isEmpty()) {
					tokens.remove(tokens.size() - 1);
					Token t = new Token(tokenizer.reclassifiedChar);
					tokens.add(t);
					tokenizer.reclassifiedChar = null;
					tokenizer.specialChar = null;
				} else {
					if (!tokenizer.token.Token.toString().equals("")) {
						Token t = new Token(tokenizer.token);
						tokens.add(t);
					}
					
					if (tokenizer.specialChar != null && !tokenizer.specialChar.Token.isEmpty()) {
						Token t = new Token(tokenizer.specialChar);
						tokens.add(t);
						tokenizer.specialChar = null;
					}
				}
			}
			
			if (tokenizer.isLineFinished) {
				lines.add(tokens);
				for (Token t : tokens) {
					System.out.println(t.Token);
				}
				tokens = new ArrayList<Token>();
				tokenizer.isLineFinished = false;
				line += 1;
			}
			
			r = reader.read();
		}
		
		if (tokenizer.handleCharacter((char) 32, line)) {
			if (!tokenizer.token.Token.toString().equals("")) {
				Token t = new Token(tokenizer.token);
				tokens.add(t);
				lines.add(tokens);
			}
		}
		
		boolean isSyntacticSuccess = Syntactic.analyse(lines);
		
		System.out.print("done");
	}

}
