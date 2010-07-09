package canto.c1.ic;

public class In extends Instruction {

	private final Location location;
	
	public In(Location location) {
		this.location = location;
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
		return location;
	}

}
