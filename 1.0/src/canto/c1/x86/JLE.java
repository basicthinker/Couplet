package canto.c1.x86;

/**
 * X86目标码的JLE指令
 */
public class JLE extends Jump {

	/**
	 * 构造一条JLE指令
	 * @param target 跳转目的地
	 */
	public JLE(Label target) {
		super(target);
	}
	
	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		// TODO Auto-generated method stub
		return JLE;
	}

}
