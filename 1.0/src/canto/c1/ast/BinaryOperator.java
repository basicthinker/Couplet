package canto.c1.ast;

/**
 * 二元操作符结点
 */
public class BinaryOperator extends Operator {
	
	/** 定义二元操作符类型编码常量 */
	public static final int PLUS = 1;
	public static final int MINUS = 2;
	public static final int TIMES = 3;
	public static final int LESS = 4;
	public static final int GREATER = 5;
	public static final int LESS_EQUALS = 6;
	public static final int GREATER_EQUALS = 7;
	public static final int EQUALS = 8;
	public static final int NOT_EQUALS = 9;

	/**
	 * 构造一个二元操作符
	 * @param code 操作符类型编码
	 */
	public BinaryOperator(int code) {
		super(code);
	}	

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return BINARY_OPERATOR;
	}
	
}
