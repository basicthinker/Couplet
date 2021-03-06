package couplet.c1.ast;

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
	public Block(StatementList statementList, int line, int column) {
		super(line, column);
		this.statementList = statementList;
		if (statementList != null) statementList.setParent(this);
	}
	
	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getASTType() {
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
