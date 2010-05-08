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
	public UnaryOperator(int code, int line, int column) {
		super(code, line, column);
	}

	public static UnaryOperator newPositive(int line, int column) {
		return new UnaryOperator(POSITIVE, line, column);
	}
	
	public static UnaryOperator newNegtive(int line, int column) {
		return new UnaryOperator(NEGTIVE, line, column);
	}
	
	public static UnaryOperator newNot(int line, int column) {
		return new UnaryOperator(NOT, line, column);
	}
	
	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return UNARY_OPERATOR;
	}

}
