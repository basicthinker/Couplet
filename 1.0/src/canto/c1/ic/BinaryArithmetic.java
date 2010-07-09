package canto.c1.ic;

public abstract class BinaryArithmetic extends Arithmetic {

	private final Operand src1;
	
	private final Operand src2;

	public BinaryArithmetic(Operand src1, Operand src2, Location dst) {
		super(dst);
		this.src1 = src1;
		this.src2 = src2;
	}

	public Operand getSrc1() {
		return src1;
	}

	public Operand getSrc2() {
		return src2;
	}
	
}
