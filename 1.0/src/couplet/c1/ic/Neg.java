package couplet.c1.ic;

/**
 * 中间代码的取负数指令
 */
public class Neg extends UnaryArithmetic {
	
	/**
	 * 构造一条中间代码的取负数指令
	 * @see couplet.c1.ic.UnaryArithmetic#UnaryArithmetic(Operand, Location)
	 */
	public Neg(Operand operand, Location result) {
		super(operand, result);
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return NEG;
	}

}
