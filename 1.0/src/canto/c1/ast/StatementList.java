package canto.c1.ast;

public class StatementList extends ASTNode {
	
	/** The first statement in the list. */
	private final Statement head;
	
	/** The remaining statements in the list. */
	private final StatementList tail;

	/**
	 * Construct a statement list with multiple statements.
	 * @param head the first statement
	 * @param tail the remaining statements
	 */
	public StatementList(Statement head, StatementList tail) {
		this.head = head;
		this.tail = tail;
	}
	
	/**
	 * Construct a statement list with one statement.
	 * @param head
	 */
	public StatementList(Statement head) {
		this.head = head;
		this.tail = null;
	}	
	

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public NodeType getNodeType() {
		return NodeType.STATEMENT_LIST;
	}

	/**
	 * Get the first statement in the list
	 * @return the first statement
	 */
	public Statement getHead() {
		return head;
	}

	/**
	 * Get the remaining statements in the list.
	 * @return the remaining statements
	 */
	public StatementList getTail() {
		return tail;
	}

}
