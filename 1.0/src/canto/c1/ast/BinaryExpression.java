package canto.c1.ast;

/**
 * 二元运算表达式结点的基类
 */
public abstract class BinaryExpression extends Expression {

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
	public BinaryExpression(Expression leftOperand, Expression rightOperand, 
			int line, int column) {
		super(line, column);
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
		if (leftOperand != null) leftOperand.setParent(this);
		if (rightOperand != null) rightOperand.setParent(this);
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
