package canto.c1.x86;

public class IMUL extends BinaryArithmetic {

	public IMUL(Register dst, Operand src) {
		super(dst, src);
	}
	
	@Override
	public void accept(X86Visitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return IMUL;
	}

}
