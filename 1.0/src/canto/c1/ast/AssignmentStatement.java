package canto.c1.ast;

public class AssignmentStatement extends Statement {

	/** The identifier on the left of assignment. */
	private final Identifier identifier;
	
	/** The expression on the right of assignment. */
	private final Expression expression;
	
	/**
	 * Construct a assignment statement.
	 * @param identifier the left identifier
	 * @param expression the right expression
	 */
	public AssignmentStatement(Identifier identifier, Expression expression) {
		this.identifier = identifier;
		this.expression = expression;
	}
	
	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public NodeType getNodeType() {
		return NodeType.ASSIGNMENT_STATEMENT;
	}

	/**
	 * Get the identifier on the left of assignment.
	 * @return the left identifier
	 */
	public Identifier getIdentifier() {
		return identifier;
	}

	/**
	 * Get the expression on the right of assignment.
	 * @return the right expression
	 */
	public Expression getExpression() {
		return expression;
	}
	
}
