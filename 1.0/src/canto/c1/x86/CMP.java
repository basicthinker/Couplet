package canto.c1.x86;

public class CMP extends Instruction {

	private final Operand operand1;
	
	private final Operand operand2;
	
	public CMP(Operand operand1, Operand operand2) {
		this.operand1 = operand1;
		this.operand2 = operand2;
	}

	@Override
	public int getTCType() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Operand getOperand1() {
		return operand1;
	}

	public Operand getOperand2() {
		return operand2;
	}

}
