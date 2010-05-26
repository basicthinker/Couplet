package canto.c1;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import canto.CantoException;
import canto.Token;
import canto.c1.token.*;
import canto.exception.*;
import canto.c1.exception.*;

/**
 * @author basicthinker
 * @author WANGZHE
 *
 */
public class Lexer implements canto.Lexer {

	private LineNumberReader inBuf;
	private static HashSet<String> reserved;
	private List<Token> tokenList;
	private static int tabWith;
	
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
		tabWith = 4;
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
		int intBufChar = inBuf.read();
		
		//设置初始列号
		int column = 1;
		while (intBufChar != -1) {
			
			int line = inBuf.getLineNumber();		
			StringBuffer lexBuf = new StringBuffer();
			
			if (intBufChar == '_' || 'a' <= intBufChar && intBufChar <= 'z' 
					|| 'A' <= intBufChar && intBufChar <= 'Z') {
				
				intBufChar = recognizeWord((char)intBufChar, lexBuf);
				
				String lexeme = lexBuf.toString();
				
				if (reserved.contains(lexeme)) {
					tokenList.add(new Keyword(line, column, lexeme));
				} else {
					tokenList.add(new Identifier(line, column, lexeme));
				}
				
				column += lexeme.length();
				
			} else if ('0' <= intBufChar && intBufChar <= '9') {
				
				intBufChar = recognizeInteger((char)intBufChar, lexBuf);
				String lexeme = lexBuf.toString();
				tokenList.add(new IntegerLiteral(line, column, lexeme));
				
				column += lexeme.length();
				
			} else if (intBufChar == ' '  ) {
				
				//intBufChar = skipWhiteSpaces();
				intBufChar = inBuf.read();
				column += 1;
				
			} else if (intBufChar == '\t') {
				
				//intBufChar = skipWhiteSpaces();
				intBufChar = inBuf.read();
				column += tabWith;
				
			}else if (intBufChar == '\n'){

				intBufChar = inBuf.read();
				
				//换行之后列号重置为1
				column = 1;

			} else if (intBufChar == '=') {
				
				int nextChar = inBuf.read();
				if (nextChar == '=') {
					tokenList.add(new Operator(line, column, "=="));
					column += 2;
					intBufChar = -1;
				} else {
					tokenList.add(new Punctuation(line, column, "="));
					column += 1;
					intBufChar = nextChar;
				}
				
				
			} else if (intBufChar == ';' || intBufChar == '{' || intBufChar == '}' 
				|| intBufChar == '(' || intBufChar == ')') {
				
				intBufChar = recognizePunctuation((char)intBufChar, lexBuf);
				String lexeme = lexBuf.toString();
				tokenList.add(new Punctuation(line, column, lexeme));
				column += lexeme.length();
				
			} else {
				
				try{				
					intBufChar = recognizeOperator((char)intBufChar, lexBuf);
					if (intBufChar == -3)
						//throw ExceptionFactory.newException(line, column, CantoExceptionClass.Lex, CantoExceptionType.TestType, CantoExceptionLevel.error);
						throw new LexException(line, column, LexException.IllegleCharactor, CantoException.LevelError);
					String lexeme = lexBuf.toString();
					tokenList.add(new Operator(line, column, lexeme));
					column += lexeme.length();
				}
				catch(LexException e){
					System.out.println(e.getExceptionMsg());
				}
				finally{
					intBufChar = inBuf.read();
					column += 1;
				}
				
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
				return -3;
				
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
