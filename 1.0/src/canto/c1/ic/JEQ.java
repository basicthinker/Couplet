package canto.c1.ic;

public class JEQ extends CJump {

	public JEQ(Operand operand1, Operand operand2, Label target) {
		super(operand1, operand2, target);
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return EQ_JUMP;
	}

}
