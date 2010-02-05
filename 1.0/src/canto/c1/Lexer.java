/**
 * 
 */
package canto.c1;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import canto.Token;

/**
 * @author basicthinker
 *
 */
public class Lexer implements canto.Lexer {

	private LinenumberedBuffer inBuf;
	private HashSet<String> reserved;
	private List<Token> tokenList;
	
	public Lexer() {
		reserved = new HashSet<String>();
		tokenList = new LinkedList<Token>();
		reserveWords();
	}
	
	/* (non-Javadoc)
	 * @see canto.Lexer#open(java.io.InputStreamReader)
	 */
	@Override
	public void open(InputStreamReader inStr) throws IOException {
		inBuf = new LinenumberedBuffer(inStr);
	}

	/* (non-Javadoc)
	 * @see canto.Lexer#scan()
	 */
	@Override
	public List<Token> scan() {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	private void reserveWords() {
		reserved.add("int");
		reserved.add("if");
		reserved.add("else");
		reserved.add("while");
		reserved.add("input");
		reserved.add("output");
	}

}
