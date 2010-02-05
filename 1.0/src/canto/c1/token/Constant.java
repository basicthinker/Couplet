/**
 * 
 */
package canto.c1.token;

import canto.c1.Token;

/**
 * @author basicthinker
 *
 */
public class Constant extends Token {

	/**
	 * @param line
	 * @param column
	 * @param lexeme
	 */
	public Constant(int line, int column, String lexeme) {
		super(line, column, lexeme);
		value = Integer.parseInt(lexeme);
	}

	/**
	 * @param lexeme 
	 */
	public Constant(String lexeme) {
		value = Integer.parseInt(lexeme);
	}

	/* (non-Javadoc)
	 * @see canto.c1.Token#getAttribute()
	 */
	@Override
	public Integer getAttribute() {
		return value;
	}

	private final int value;

	@Override
	public int getType() {
		return Token.CONSTANT;
	}
}
