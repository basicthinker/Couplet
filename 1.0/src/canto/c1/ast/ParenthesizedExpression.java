package canto.c1.ast;

public class ParenthesizedExpression extends Expression {

	/** The body expression in the parenthesis. */
	private final Expression body;
	
	/**
	 * Construct a parenthesized expression.
	 * @param body the body expression
	 */
	public ParenthesizedExpression(Expression body) {
		this.body = body;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return PARENTHESIZED_EXPRESSION;
	}

	/**
	 * Get the body expression in the parenthesis.
	 * @return the body
	 */
	public Expression getBody() {
		return body;
	}

}
