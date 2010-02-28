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

	private final int value;
	
	/**
	 * @param lineNum
	 * @param columnNum
	 * @param lexeme
	 */
	public Constant(int lineNumber, String lexeme) {
		super(lineNumber, lexeme);
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

	@Override
	public int getType() {
		return Token.CONSTANT;
	}
}
