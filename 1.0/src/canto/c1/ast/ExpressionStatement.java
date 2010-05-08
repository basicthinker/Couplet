package canto.c1.ast;

/**
 * 表达式语句结点
 */
public class ExpressionStatement extends Statement {

	/** 语句中的表达式 */
	private final Expression expression;
	
	/**
	 * 构造一个表达式语句结点
	 * @param line
	 * @param column
	 */
	public ExpressionStatement(Expression expression, int line, int column) {
		super(line, column);
		this.expression = expression;
	}

	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return EXPRESSION_STATEMENT;
	}

	/**
	 * 获取语句中的表达式
	 * @return 语句中的表达式
	 */
	public Expression getExpression() {
		return expression;
	}

}
