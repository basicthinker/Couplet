package canto.c1.x86;

import java.io.IOException;

/**
 * X86目标码的IMUL指令 
 */
public class IMUL extends BinaryArithmetic {

	/**
	 * 构造一条IMUL指令
	 * @param dst 目的操作数寄存器
	 * @param src 源操作数
	 */
	public IMUL(Register dst, Operand src) {
		super(dst, src);
	}
	
	@Override
	public void accept(X86Visitor visitor) throws IOException {
		visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return IMUL;
	}

}
