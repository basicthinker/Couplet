/**
 * 
 */
package couplet.c1.token;


/**
 * @author basicthinker
 *
 */
public class IntegerLiteral extends TokenBase {

	private final int value;
	
	/**
	 * @param line
	 * @param column
	 * @param value
	 */
	public IntegerLiteral(int line, int column, int value) {
		super(line, column, String.valueOf(value), INTEGER_LITERAL);
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see canto.c1.Token#getAttribute()
	 */
	@Override
	public Integer getAttribute() {
		return value;
	}

}
