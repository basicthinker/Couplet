/**
 * 
 */
package couplet.c1.token;


/**
 * @author basicthinker
 *
 */
public class Punctuation extends TokenBase {

	/**
	 * @param line
	 * @param column
	 * @param lexeme
	 */
	public Punctuation(int line, int column, String lexeme) {
		super(line, column, lexeme, tokenCodeMap.get(lexeme));
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
