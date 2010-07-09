package canto.c1.x86;

public abstract class BinaryArithmetic extends Arithmetic {

	public final Operand src;
	
	public BinaryArithmetic(Location dst, Operand src) {
		super(dst);
		this.src = src;
	}

	public Operand getSrc() {
		return src;
	}

}
