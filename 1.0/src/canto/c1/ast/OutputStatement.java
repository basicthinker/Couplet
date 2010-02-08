package canto.c1.ast;

public class OutputStatement extends Statement {

	/** The expression of output. */
	private final Expression expression;
		
	/**
	 * Construct a input statement.
	 * @param expression the expression of output.
	 */
	public OutputStatement(Expression expression) {
		this.expression = expression;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return OUTPUT_STATEMENT;
	}

	/**
	 * Get the expression of output.
	 * @return the expression of output
	 */
	public Expression getExpression() {
		return expression;
	}
	
}
