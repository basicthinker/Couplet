package couplet.c1.x86;

/**
 * X86目标码的JL指令
 */
public class JL extends Jump {

	/**
	 * 构造一条JL指令
	 * @param target 跳转目的地
	 */
	public JL(Label target) {
		super(target);
	}
	
	@Override
	public void accept(X86Visitor visitor) throws Exception {
		visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return JL;
	}

}
