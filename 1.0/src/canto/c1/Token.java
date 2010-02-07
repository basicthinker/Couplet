/**
 * 
 */
package canto.c1;

/**
 * @author basicthinker
 * 
 */
public abstract class Token implements canto.Token {

	private int lineNumber;
	private String lexeme;
	
	public static final int CONSTANT = 1;
	public static final int ID = 2;
	public static final int KEYWORD = 3;
	public static final int OPERERATOR = 4;
	public static final int PUNCTUATION = 5;
	
	/**
	 * @param line 单词所在行号
	 * @param column 单词所在列数
	 * @param lexeme 单词的字面
	 */
	public Token(int line, String lexeme) {
		setLineNumber(line);
		setLexeme(lexeme);
	}
	
	/**
	 * 
	 */
	public Token() {
		setLineNumber(0);
		setLexeme("");
	}
	
	/**
	 * @param lineNumber 单词所在行号
	 */
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * @return 单词所在行号
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * @return 单词所在列数
	 */
	public int getColumnNumber() {
		return 0;
	}

	/**
	 * @param lexeme 单词
	 */
	public void setLexeme(String lexeme) {
		this.lexeme = lexeme;
	}

	/**
	 * @return 单词
	 */
	public String getLexeme() {
		return lexeme;
	}

}
