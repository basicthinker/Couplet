package canto.c1.ast;

/**
 * 输出语句结点
 */
public class OutputStatement extends Statement {

	/** 用于输出的表达式 */
	private final Expression expression;
		
	/**
	 * 构造一个输出语句
	 * @param expression 输出的表达式
	 */
	public OutputStatement(Expression expression, int line, int column) {
		super(line, column);
		this.expression = expression;
		if (expression != null) expression.setParent(this);
	}

	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getASTType() {
		return OUTPUT_STATEMENT;
	}

	/**
	 * 获取输出的表达式
	 * @return 输出的表达式
	 */
	public Expression getExpression() {
		return expression;
	}
	
}
