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
	public OutputStatement(Expression expression) {
		this.expression = expression;
	}

	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
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
