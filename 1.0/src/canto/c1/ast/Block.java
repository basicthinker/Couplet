package canto.c1.ast;

public class Block extends Statement {

	/** The statement list. */
	private final StatementList statementList;
	
	/**
	 * Construct a block.
	 * @param statementList the statement list
	 */
	public Block(StatementList statementList) {
		this.statementList = statementList;
	}
	
	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return BLOCK;
	}

	/**
	 * Get the statement list.
	 * @return the statement list
	 */
	public StatementList getStatementList() {
		return statementList;
	}
	
}
