package canto.c1.x86;

/**
 * X86目标码的符号
 */
public class Symbol extends Memory {

	/** 存储符号名称的数据结构 */
	private final String name;
	
	/**
	 * 构造一个符号
	 * @param name 符号名称
	 */
	public Symbol(String name) {
		this.name = name;
	}

	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return SYMBOL;
	}

	/**
	 * 获取符号名称
	 * @return 符号名称
	 */
	public String getName() {
		return name;
	}

}
