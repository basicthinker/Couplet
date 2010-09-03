package canto.c1.token;

import java.util.HashMap;

public abstract class Token implements canto.Token {

	/** 终极符编号常量 */
	public static final int ID = 1;
	public static final int INTEGER_LITERAL = 2;
	public static final int IF = 3;
	public static final int ELSE = 4;
	public static final int WHILE = 5;
	public static final int INPUT = 6;
	public static final int OUTPUT = 7;
	public static final int L_BRACE = 8;
	public static final int R_BRACE = 9;
	public static final int L_PARENT = 10;
	public static final int R_PARENT  = 11;
	public static final int SEMI = 12;
	public static final int EQUAL = 13;
	public static final int NOT = 14;
	public static final int PLUS = 15;
	public static final int MINUS = 16;
	public static final int TIMES = 17;
	public static final int LESS = 18;
	public static final int LESS_EQUAL = 19;
	public static final int GREATER = 20;
	public static final int GREATER_EQUAL = 21;
	public static final int EQUAL_EQUAL = 22;
	public static final int NOT_EQUAL = 23;
	public static final int AND_AND = 24;
	public static final int OR_OR = 25;

	private final int line;
	
	private final int column;
	
	private final String lexeme;
	
	protected int tokenType;
	
	protected static final HashMap<String, Integer> tokenCodeMap;
	
	protected static final String[] codeTokenMap;
	
	static {
		tokenCodeMap = new HashMap<String, Integer>();
		tokenCodeMap.put("if", IF);
		tokenCodeMap.put("else", ELSE);
		tokenCodeMap.put("while", WHILE);
		tokenCodeMap.put("input", INPUT);
		tokenCodeMap.put("output", OUTPUT);
		tokenCodeMap.put("{", L_BRACE);
		tokenCodeMap.put("}", R_BRACE);
		tokenCodeMap.put("(", L_PARENT);
		tokenCodeMap.put(")", R_PARENT);
		tokenCodeMap.put(";", SEMI);
		tokenCodeMap.put("=", EQUAL);
		tokenCodeMap.put("!", NOT);
		tokenCodeMap.put("+", PLUS);
		tokenCodeMap.put("-", MINUS);
		tokenCodeMap.put("*", TIMES);
		tokenCodeMap.put("<", LESS);
		tokenCodeMap.put("<=", LESS_EQUAL);
		tokenCodeMap.put(">", GREATER);
		tokenCodeMap.put(">=", GREATER_EQUAL);
		tokenCodeMap.put("==", EQUAL_EQUAL);
		tokenCodeMap.put("!=", NOT_EQUAL);
		tokenCodeMap.put("&&", AND_AND);
		tokenCodeMap.put("||", OR_OR);

		codeTokenMap = new String[26];
		codeTokenMap[ID] = "Identifier";
		codeTokenMap[INTEGER_LITERAL] = "Integer Literal";
		codeTokenMap[IF] = "if";
		codeTokenMap[ELSE] = "else";
		codeTokenMap[WHILE] = "while";
		codeTokenMap[INPUT] = "input";
		codeTokenMap[OUTPUT] = "output";
		codeTokenMap[L_BRACE] = "{";
		codeTokenMap[R_BRACE] = "}";
		codeTokenMap[L_PARENT] = "(";
		codeTokenMap[R_PARENT] = ")";
		codeTokenMap[SEMI] = ";";
		codeTokenMap[EQUAL] = "=";
		codeTokenMap[NOT] = "!";
		codeTokenMap[PLUS] = "+";
		codeTokenMap[MINUS] = "-";
		codeTokenMap[TIMES] = "*";
		codeTokenMap[LESS] = "<";
		codeTokenMap[LESS_EQUAL] = "<=";
		codeTokenMap[GREATER] = ">";
		codeTokenMap[GREATER_EQUAL] = ">=";
		codeTokenMap[EQUAL_EQUAL] = "==";
		codeTokenMap[NOT_EQUAL] = "!=";
		codeTokenMap[AND_AND] = "&&";
		codeTokenMap[OR_OR] = "||";
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
	
	/**
	 * 由Token类型编号获取Token字符串
	 * @param tokenCode Token类型编号
	 * @return Token字符串
	 */
	public static String getCodeToken(int tokenCode) {
		return codeTokenMap[tokenCode];
	}

}
