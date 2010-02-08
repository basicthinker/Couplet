package canto.c1.ast;

public class InputStatement extends Statement {

	/** The identifier of input. */
	private final Identifier identifier;
	
	/**
	 * Construct an input statement
	 * @param identifier the identifier of input
	 */
	public InputStatement(Identifier identifier) {
		this.identifier = identifier;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return INPUT_STATEMENT;
	}

	/**
	 * Get the identifier of input
	 * @return the identifier of input
	 */
	public Identifier getIdentifier() {
		return identifier;
	}

}
