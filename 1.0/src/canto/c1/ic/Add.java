package canto.c1.ic;

/**
 * 中间代码的加法指令
 */
public class Add extends BinaryArithmetic {

	/**
	 * 构造一条加法的中间代码
	 * @see canto.c1.ic.BinaryArithmetic#BinaryArithmetic(Operand, Operand, Location)
	 */
	public Add(Operand operand1, Operand operand2, Location result) {
		super(operand1, operand2, result);
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);		
	}

	@Override
	public int getICType() {
		return ADD;
	}
	
}
