package canto.c1.ast;

public class WhileStatement extends Statement {

	/** The condition expression in the WHILE statement. */
	private final Expression condition;
	
	/** The body statement in the WHILE statement. */
	private final Statement body;
	
	/**
	 * Construct from the condition expression and body statement.
	 * @param condition the condition expression
	 * @param body the body statement
	 */
	public WhileStatement(Expression condition, Statement body) {
		this.condition = condition;
		this.body = body;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return WHILE_STATEMENT;
	}

	/**
	 * Get the condition expression in the WHILE statement. 
	 * @return the condition expression
	 */
	public Expression getCondition() {
		return condition;
	}

	/**
	 * Get the body statement in the WHILE statement.
	 * @return the body statement
	 */
	public Statement getBody() {
		return body;
	}

}
