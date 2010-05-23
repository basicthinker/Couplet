package canto.c1.ic;

public class GTJump extends CondJump {

	public GTJump(int relation, Operand operand1, Operand operand2, Label target) {
		super(relation, operand1, operand2, target);
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
