package canto.c1.ic;

/**
 * 中间代码的输出指令
 */
public class Out extends Instruction {

	/** 存储输出的源操作数 */
	private final Operand src;
	
	/**
	 * 构造一条中间代码的输出指令
	 * @param src 输出的源操作数
	 */
	public Out(Operand src) {
		this.src = src;
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return OUT;
	}

	/**
	 * 获取输出的源操作数
	 * @return 输出的源操作数
	 */
	public Operand getSrc() {
		return src;
	}

}
