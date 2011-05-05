package couplet.c1.ast;

/**
 * WHILE语句结点
 */
public class WhileStatement extends Statement {

	/** WHILE语句的条件表达式 */
	private final Expression condition;
	
	/** WHILE语句条件成立时执行的语句 */
	private final Statement body;
	
	/**
	 * 构造一个WHILE语句
	 * @param condition 条件表达式
	 * @param body 主体语句
	 */
	public WhileStatement(Expression condition, Statement body, 
			int line, int column) {
		super(line, column);
		this.condition = condition;
		this.body = body;
		if (condition != null) condition.setParent(this);
		if (body != null) body.setParent(this);
	}

	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getASTType() {
		return WHILE_STATEMENT;
	}

	/**
	 * 获取条件表达式
	 * @return 条件表达式
	 */
	public Expression getCondition() {
		return condition;
	}

	/**
	 * 获取主体语句
	 * @return 主体语句
	 */
	public Statement getBody() {
		return body;
	}

}
