package canto.c1.ic;

/**
 * 中间代码的取负数指令
 */
public class Neg extends Arithmetic {

	private final Operand operand;
	
	public Neg(Operand operand) {
		this.operand = operand;
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return NEG;
	}

	public Operand getOperand() {
		return operand;
	}

}
