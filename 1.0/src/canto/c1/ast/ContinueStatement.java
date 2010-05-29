package canto.c1.ast;

import canto.CantoException;

/**
 * CONTINUE语句结点
 */
public class ContinueStatement extends Statement {

	/**
	 * 构造一个CONTINUE语句结点
	 * @param line
	 * @param column
	 */
	public ContinueStatement(int line, int column) {
		super(line, column);
	}
	
	@Override
	public void accept(ASTVisitor visitor) throws CantoException {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return CONTINUE_STATEMENT;
	}

}
