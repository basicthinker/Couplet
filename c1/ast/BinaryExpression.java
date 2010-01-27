package canto.c1.ast;

public class BinaryExpression extends Expression {

	/**
	 * Binary operator. 
	 */
	public enum Operator {
		PLUS,
		MINUS,
		TIMES,
		LESS,
		GREATER,
		LESS_EQUALS,
		GREATER_EQUALS,
		EQUALS,
		NOT_EQUALS,
	}
	
	/** The operator in the binary expression. */
	private final Operator operator;
	
	/** The left operand in the binary expression. */
	private final Expression leftOperand;
	
	/** The right operand in the binary expression. */
	private final Expression rightOperand;
	
	/**
	 * Construct a binary expression. 
	 * @param operator the operator
	 * @param leftOperand the left operand
	 * @param rightOperand the right operand
	 */
	public BinaryExpression(Operator operator,
			Expression leftOperand, Expression rightOperand) {
		this.operator = operator;
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public NodeType getNodeType() {
		return NodeType.BINARY_EXPRESSION;
	}

	/**
	 * Get the operator in the binary expression.
	 * @return the operator
	 */
	public Operator getOperator() {
		return operator;
	}

	/**
	 * Get the left operand in the binary expression.
	 * @return the left operand
	 */
	public Expression getLeftOperand() {
		return leftOperand;
	}

	/**
	 * Get the right operand in the binary expression.
	 * @return the right operand
	 */
	public Expression getRightOperand() {
		return rightOperand;
	}
}
