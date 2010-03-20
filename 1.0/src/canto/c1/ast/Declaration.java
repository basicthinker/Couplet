package canto.c1.ast;

public class Declaration extends Listable {

	/** The type in the declaration. */
	private final Type type;
	
	/** The identifier in the declaration. */
	private final Identifier identifier;

	/**
	 * Construct a declaration.
	 * @param type
	 * @param identifier
	 */
	public Declaration(Type type, Identifier identifier) {
		this.type = type;
		this.identifier = identifier;
	}
	
	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return DECLARATION;
	}

	/**
	 * Get the type in the declaration. 
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Get the identifier in the declaration.
	 * @return the identifier
	 */
	public Identifier getIdentifier() {
		return identifier;
	}

	
}
