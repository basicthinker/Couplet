/**
 * 
 */
package canto.c1.token;

import canto.c1.Token;

/**
 * @author basicthinker
 *
 */
public class Operator extends Token {

	/**
	 * @param lineNumber
	 * @param column
	 * @param lexeme
	 */
	public Operator(int lineNumber, String lexeme) {
		super(lineNumber, lexeme);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public Operator() {
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
		return Token.OPERERATOR;
	}

}
