package couplet.c1.ast;

/**
 * IF语句结点
 */
public class IfStatement extends Statement {

	/** IF语句的条件表达式 */
	private final Expression condition;
	
	/** 条件成立时执行的THEN语句 */
	private final Statement thenStatement;
	
	/** 条件不成立时执行的ELSE语句 */
	private final Statement elseStatement;
			
	/**
	 * 构造一个IF语句 
	 * @param condition 条件表达式
	 * @param thenStatement THEN语句
	 * @param elseStatement ELSE语句
	 */
	public IfStatement(Expression condition, Statement thenStatement,
			Statement elseStatement, int line, int column) {
		super(line, column);
		this.condition = condition;
		this.thenStatement = thenStatement;
		this.elseStatement = elseStatement;
		if (condition != null) condition.setParent(this);
		if (thenStatement != null)thenStatement.setParent(this);
		if (elseStatement != null) elseStatement.setParent(this);
	}

	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getASTType() {
		return IF_STATEMENT;
	}

	/**
	 * 获取条件表达式
	 * @return 条件表达式
	 */
	public Expression getCondition() {
		return condition;
	}

	/**
	 * 获取THEN语句
	 * @return THEN语句
	 */
	public Statement getThenStatement() {
		return thenStatement;
	}

	/**
	 * 获取ELSE语句
	 * @return ELSE语句
	 */
	public Statement getElseStatement() {
		return elseStatement;
	}

}
