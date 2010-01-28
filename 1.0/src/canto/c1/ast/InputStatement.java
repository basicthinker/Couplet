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
		// TODO Auto-generated method stub

	}

	@Override
	public NodeType getNodeType() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Get the identifier of input
	 * @return the identifier of input
	 */
	public Identifier getIdentifier() {
		return identifier;
	}

}
