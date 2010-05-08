package canto.c1.ast;

/**
 * 操作符的基类
 */
public abstract class Operator extends ASTNode {
	
	/** 操作符类型编码 */
	private final int code;
	
	/**
	 * 构造一个操作符
	 * @param code 操作符类型编码
	 */
	public Operator(int code, int line, int column) {
		super(line, column);
		this.code = code;
	}
	
	/**
	 * 获取操作符类型编码
	 * @return 操作符类型编码
	 */
	public int getOperatorCode() {
		return code;
	}
	
}
