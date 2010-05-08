package canto.c1.token;

public class Keyword extends Token {

	/**
	 * @param line
	 * @param column
	 * @param lexeme
	 */
	public Keyword(int line, int column, String lexeme) {
		super(line, column, lexeme, terminalMap.get(lexeme));
	}

	/* (non-Javadoc)
	 * @see canto.c1.Token#getAttribute()
	 */
	@Override
	public String getAttribute() {
		return super.getLexeme();
	}

}
