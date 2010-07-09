package canto.c1.x86;

public class IMUL extends BinaryArithmetic {

	public IMUL(Register dst, Operand src) {
		super(dst, src);
	}

	@Override
	public int getTCType() {
		// TODO Auto-generated method stub
		return 0;
	}

}
