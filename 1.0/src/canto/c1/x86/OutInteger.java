package canto.c1.x86;

/**
 * X86目标码的整数输出指令（一次输出一个整数）
 */
public class OutInteger extends Out {

	/** 存储用于输出的源操作数 */
	private final Operand src;

	/**
	 * 构造一条整数输出指令
	 * @param src 输出的源操作数
	 */
	public OutInteger(Operand src) {
		this.src = src;
	}
	
	@Override
	public void accept(X86Visitor visitor) throws Exception {
		visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return OUT_INTEGER;
	}

	/**
	 * 获取输出的源操作数
	 * @return 输出的源操作数
	 */
	public Operand getSrc() {
		return src;
	}

}
