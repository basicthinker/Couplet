package canto.c1.ast;

/**
 * 单元运算表达式结点
 */
public class UnaryExpression extends Expression {

	/** 单元操作符 */
	private final UnaryOperator operator;
	
	/** 参与运算的表达式 */
	private final Expression operand;
		
	/**
	 * 构造一个单元运算表达式
	 * @param operator 单元运算符
	 * @param operand 操作表达式
	 */
	public UnaryExpression(UnaryOperator operator, Expression operand) {
		this.operator = operator;
		this.operand = operand;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return UNARY_EXPRESSION;
	}

	/**
	 * 获取单元操作符
	 * @return 单元操作符
	 */
	public UnaryOperator getOperator() {
		return operator;
	}

	/**
	 * 获取参与运算的表达式
	 * @return 参与运算的表达式
	 */
	public Expression getOperand() {
		return operand;
	}

}
