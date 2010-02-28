package canto.c1.ast;

public class UnaryExpression extends Expression {

	/** The operator in the unary expression. */
	private final Operator operator;
	
	/** The expression in the unary expression. */
	private final Expression operand;
		
	/**
	 * Construct an unary expression.
	 * @param operator the operator
	 * @param operand the operand
	 */
	public UnaryExpression(Operator operator, Expression operand) {
		this.operator = operator;
		this.operand = operand;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return UNARY_EXPRESSION;
	}

	/**
	 * Get the operator of the expression.
	 * @return the operator
	 */
	public Operator getOperator() {
		return operator;
	}

	/**
	 * Get the operand of the expression.
	 * @return the operand
	 */
	public Expression getOperand() {
		return operand;
	}

}
