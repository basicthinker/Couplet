package canto.c1.ic;

/**
 * 中间代码的无条件转移指令
 */
public class Goto extends Jump {

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
