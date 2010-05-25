package canto.c1.ic;

public class JGT extends CJump {

	public JGT(Operand operand1, Operand operand2, Label target) {
		super(operand1, operand2, target);
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return GT_JUMP;
	}

}
