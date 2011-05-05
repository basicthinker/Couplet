package couplet.c1.ic;

/**
 * 中间代码的减法指令
 */
public class Sub extends BinaryArithmetic {
	
	/**
	 * 构造一条中间代码的减法指令
	 * @see couplet.c1.ic.BinaryArithmetic#BinaryArithmetic(Operand, Operand, Location)
	 */
	public Sub(Operand operand1, Operand operand2, Location result) {
		super(operand1, operand2, result);
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return SUB;
	}

}
