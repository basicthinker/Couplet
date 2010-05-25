package canto.c1.ic;

public abstract class UnaryArithmetic extends Arithmetic {

	private final Operand operand;

	public UnaryArithmetic(Operand operand, Location result) {
		super(result);
		this.operand = operand;
	}

	public Operand getOperand() {
		return operand;
	}
	
}
