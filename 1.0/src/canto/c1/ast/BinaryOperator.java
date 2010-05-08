package canto.c1.ast;

/**
 * 二元操作符结点
 */
public class BinaryOperator extends Operator {
	
	/** 定义二元操作符类型编码常量 */
	private static final int PLUS = 1;
	private static final int MINUS = 2;
	private static final int TIMES = 3;
	private static final int LESS = 4;
	private static final int LESS_EQUAL = 5;
	private static final int GREATER = 6;
	private static final int GREATER_EQUAL = 7;
	private static final int EQUAL_EQUAL = 8;
	private static final int NOT_EQUAL = 9;
	private static final int AND_AND = 10;
	private static final int OR_OR = 11;

	/**
	 * 构造一个二元操作符
	 * @param code 操作符类型编码
	 */
	public BinaryOperator(int code, int line, int column) {
		super(code, line, column);
	}
	
	public static BinaryOperator newPlus(int line, int column) {
		return new BinaryOperator(PLUS, line, column);
	}
	
	public static BinaryOperator newMinus(int line, int column) {
		return new BinaryOperator(MINUS, line, column);
	}
	
	public static BinaryOperator newTimes(int line, int column) {
		return new BinaryOperator(TIMES, line, column);
	}
	
	public static BinaryOperator newLess(int line, int column) {
		return new BinaryOperator(LESS, line, column);
	}	
	
	public static BinaryOperator newLessEqual(int line, int column) {
		return new BinaryOperator(LESS_EQUAL, line, column);
	}
	
	public static BinaryOperator newGreater(int line, int column) {
		return new BinaryOperator(GREATER, line, column);
	}
	
	public static BinaryOperator newGreaterEqual(int line, int column) {
		return new BinaryOperator(GREATER_EQUAL, line, column);
	}
	
	public static BinaryOperator newEqualEqual(int line, int column) {
		return new BinaryOperator(EQUAL_EQUAL, line, column);
	}
	
	public static BinaryOperator newNotEqual(int line, int column) {
		return new BinaryOperator(NOT_EQUAL, line, column);
	}
	
	public static BinaryOperator newAndAnd(int line, int column) {
		return new BinaryOperator(AND_AND, line, column);
	}
	
	public static BinaryOperator newOrOr(int line, int column) {
		return new BinaryOperator(OR_OR, line, column);
	}
	
	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return BINARY_OPERATOR;
	}
	
}
