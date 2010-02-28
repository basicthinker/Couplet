/**
 * 
 */
package canto.c1.token;

import canto.c1.Token;

/**
 * @author basicthinker
 *
 */
public class Punctuation extends Token {

	/**
	 * @param lineNumber
	 * @param column
	 * @param lexeme
	 */
	public Punctuation(int lineNumber, String lexeme) {
		super(lineNumber, lexeme);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public Punctuation() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see canto.c1.Token#getAttribute()
	 */
	@Override
	public Object getAttribute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getType() {
		return Token.PUNCTUATION;
	}

}
