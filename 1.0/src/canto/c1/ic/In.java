package canto.c1.ic;

public class In extends Instruction {

	private final Operand operand;
	
	public In(Operand operand) {
		this.operand = operand;
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return IN;
	}

	public Operand getOperand() {
		return operand;
	}

}
