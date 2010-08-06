/**
 * 
 */
package canto.c1.token;


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
		super(line, column, lexeme, terminalMap.get(lexeme));
	}

	/* (non-Javadoc)
	 * @see canto.c1.Token#getAttribute()
	 */
	@Override
	public Object getAttribute() {
		// TODO Auto-generated method stub
		return super.getLexeme();
	}

}
