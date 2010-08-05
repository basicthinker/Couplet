package canto.c1.x86;

/**
 * X86目标码的POP指令 
 */
public class POP extends Instruction {

	/** 存储出栈的目的操作数位置 */
	private final Location dst;
	
	/**
	 * 构造一个POP指令
	 * @param dst 出栈的目的操作数位置
	 */
	public POP(Location dst) {
		this.dst = dst;
	}

	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return POP;
	}

	/**
	 * 获取目的操作数位置
	 * @return 目的操作数位置
	 */
	public Location getDst() {
		return dst;
	}

}
