package canto.c1.x86;

public class SUB extends BinaryArithmetic {

	public SUB(Location dst, Immediate src) {
		super(dst, src);
	}
	
	public SUB(Register dst, Operand src) {
		super(dst, src);
	}
	
	public SUB(Location dst, Register src) {
		super(dst, src);
	}

	@Override
	public void accept(X86Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return SUB;
	}

}
