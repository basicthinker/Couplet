package canto.c1.ic;

/**
 * 中间代码的输入指令
 */
public class In extends Instruction {

	/** 储存输入的目的位置 */
	private final Location dst;
	
	/**
	 * 构造一条输入指令的中间代码
	 * @param dst 输入的目的位置
	 */
	public In(Location dst) {
		this.dst = dst;
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return IN;
	}

	/**
	 * 获取输入的目的位置
	 * @return 输入的目的位置
	 */
	public Operand getDst() {
		return dst;
	}

}
