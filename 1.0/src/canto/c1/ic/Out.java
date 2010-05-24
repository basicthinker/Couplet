package canto.c1.ic;

public class Out extends Instruction {

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return OUT;
	}

}
