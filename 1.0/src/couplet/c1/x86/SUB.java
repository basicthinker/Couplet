package couplet.c1.x86;

/**
 * X86目标码的SUB指令
 */
public class SUB extends BinaryArithmetic {

	/**
	 * 构造一条源操作数为立即数、目的操作数为任意位置的SUB指令
	 * @param dst 目的操作数位置
	 * @param src 源操作立即数
	 */
	public SUB(Location dst, Immediate src) {
		super(dst, src);
	}
	
	/**
	 * 构造一条源操作数为寄存器、目的操作数为任意位置的SUB指令
	 * @param dst 目的操作数位置
	 * @param src 源操作寄存器
	 */
	public SUB(Location dst, Register src) {
		super(dst, src);
	}
	
	/**
	 * 构造一条源操作数为任意、目的操作为寄存器的SUB指令
	 * @param dst 源操作数
	 * @param src 目的操作寄存器
	 */
	public SUB(Register dst, Operand src) {
		super(dst, src);
	}
	
	@Override
	public void accept(X86Visitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return SUB;
	}

}
