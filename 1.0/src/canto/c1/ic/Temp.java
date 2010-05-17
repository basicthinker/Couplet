package canto.c1.ic;

/**
 * 中间代码的临时变量操作数
 */
public class Temp extends Operand {

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return TEMP;
	}

}
