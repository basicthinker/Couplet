package canto.c1.ic;

public class JLE extends CJump {

	public JLE(Operand operand1, Operand operand2, Label target) {
		super(operand1, operand2, target);
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return LE_JUMP;
	}

}
