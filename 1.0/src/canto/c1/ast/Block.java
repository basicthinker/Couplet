package canto.c1.ast;

/**
 * BLOCK语句结点
 */
public class Block extends Statement {

	/** 语句列表 */
	private final StatementList statementList;
	
	/**
	 * 构造一个Block语句结点
	 * @param statementList 语句列表
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
	 * 获取语句列表
	 * @return 语句列表
	 */
	public StatementList getStatementList() {
		return statementList;
	}
	
}
