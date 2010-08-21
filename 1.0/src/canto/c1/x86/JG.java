package canto.c1.x86;

import java.io.IOException;

/**
 * X86目标码的JG指令
 */
public class JG extends Jump {
	
	/**
	 * 构造一条JG指令
	 * @param target 跳转目的地
	 */
	public JG(Label target) {
		super(target);
	}
	
	@Override
	public void accept(X86Visitor visitor) throws IOException {
		visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return JG;
	}

}
