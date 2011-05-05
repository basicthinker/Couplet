package couplet.c1.ic;

/**
 * 中间代码的无条件转移指令
 */
public class Goto extends Jump {

	/**
	 * 构造一条无条件中转的中间代码
	 * @param target 跳转的目的地
	 */
	public Goto(Label target) {
		super(target);
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return UNCOND_JUMP;
	}

}
