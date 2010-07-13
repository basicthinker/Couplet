package canto.c1.x86;

public class ADD extends BinaryArithmetic {

	public ADD(Location dst, Immediate src) {
		super(dst, src);
	}
	
	public ADD(Register dst, Operand src) {
		super(dst, src);
	}
	
	public ADD(Location dst, Register src) {
		super(dst, src);
	}
	
	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);
	}
	
	@Override
	public int getTCType() {
		return ADD;
	}

}
