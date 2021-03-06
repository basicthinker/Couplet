package couplet.c1.x86;

/**
 * X86目标码的JMP指令
 */
public class JMP extends Jump {

	/**
	 * 构造一条JMP指令
	 * @param target 跳转目的地
	 */
	public JMP(Label target) {
		super(target);
	}
	
	@Override
	public void accept(X86Visitor visitor) throws Exception {
		visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return JMP;
	}

}
