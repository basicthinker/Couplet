package canto.c1.ic;

public abstract class UnaryArithmetic extends Arithmetic {

	private final Operand src;

	public UnaryArithmetic(Operand src, Location dst) {
		super(dst);
		this.src = src;
	}

	public Operand getSrc() {
		return src;
	}
	
}
