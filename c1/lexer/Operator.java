/**
 * 
 */
package canto.c1.lexer;

/**
 * @author basicthinker
 *
 */
public class Operator extends Token {

	/**
	 * @param line
	 * @param column
	 * @param lexeme
	 */
	public Operator(int line, int column, String lexeme) {
		super(line, column, lexeme);
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

}
