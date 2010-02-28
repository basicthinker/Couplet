package canto.c1.ast;

public class IntegerLiteral extends Literal {

	/** The value of the integer literal. */
	private final Integer value;
	
	/**
	 * Construct a integer literal.
	 * @param value the value of integer literal
	 */
	public IntegerLiteral(Integer value) {
		this.value = value;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return INTEGER_LITERAL;
	}

	/**
	 * Get the value of the integer literal.
	 * @return the value of the integer literal
	 */
	public Integer getValue() {
		return value;
	}

}
