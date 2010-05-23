package canto.c1.ic;

public class LTJump extends CondJump {

	public LTJump(int relation, Operand operand1, Operand operand2, Label target) {
		super(relation, operand1, operand2, target);
	}

	@Override
	public void accept(ICVisitor visitor) throws Exception {
		visitor.visit(this);
	}

	@Override
	public int getICType() {
		return LT_JUMP;
	}

}
