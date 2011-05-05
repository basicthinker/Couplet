package couplet.c1.ic;

/**
 * 中间代码的赋值指令
 */
public class Mov extends Instruction {

	/** 存储源操作数 */
	private final Operand src;
	
	/** 存储目的位置 */
	private final Location dst;
	
	/**
	 * 构造一条中间代码的赋值指令
	 * @param src 源操作数
	 * @param dst 目的位置
	 */
	public Mov(Operand src, Location dst) {
		this.src = src;
		this.dst = dst;
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return MOVE;
	}

	/**
	 * 获取源操作数
	 * @return 源操作数
	 */
	public Operand getSrc() {
		return src;
	}

	/**
	 * 获取目的位置
	 * @return 目的位置
	 */
	public Location getDst() {
		return dst;
	}

}
