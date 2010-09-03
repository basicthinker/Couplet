package canto.c1.x86;

/**
 * X86目标码的整数输入指令（一次输入一个整数）
 */
public class InInteger extends In {

	/** 存储用于输入的目的位置 */
	private final Location dst;
	
	/**
	 * 构造一条整数输入指令
	 * @param dst 输入的目的位置
	 */
	public InInteger(Location dst) {
		this.dst = dst;
	}

	@Override
	public void accept(X86Visitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return IN_INTEGER;
	}

	/**
	 * 获取输入的目的位置
	 * @return 输入的目的位置
	 */
	public Location getDst() {
		return dst;
	}

}
