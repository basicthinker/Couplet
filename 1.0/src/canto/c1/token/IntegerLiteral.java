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
	 * @param lineNum
	 * @param columnNum
	 * @param lexeme
	 */
	public IntegerLiteral(int lineNumber, String lexeme) {
		super(lineNumber, lexeme);
		value = Integer.parseInt(lexeme);
	}

	/**
	 * @param lexeme 
	 */
	public IntegerLiteral(String lexeme) {
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
