package canto.c1.ic;

public class In extends Instruction {

	private final Location dst;
	
	public In(Location dst) {
		this.dst = dst;
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return IN;
	}

	public Operand getDst() {
		return dst;
	}

}
