package canto.c1.ast;

/**
 * 整型字面常量结点
 */
public class IntegerLiteral extends Literal {

	/** 整型字面常量的值 */
	private final Integer value;
	
	/**
	 * 构造一个整型字面常量
	 * @param value 整型值
	 */
	public IntegerLiteral(Integer value, int line, int column) {
		super(line, column);
		this.value = value;
	}

	@Override
	public void accept(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getNodeType() {
		return INTEGER_LITERAL;
	}

	/**
	 * 获取整型字面常量的值
	 * @return 整形值
	 */
	public Integer getValue() {
		return value;
	}

}
