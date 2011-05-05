package couplet.c1.x86;

/**
 * X86目标码的JE指令 
 */
public class JE extends Jump {

	/**
	 * 构造一条JE指令
	 * @param target 跳转目的地
	 */
	public JE(Label target) {
		super(target);
	}
	
	@Override
	public void accept(X86Visitor visitor) throws Exception {
		visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return JE;
	}

}
