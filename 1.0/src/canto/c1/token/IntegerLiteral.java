/**
 * 
 */
package canto.c1.token;


/**
 * @author basicthinker
 *
 */
public class IntegerLiteral extends Token {

	private final int value;
	
	/**
	 * @param line
	 * @param column
	 * @param lexeme
	 */
	public IntegerLiteral(int line, int column, String lexeme) {
		super(line, column, lexeme, INTEGER_LITERAL);
		value = Integer.parseInt(lexeme);
	}

	/* (non-Javadoc)
	 * @see canto.c1.Token#getAttribute()
	 */
	@Override
	public Integer getAttribute() {
		return value;
	}

}
