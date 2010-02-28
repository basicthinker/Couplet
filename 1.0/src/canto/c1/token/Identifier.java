/**
 * 
 */
package canto.c1.token;

import canto.c1.Token;

/**
 * @author basicthinker
 *
 */
public class Identifier extends Token {

	/**
	 * @param lineNumber
	 * @param column
	 * @param word
	 */
	public Identifier(int lineNumber, String word) {
		super(lineNumber, word);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public Identifier() {
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
		return Token.ID;
	}

}
