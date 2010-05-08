package canto.c1.token;

import java.util.HashMap;

public abstract class Token implements canto.Token {

	/** 终极符编号常量 */
	public static final int ID = 1;
	public static final int INTEGER_LITERAL = 2;
	public static final int IF = 3;
	public static final int ELSE = 4;
	public static final int WHILE = 5;
	public static final int BREAK = 6;
	public static final int CONTINUE = 7;
	public static final int INPUT = 8;
	public static final int OUTPUT = 9;
	public static final int L_BRACE = 10;
	public static final int R_BRACE = 11;
	public static final int L_PARENT = 12;
	public static final int R_PARENT  = 13;
	public static final int SEMI = 14;
	public static final int EQUAL = 15;
	public static final int NOT = 16;
	public static final int PLUS = 17;
	public static final int MINUS = 18;
	public static final int TIMES = 19;
	public static final int LESS = 20;
	public static final int LESS_EQUAL = 21;
	public static final int GREATER = 22;
	public static final int GREATER_EQUAL = 23;
	public static final int EQUAL_EQUAL = 24;
	public static final int NOT_EQUAL = 25;
	public static final int AND_AND = 26;
	public static final int OR_OR = 27;

	private final int line;
	
	private final int column;
	
	private final String lexeme;
	
	protected int tokenType;
	
	protected static final HashMap<String, Integer> terminalMap;
	
	static {
		terminalMap = new HashMap<String, Integer>();
		terminalMap.put("if", IF);
		terminalMap.put("else", ELSE);
		terminalMap.put("while", WHILE);
		terminalMap.put("break", BREAK);
		terminalMap.put("continue", CONTINUE);
		terminalMap.put("input", INPUT);
		terminalMap.put("output", OUTPUT);
		terminalMap.put("{", L_BRACE);
		terminalMap.put("}", R_BRACE);
		terminalMap.put("(", L_PARENT);
		terminalMap.put(")", R_PARENT);
		terminalMap.put(";", SEMI);
		terminalMap.put("=", EQUAL);
		terminalMap.put("!", NOT);
		terminalMap.put("+", PLUS);
		terminalMap.put("-", MINUS);
		terminalMap.put("*", TIMES);
		terminalMap.put("<", LESS);
		terminalMap.put("<=", LESS_EQUAL);
		terminalMap.put(">", GREATER);
		terminalMap.put(">=", GREATER_EQUAL);
		terminalMap.put("==", EQUAL_EQUAL);
		terminalMap.put("!=", NOT_EQUAL);
		terminalMap.put("&&", AND_AND);
		terminalMap.put("||", OR_OR);
	}
	
	/**
	 * @param line 单词所在行号
	 * @param column 单词所在列数
	 * @param lexeme 单词的字面
	 */
	public Token(int line, int column, String lexeme, int tokenType) {
		this.line = line;
		this.column = column;
		this.lexeme = lexeme;
		this.tokenType = tokenType;
	}
	
	/**
	 * @return 单词所在行号
	 */
	public int getLine() {
		return line;
	}
	
	/**
	 * @return 单词所在列数
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @return 单词
	 */
	public String getLexeme() {
		return lexeme;
	}
	
	@Override
	public int getTokenType() {
		return tokenType;
	}

}
