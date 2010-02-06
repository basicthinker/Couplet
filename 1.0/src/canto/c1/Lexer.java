/**
 * 
 */
package canto.c1;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import canto.Token;
import canto.c1.token.*;

/**
 * @author basicthinker
 *
 */
public class Lexer implements canto.Lexer {

	private LineNumberReader inBuf;
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
		inBuf = new LineNumberReader(inStr);
		inBuf.ready();
	}

	/* (non-Javadoc)
	 * @see canto.Lexer#scan()
	 */
	@Override
	public List<Token> scan() throws IOException {
		// TODO Auto-generated method stub
		int bufChar = inBuf.read();
		while (bufChar != -1) {
			
			int lineNumber = inBuf.getLineNumber();
			String lexeme = null;
			
			if (bufChar == '_' || 'a' <= bufChar && bufChar <= 'z' 
					|| 'A' <= bufChar && bufChar <= 'Z') {
				
				bufChar = recognizeWord((char)bufChar, lexeme);
				
				if (reserved.contains(lexeme)) {
					tokenList.add(new Keyword(lineNumber, lexeme));
				} else {
					tokenList.add(new Identifier(lineNumber, lexeme));
				}
				
			} else if ('0' <= bufChar && bufChar <= '9') {
				
				bufChar = recognizeInteger((char)bufChar, lexeme);
				tokenList.add(new Constant(lineNumber, lexeme));
				
			} else if (bufChar == ' ' || bufChar == '\t' || bufChar == '\n') {
				
				bufChar = skipWhiteSpaces();

			} else if (bufChar == '=') {
				
				int nextChar = inBuf.read();
				if (nextChar == '=') {
					tokenList.add(new Operator(lineNumber, "=="));
					bufChar = -1;
				} else {
					tokenList.add(new Punctuation(lineNumber, "="));
					bufChar = nextChar;
				}
				
			} else if (bufChar == ';' || bufChar == '{' || bufChar == '}' 
				|| bufChar == '(' || bufChar == ')') {
				
				bufChar = recognizePunctuation((char)bufChar, lexeme);
				tokenList.add(new Punctuation(lineNumber, lexeme));
				
			} else {
				
				bufChar = recognizeOperator((char)bufChar, lexeme);
				tokenList.add(new Operator(lineNumber, lexeme));
				
			}
			
			if (bufChar == -1) {
				bufChar = inBuf.read();
			}
		} // while
		return null;
	}
	
	private int recognizePunctuation(char bufChar, String lexeme) {
		lexeme = String.valueOf(bufChar);
		return -1;
	}

	private int recognizeOperator(char bufChar, String lexeme) throws IOException {
		switch (bufChar) {
			case '+': 
			case '-':
			case '*':
				lexeme = String.valueOf(bufChar);
				break;
			case '>':
			case '<':
			case '!':
				int nextChar = inBuf.read();
				if (nextChar == '=') {
					lexeme = new StringBuffer().append(bufChar).append(nextChar).toString();
				} else {
					lexeme = String.valueOf(bufChar);
					return nextChar;
				}
				break;
			case '=':
			case '|':
			case '&':
				nextChar = inBuf.read();
				if (nextChar == bufChar) {
					lexeme = new StringBuffer().append(bufChar).append(nextChar).toString();
				} else {
					lexeme = String.valueOf(bufChar);
					return nextChar;
				}
				break;
			default:
				
		}
		return -1;
	}

	private int recognizeInteger(char prefix, String lexeme) throws IOException {
		StringBuffer lexBuf = new StringBuffer(prefix);
		int nextChar = inBuf.read();
		while (nextChar != -1 && '0' <= nextChar && nextChar <= '9') {
			lexBuf.append(nextChar);
			nextChar = inBuf.read();
		}
		lexeme = lexBuf.toString();
		return nextChar;
	}

	private int skipWhiteSpaces() throws IOException {
		int nextChar = inBuf.read();
		while (nextChar != -1) {
			if (nextChar == ' ' || nextChar == '\t' || nextChar == '\n') {
				nextChar = inBuf.read();
			} else break;		
		}
		return nextChar;
	}

	private int recognizeWord(char prefix, String lexeme) throws IOException {
		StringBuffer lexBuf = new StringBuffer(prefix);
		int nextChar = inBuf.read();
		while (nextChar != -1) {
			if ('a' <= nextChar && nextChar <= 'z'
				|| 'A' <= nextChar && nextChar <= 'Z') {
				lexBuf.append(nextChar);
				nextChar = inBuf.read();
			} else break;
		}
		lexeme = lexBuf.toString();
		return nextChar;
	}

	private void reserveWords() {
		reserved.add("int");
		reserved.add("if");
		reserved.add("else");
		reserved.add("while");
		reserved.add("input");
		reserved.add("output");
	}

	@Override
	public List<Token> getTokenList() {
		return tokenList;
	}

}
