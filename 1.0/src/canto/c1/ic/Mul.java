package canto.c1.ic;

/**
 * 中间代码的乘法指令
 */
public class Mul extends BinaryArithmetic {
	
	public Mul(Operand operand1, Operand operand2, Location result) {
		super(operand1, operand2, result);
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return MUL;
	}

}
