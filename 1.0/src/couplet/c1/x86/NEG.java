package couplet.c1.x86;

/**
 * X86目标码的NEG指令
 */
public class NEG extends UnaryArithmetic {

	/**
	 * 构造一条NEG指令
	 * @param dst 源操作数位置
	 */
	public NEG(Location dst) {
		super(dst);
	}

	@Override
	public void accept(X86Visitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return NEG;
	}

}
