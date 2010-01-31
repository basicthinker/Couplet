package canto.c1.ast;

public class PrimitiveType extends Type {

	/**
	 * Types of the primitive type. 
	 */
	enum Type {
		INTEGER,
	}
	
	/** The type of the primitive type. */
	private final PrimitiveType.Type type;
	
	/**
	 * Construct a primitive type.
	 * @param type the type of the primitive type.
	 */
	public PrimitiveType(PrimitiveType.Type type) {
		this.type = type;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public NodeType getNodeType() {
		return NodeType.PRIMITIVE_TYPE;
	}

	/**
	 * Get the type of the primitive type.
	 * @return the type of the primitive type.
	 */
	public PrimitiveType.Type getType() {
		return type;
	}

}
