/**
 * 
 */
package canto.c1.lexer;

/**
 * @author basicthinker
 *
 */
public class Identifier extends Token {

	/**
	 * @param line
	 * @param column
	 * @param word
	 */
	public Identifier(int line, int column, String word) {
		super(line, column, word);
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

}
