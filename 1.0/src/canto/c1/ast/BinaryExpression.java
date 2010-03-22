package canto.c1.ast;

/**
 * 二元运算表达式结点
 */
public class BinaryExpression extends Expression {

	/** 二元操作符 */
	private final BinaryOperator operator;
	
	/** 操作符左侧的表达式 */
	private final Expression leftOperand;
	
	/** 操作符右侧的表达式 */
	private final Expression rightOperand;
	
	/**
	 * 构造一个二元运算表达式 
	 * @param operator 二元操作符
	 * @param leftOperand 左操作表达式
	 * @param rightOperand 右操作表达式
	 */
	public BinaryExpression(BinaryOperator operator,
			Expression leftOperand, Expression rightOperand) {
		this.operator = operator;
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
	}

	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return BINARY_EXPRESSION;
	}

	/**
	 * 获取二元操作符
	 * @return 二元操作符
	 */
	public BinaryOperator getOperator() {
		return operator;
	}

	/**
	 * 获取操作符左侧的表达式
	 * @return 左操作表达式
	 */
	public Expression getLeftOperand() {
		return leftOperand;
	}

	/**
	 * 获取操作符右侧的表达式
	 * @return 右操作表达式
	 */
	public Expression getRightOperand() {
		return rightOperand;
	}
}
