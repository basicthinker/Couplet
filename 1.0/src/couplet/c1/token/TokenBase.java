package couplet.c1.token;

import java.util.HashMap;

/**
 * C1语言Token的基类
 */
public abstract class TokenBase implements Token {

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
	public TokenBase(int line, int column, String lexeme, int tokenType) {
		this.line = line;
		this.column = column;
		this.lexeme = lexeme;
		this.tokenType = tokenType;
	}
	
	@Override
	public int getLine() {
		return line;
	}
	
	@Override
	public int getColumn() {
		return column;
	}

	@Override
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
