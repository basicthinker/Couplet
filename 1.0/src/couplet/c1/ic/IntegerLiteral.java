package couplet.c1.ic;

/**
 * 中间代码的整形常量操作数
 */
public class IntegerLiteral extends Literal {

	/** 存储整形常量的值 */
	private final Integer value; 
	
	/**
	 * 构造一个中间代码中的整形常量
	 * @param value 整形常量的值
	 */
	public IntegerLiteral(Integer value) {
		this.value = value;
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return INTEGER_LITERAL;
	}

	@Override
	public String toString() {
		return value.toString();
	}

	/**
	 * 获取整形常量的值
	 * @return 整形常量的值
	 */
	public Integer getValue() {
		return value;
	}
	
}
