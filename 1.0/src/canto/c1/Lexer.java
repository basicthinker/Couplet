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
	private static HashSet<String> reserved;
	private List<Token> tokenList;
	
	static {
		reserved = new HashSet<String>();
		reserved.add("if");
		reserved.add("else");
		reserved.add("while");
		reserved.add("break");
		reserved.add("continue");
		reserved.add("input");
		reserved.add("output");
	}
	
	public Lexer() {
		tokenList = new LinkedList<Token>();
	}
	
	/* (non-Javadoc)
	 * @see canto.Lexer#open(java.io.InputStreamReader)
	 */
	@Override
	public void open(InputStreamReader inStr) throws IOException {
		inBuf = new LineNumberReader(inStr);
		inBuf.ready();
		inBuf.setLineNumber(1);
	}

	/* (non-Javadoc)
	 * @see canto.Lexer#scan()
	 */
	@Override
	public List<Token> scan() throws IOException {
		// TODO Auto-generated method stub
		int intBufChar = inBuf.read();
		while (intBufChar != -1) {
			
			int line = inBuf.getLineNumber();
			StringBuffer lexBuf = new StringBuffer();
			
			if (intBufChar == '_' || 'a' <= intBufChar && intBufChar <= 'z' 
					|| 'A' <= intBufChar && intBufChar <= 'Z') {
				
				intBufChar = recognizeWord((char)intBufChar, lexBuf);
				String lexeme = lexBuf.toString();
				
				if (reserved.contains(lexeme)) {
					tokenList.add(new Keyword(line, 0, lexeme));
				} else {
					tokenList.add(new Identifier(line, 0, lexeme));
				}
				
			} else if ('0' <= intBufChar && intBufChar <= '9') {
				
				intBufChar = recognizeInteger((char)intBufChar, lexBuf);
				String lexeme = lexBuf.toString();
				tokenList.add(new IntegerLiteral(line, 0, lexeme));
				
			} else if (intBufChar == ' ' || intBufChar == '\t' || intBufChar == '\n') {
				
				intBufChar = skipWhiteSpaces();

			} else if (intBufChar == '=') {
				
				int nextChar = inBuf.read();
				if (nextChar == '=') {
					tokenList.add(new Operator(line, 0, "=="));
					intBufChar = -1;
				} else {
					tokenList.add(new Punctuation(line, 0, "="));
					intBufChar = nextChar;
				}
				
			} else if (intBufChar == ';' || intBufChar == '{' || intBufChar == '}' 
				|| intBufChar == '(' || intBufChar == ')') {
				
				intBufChar = recognizePunctuation((char)intBufChar, lexBuf);
				String lexeme = lexBuf.toString();
				tokenList.add(new Punctuation(line, 0, lexeme));
				
			} else {
				
				intBufChar = recognizeOperator((char)intBufChar, lexBuf);
				String lexeme = lexBuf.toString();
				tokenList.add(new Operator(line, 0, lexeme));
				
			}
			
			if (intBufChar == -1) {
				intBufChar = inBuf.read();
			}
		} // while
		return null;
	}
	
	private int recognizePunctuation(char bufChar, StringBuffer lexBuf) {
		lexBuf.append(bufChar);
		return -1;
	}

	private int recognizeOperator(char bufChar, StringBuffer lexBuf) throws IOException {
		switch (bufChar) {
			case '+': 
			case '-':
			case '*':
				lexBuf.append(bufChar);
				break;
			case '>':
			case '<':
			case '!':
				int intNextChar = inBuf.read();
				if (intNextChar == '=') {
					lexBuf.append(bufChar).append((char)intNextChar).toString();
				} else {
					lexBuf.append(bufChar);
					return intNextChar;
				}
				break;
			case '=':
			case '|':
			case '&':
				intNextChar = inBuf.read();
				if (intNextChar == bufChar) {
					lexBuf.append(bufChar).append((char)intNextChar).toString();
				} else {
					lexBuf.append(bufChar);
					return intNextChar;
				}
				break;
			default:
				
		}
		return -1;
	}

	private int recognizeInteger(char prefix, StringBuffer lexBuf) throws IOException {
		lexBuf.append(prefix);
		int intNextChar = inBuf.read();
		while (intNextChar != -1 && '0' <= intNextChar && intNextChar <= '9') {
			lexBuf.append((char)intNextChar);
			intNextChar = inBuf.read();
		}
		return intNextChar;
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

	private int recognizeWord(char prefix, StringBuffer lexBuf) throws IOException {
		lexBuf.append(prefix);
		int intNextChar = inBuf.read();
		while (intNextChar != -1) {
			if ('a' <= intNextChar && intNextChar <= 'z'
				|| 'A' <= intNextChar && intNextChar <= 'Z') {
				lexBuf.append((char)intNextChar);
				intNextChar = inBuf.read();
			} else break;
		}
		return intNextChar;
	}

	@Override
	public List<Token> getTokenList() {
		return tokenList;
	}

}
