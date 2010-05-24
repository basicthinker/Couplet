package canto.c1.ic;

public class Out extends Instruction {

	private final Operand operand;
	
	public Out(Operand operand) {
		this.operand = operand;
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return OUT;
	}

	public Operand getOperand() {
		return operand;
	}

}
