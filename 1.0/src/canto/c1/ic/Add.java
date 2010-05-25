package canto.c1.ic;

/**
 * 中间代码的加法指令
 */
public class Add extends BinaryArithmetic {

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
