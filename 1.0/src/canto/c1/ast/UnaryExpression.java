package canto.c1.ast;

/**
 * 单元运算表达式结点的基类
 */
public abstract class UnaryExpression extends Expression {

	/** 参与运算的表达式 */
	private final Expression operand;
		
	/**
	 * 构造一个单元运算表达式
	 * @param operator 单元运算符
	 * @param operand 操作表达式
	 */
	public UnaryExpression(Expression operand, int line, int column) {
		super(line, column);
		this.operand = operand;
		if (operand != null) operand.setParent(this);
	}

	/**
	 * 获取参与运算的表达式
	 * @return 参与运算的表达式
	 */
	public Expression getOperand() {
		return operand;
	}

}
