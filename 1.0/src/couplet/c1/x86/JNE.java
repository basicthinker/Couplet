package couplet.c1.x86;

/**
 * X86目标码的JNE指令
 */
public class JNE extends Jump {

	/**
	 * 构造一条JNE指令
	 * @param target 跳转目的地
	 */
	public JNE(Label target) {
		super(target);
	}
	
	@Override
	public void accept(X86Visitor visitor) throws Exception {
		visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return JNE;
	}

}
