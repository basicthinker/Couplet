package canto.c1.ast;

public class UnaryOperator extends Operator {

	/** The operator code constant of each unary operator. */
	public static final int POSITIVE = 1;
	public static final int NEGTIVE = 2;
	public static final int NOT = 3;
	
	/**
	 * Construct an unary operator.
	 * @param code the operator code
	 */
	public UnaryOperator(int code) {
		super(code);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return UNARY_OPERATOR;
	}

}
