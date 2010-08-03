package canto.c1.x86;

public class Register extends Location {

	public static final int EAX = 0;
	public static final int EBX = 1;
	public static final int ECX = 2;
	public static final int EDX = 3;
	public static final int EBP = 4;
	public static final int ESP = 5;
	public static final int EDI = 6;
	public static final int ESI = 7;
	
	private final int regNum;
	
	public Register(int regNum) {
		this.regNum = regNum;
	}

	@Override
	public Object accept(X86Visitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public int getTCType() {
		return REGISTER;
	}

	public int getRegNum() {
		return regNum;
	}

}
