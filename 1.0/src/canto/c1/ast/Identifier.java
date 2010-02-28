package canto.c1.ast;

public class Identifier extends Expression {

	/** The name of the identifier. */
	private final String name;
	
	/**
	 * Construct an identifier.
	 * @param name the name of the identifier
	 */
	public Identifier(String name) {
		this.name = name;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return IDENTIFIER;
	}

	/**
	 * Get the name of the identifier.
	 * @return the name of the identifier
	 */
	public String getName() {
		return name;
	}

}
