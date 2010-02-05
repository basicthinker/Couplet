/**
 * 
 */
package canto.c1;

/**
 * @author basicthinker
 * 
 */
public abstract class Token implements canto.Token {

	private int lineNum;
	private int columnNum;
	private String lexeme;
	
	public static final int CONSTANT = 1;
	public static final int ID = 2;
	public static final int KEYWORD = 3;
	public static final int OPERERATOR = 4;
	public static final int PUNCTUATION = 5;
	
	/**
	 * @param line 单词所在的行号
	 * @param column 单词所在的列数
	 * @param lexeme 单词的字面
	 */
	public Token(int line, int column, String lexeme) {
		setLine(line);
		setColumn(column);
		setLexeme(lexeme);
	}
	
	/**
	 * 
	 */
	public Token() {
		setLine(0);
		setColumn(0);
		setLexeme("");
	}
	
	/**
	 * @param line the line to set
	 */
	public void setLine(int line) {
		this.lineNum = line;
	}

	/**
	 * @return the line
	 */
	public int getLineNum() {
		return lineNum;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn(int column) {
		this.columnNum = column;
	}

	/**
	 * @return the column
	 */
	public int getColumnNum() {
		return columnNum;
	}

	/**
	 * @param lexeme the lexeme to set
	 */
	public void setLexeme(String lexeme) {
		this.lexeme = lexeme;
	}

	/**
	 * @return the lexeme
	 */
	public String getLexeme() {
		return lexeme;
	}

}
