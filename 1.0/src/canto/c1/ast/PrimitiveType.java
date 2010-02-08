package canto.c1.ast;

public class PrimitiveType extends Type {

	/** The type code constant of each primitive type. */
	public static final int INTEGER = 1;
	
	/** The type code indicating the kind of the primitive type. */
	private final int code;
	
	/**
	 * Construct a primitive type.
	 * @param type the type code
	 */
	public PrimitiveType(int code) {
		this.code = code;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return PRIMITIVE_TYPE;
	}

	/**
	 * Get the type code of the primitive type.
	 * @return the type code
	 */
	public int getTypeCode() {
		return code;
	}

}
