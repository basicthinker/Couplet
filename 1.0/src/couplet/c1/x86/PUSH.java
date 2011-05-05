package couplet.c1.x86;

/**
 * X86目标码的PUSH指令
 */
public class PUSH extends Instruction {

	/** 存储入栈的源操作数 */
	private final Operand src;
	
	/**
	 * 构造一条PUSH语句
	 * @param src 入栈的源操作数
	 */
	public PUSH(Operand src) {
		this.src = src;
	}

	@Override
	public void accept(X86Visitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return PUSH;
	}

	/**
	 * 获取源操作数
	 * @return 源操作数
	 */
	public Operand getSrc() {
		return src;
	}

}
