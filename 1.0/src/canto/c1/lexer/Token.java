/**
 * 
 */
package canto.c1.lexer;

/**
 * @author basicthinker
 * 
 */
public abstract class Token implements canto.c1.Token {

	/**
	 * @param line the line to set
	 * @param column the column to set
	 * @param lexeme the lexeme to set
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
		this.line = line;
	}

	/**
	 * @return the line
	 */
	public int getLine() {
		return line;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
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

	private int line;
	private int column;
	private String lexeme;
}
