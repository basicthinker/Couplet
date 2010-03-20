package canto.c1.ast;

/**
 * 单元操作符结点
 */
public class UnaryOperator extends Operator {

	/** 定义单元操作符编码常量 */
	public static final int POSITIVE = 1;
	public static final int NEGTIVE = 2;
	public static final int NOT = 3;
	
	/**
	 * 构造一个单元运算符
	 * @param code 单元运算符编码
	 */
	public UnaryOperator(int code) {
		super(code);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return UNARY_OPERATOR;
	}

}
