package canto.c1.x86;

import java.io.IOException;

/**
 * X86目标码的MOV指令
 */
public class MOV extends Instruction {

	/** 存储目的操作数位置 */
	private final Location dst;
	
	/** 存储源操作数 */
	private final Operand src;

	/**
	 * 构造一条MOV指令
	 * @param dst
	 * @param src
	 */
	public MOV(Location dst, Operand src) {
		this.dst = dst;
		this.src = src;
	}

	@Override
	public void accept(X86Visitor visitor) throws IOException {
		visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return MOV;
	}

	public Location getDst() {
		return dst;
	}

	public Operand getSrc() {
		return src;
	}
	
}
