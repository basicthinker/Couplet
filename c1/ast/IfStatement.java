package canto.c1.ast;

public class IfStatement extends Statement {

	/** The condition expression in the IF statement. */
	private final Expression condition;
	
	/** The THEN statement in the IF statement. */
	private final Statement thenStatement;
	
	/** The ELSE statement in the IF statement. */
	private final Statement elseStatement;
			
	/**
	 * Construct a IF statement with THEN and ELSE statements. 
	 * @param condition the condition expression
	 * @param thenStatement the THEN statement
	 * @param elseStatement the ELSE statement
	 */
	public IfStatement(Expression condition, Statement thenStatement,
			Statement elseStatement) {
		this.condition = condition;
		this.thenStatement = thenStatement;
		this.elseStatement = elseStatement;
	}
	
	/**
	 * Construct a IF statement with only THEN statement. 
	 * @param condition the condition expression
	 * @param thenStatement the THEN statement
	 */
	public IfStatement(Expression condition, Statement thenStatement) {
		this.condition = condition;
		this.thenStatement = thenStatement;
		this.elseStatement = null;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public NodeType getNodeType() {
		return NodeType.IF_STATEMENT;
	}

	/**
	 * Get the condition expression.
	 * @return the condition expression
	 */
	public Expression getCondition() {
		return condition;
	}

	/**
	 * Get the THEN statement.
	 * @return the THEN statement
	 */
	public Statement getThenStatement() {
		return thenStatement;
	}

	/**
	 * Get the ELSE statement.
	 * @return the ELSE statement
	 */
	public Statement getElseStatement() {
		return elseStatement;
	}

}
