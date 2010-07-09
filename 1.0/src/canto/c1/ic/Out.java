package canto.c1.ic;

public class Out extends Instruction {

	private final Operand src;
	
	public Out(Operand src) {
		this.src = src;
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return OUT;
	}

	public Operand getSrc() {
		return src;
	}

}
