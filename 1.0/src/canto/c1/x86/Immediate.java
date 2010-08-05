package canto.c1.x86;

/**
 * X86目标码的立即数操作
 */
public class Immediate extends Operand {

	/** 存储立即数的值 */
	private final Integer value;
	
	/**
	 * 构造一个立即数
	 * @param value 立即数的值
	 */
	public Immediate(Integer value) {
		this.value = value;
	}

	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return IMMEDIATE;
	}

	/**
	 * 获取立即数的值
	 * @return 立即数的值
	 */
	public Integer getValue() {
		return value;
	}

}
