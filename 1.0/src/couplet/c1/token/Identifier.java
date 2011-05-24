 /**
 * 
 */
package couplet.c1.token;


/**
 * @author basicthinker
 *
 */
public class Identifier extends TokenBase {

	/**
	 * @param line
	 * @param column
	 * @param word
	 */
	public Identifier(int line, int column, String word) {
		super(line, column, word, ID);
	}

	/* (non-Javadoc)
	 * @see couplet.c1.Token#getAttribute()
	 */
	@Override
	public Object getAttribute() {
		// TODO Auto-generated method stub
		return super.getLexeme();
	}

}
