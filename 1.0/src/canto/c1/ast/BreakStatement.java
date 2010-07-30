package canto.c1.ast;

import canto.CantoException;

/**
 * BREAK语句结点
 */
public class BreakStatement extends Statement {

	/**
	 * 构造一个BREAK语句结点
	 * @param line
	 * @param column
	 */
	public BreakStatement(int line, int column) {
		super(line, column);
	}
	
	@Override
	public void accept(ASTVisitor visitor) throws CantoException {
		visitor.visit(this);
	}

	@Override
	public int getASTType() {
		return BREAK_STATEMENT;
	}

}
