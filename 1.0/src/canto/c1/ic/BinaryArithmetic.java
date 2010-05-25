package canto.c1.ic;

public abstract class BinaryArithmetic extends Arithmetic {

	private final Operand operand1;
	
	private final Operand operand2;

	public BinaryArithmetic(Operand operand1, Operand operand2, Location result) {
		super(result);
		this.operand1 = operand1;
		this.operand2 = operand2;
	}

	public Operand getOperand1() {
		return operand1;
	}

	public Operand getOperand2() {
		return operand2;
	}
	
}
