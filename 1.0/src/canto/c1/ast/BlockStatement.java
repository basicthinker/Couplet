package canto.c1.ast;

public class BlockStatement extends Statement {

	/** The statements in the block. */
	private final StatementList statementList;
	
	/**
	 * Construct a block.
	 * @param statementList statements in the block
	 */
	public BlockStatement(StatementList statementList) {
		this.statementList = statementList;
	}
	
	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public NodeType getNodeType() {
		return NodeType.BLOCK;
	}

	/**
	 * Get the statements in the block.
	 * @return the statements list
	 */
	public StatementList getStatementList() {
		return statementList;
	}

}
