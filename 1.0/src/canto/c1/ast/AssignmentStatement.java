package canto.c1.ast;

public class AssignmentStatement extends Statement {

	/** The identifier on the left of assignment. */
	private final Identifier leftIdentifier;
	
	/** The expression on the right of assignment. */
	private final Expression rightExpression;
	
	/**
	 * Construct a assignment statement.
	 * @param leftIdentifier the left identifier
	 * @param rightExpression the right expression
	 */
	public AssignmentStatement(Identifier leftIdentifier, 
			Expression rightExpression) {
		this.leftIdentifier = leftIdentifier;
		this.rightExpression = rightExpression;
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
	public Identifier getLeftIdentifier() {
		return leftIdentifier;
	}

	/**
	 * Get the expression on the right of assignment.
	 * @return the right expression
	 */
	public Expression getRightExpression() {
		return rightExpression;
	}
	
}
