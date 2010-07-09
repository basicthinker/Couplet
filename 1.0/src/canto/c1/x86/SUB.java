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
	public int getTCType() {
		// TODO Auto-generated method stub
		return 0;
	}

}
